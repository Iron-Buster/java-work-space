import java.lang.foreign.Arena;
import java.nio.charset.StandardCharsets;

import static java.io.IO.println;

void main() {

    var seg = Arena.global().allocate(512L);
    println("seg " + seg);

    seg.setString(0, "hello", StandardCharsets.UTF_8);
    var text = seg.getString(0, StandardCharsets.UTF_8);

    println(text);
}