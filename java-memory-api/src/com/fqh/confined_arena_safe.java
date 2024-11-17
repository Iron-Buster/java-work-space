import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

/**
 * Arena.ofConfined()
 * 使用这种方式创建的MemorySegment 只允许构建Arena的线程访问
 * 当你访问内存段时，会检查小大、线程、Arena是否已经关闭。
 */
void main() throws InterruptedException {

    MemorySegment seg;
    try (var arena = Arena.ofConfined()) {
        seg = arena.allocate(ValueLayout.JAVA_INT, 512L);
//        seg.setAtIndex(ValueLayout.JAVA_INT, 1024L, 42); // 1.OUT_INDEX

        new Thread(() -> {
//            seg.setAtIndex(ValueLayout.JAVA_INT, 12L, 42); // 2.java.lang.WrongThreadException
        }).start();
        Thread.sleep(1000);

    }
    // twr Arena已经关闭了
    // 3.java.lang.IllegalStateException: Already closed
//    seg.setAtIndex(ValueLayout.JAVA_INT, 12L, 32);
//    seg.getAtIndex(ValueLayout.JAVA_INT, 12L);
}