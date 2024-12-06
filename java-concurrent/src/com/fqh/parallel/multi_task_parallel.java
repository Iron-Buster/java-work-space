

/**
 * @author <a href="qihao.feng@vtradex.com">fqh</a>
 * @link <a href="https://github.com/Iron-Buster">Iron-Buster</a>
 * @date 2024/12/5 17:39
 */

/**
 * 在任务量相同的情况下，<b>虚拟线程</b> 处理任务的时间相比平台线程缩短了一半。
 * 并行流 和 线程池 + CF 相差不大
 */

//static long[] times = {1000, 3000, 2500, 4000, 1500}; // random
static long[] times = {1500};

void main() {
    // 10w个任务，每组5000个
    List<Integer> numbers = IntStream.range(1, 100001)
            .boxed()
            .toList();

    List<List<Integer>> partitionList = partitionList(numbers, 5000);

    runWithParallelStream(partitionList);

//    runWithStream(partitionList);

    runWithThreadPool(partitionList);

    runWithVirtualThread(partitionList);

    runWithStructuredTaskScope(partitionList);

}

/** 串行执行 */
static void runWithStream(List<List<Integer>> partitionList) {
    long t1 = System.currentTimeMillis();
    partitionList.forEach(_ -> {
                try {
                    // mock costTime
//                    long start = System.currentTimeMillis();
                    int i = ThreadLocalRandom.current().nextInt(times.length);
                    Thread.sleep(times[i]);
//                    long end = System.currentTimeMillis();
//                    System.out.println(Thread.currentThread().getName() + " cost: " + (end - start) + "ms");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
    long t2 = System.currentTimeMillis();
    System.out.println("runWithStream totalCost: " + (t2 - t1) + "ms");
}

/** 并行流 底层ForkJoinPool */
static void runWithParallelStream(List<List<Integer>> partitionList) {
    long t1 = System.currentTimeMillis();
    partitionList.parallelStream()
            .forEach(_ -> {
                try {
                    // mock costTime
//                    long start = System.currentTimeMillis();
                    int i = ThreadLocalRandom.current().nextInt(times.length);
                    Thread.sleep(times[i]);
//                    long end = System.currentTimeMillis();
//                    System.out.println(Thread.currentThread().getName() + " cost: " + (end - start) + "ms");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
    long t2 = System.currentTimeMillis();
    System.out.println("runWithParallelStream  totalCost: " + (t2 - t1) + "ms");
}

/** 线程池（平台线程） + CF */
static void runWithThreadPool(List<List<Integer>> partitionList) {
    long t1 = System.currentTimeMillis();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    CompletableFuture<Void>[] cfs = new CompletableFuture[partitionList.size()];

    // 遍历 partitionList，并为每个任务创建一个 CompletableFuture
    for (int index = 0; index < partitionList.size(); index++) {
        final int i = index;
        cfs[i] = CompletableFuture.runAsync(() -> {
            try {
                int j = ThreadLocalRandom.current().nextInt(times.length);
                Thread.sleep(times[j]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    // 等待所有任务完成
    try {
        CompletableFuture.allOf(cfs).join(); // 等待所有任务执行完成
    } finally {
        executorService.shutdown(); // 关闭线程池
    }

    long t2 = System.currentTimeMillis();
    System.out.println("runWithThreadPool + CF totalCost: " + (t2 - t1) + "ms");
}

/** 虚拟线程 + CF */
static void runWithVirtualThread(List<List<Integer>> partitionList) {
    long t1 = System.currentTimeMillis();
    // 创建一个虚拟线程池，每个任务都会在一个新的虚拟线程中执行
    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    CompletableFuture<Void>[] cfs = new CompletableFuture[partitionList.size()];

    // 遍历 partitionList，为每个任务创建一个 CompletableFuture
    for (int index = 0; index < partitionList.size(); index++) {
        final int i = index;
        cfs[i] = CompletableFuture.runAsync(() -> {
            try {
                int j = ThreadLocalRandom.current().nextInt(times.length);
                Thread.sleep(times[j]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 保证中断信号得到传递
                throw new RuntimeException("Task " + i + " was interrupted", e);
            }
        }, executorService);
    }

    // 等待所有任务完成
    try {
        CompletableFuture.allOf(cfs).join(); // 等待所有任务执行完成
    } finally {
        executorService.shutdown(); // 关闭线程池
    }

    long t2 = System.currentTimeMillis();
    System.out.println("runWithVirtualThread totalCost: " + (t2 - t1) + "ms");
}

/** 虚拟线程 + 结构化并发 */
static void runWithStructuredTaskScope(List<List<Integer>> partitionList) {
    long t1 = System.currentTimeMillis();
    // 使用结构化任务作用域来管理任务
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        // 创建虚拟线程池
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        // 遍历 partitionList，为每个任务创建一个 CompletableFuture，并提交到作用域中
        for (int index = 0; index < partitionList.size(); index++) {
            final int i = index;
            // 提交任务到结构化任务作用域
            scope.fork(() -> {
                try {
                    int j = ThreadLocalRandom.current().nextInt(times.length);
                    Thread.sleep(times[j]);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 保证中断信号得到传递
                    throw new RuntimeException("Task " + i + " was interrupted", e);
                }
                return null;
            });
        }

        // 等待所有任务完成
        scope.join(); // 阻塞直到所有任务完成
    } catch (Exception e) {
        e.printStackTrace();
    }

    long t2 = System.currentTimeMillis();
    System.out.println("runWithStructuredTaskScope totalCost: " + (t2 - t1) + "ms");
}


static <T> List<List<T>> partitionList(List<T> list, int groupSize) {
    List<List<T>> partitionedList = new ArrayList<>();
    for (int i = 0; i < list.size(); i += groupSize) {
        partitionedList.add(list.subList(i, Math.min(i + groupSize, list.size())));
    }
    return partitionedList;
}
