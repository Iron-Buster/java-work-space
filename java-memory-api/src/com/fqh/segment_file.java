
import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.io.IO.println;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

void main() throws IOException {

    var text = """
            hello byte buffer
            """;
    var seg = Arena.global().allocate(64L);

    var array = text.getBytes(StandardCharsets.UTF_8);
    seg.copyFrom(MemorySegment.ofArray(array));
    var byteBuffer = seg.asByteBuffer();
    byteBuffer.limit(array.length);

    var path = Path.of("files/file7");
    try (var file = FileChannel.open(path, CREATE, WRITE)) {
        file.write(byteBuffer);
    }

    println("file: " + Files.readString(path));
}