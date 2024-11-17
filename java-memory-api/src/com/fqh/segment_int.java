import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;

import static java.io.IO.println;


void main() {

    var segment = Arena.global().allocate(512L);
    println(segment);

    segment.set(ValueLayout.JAVA_INT, 4L, 42);
//    segment.set(ValueLayout.JAVA_INT, 3L, 32);  只允许4字节对齐的offset  crash!
    var value = segment.get(ValueLayout.JAVA_INT, 4L);
    println(value);

//    var array = Arena.global().allocate(ValueLayout.JAVA_INT, );
//    array.setAtIndex(ValueLayout.JAVA_INT, 4L, 777);
//    var value2 = array.getAtIndex(ValueLayout.JAVA_INT, 4L);
//    println(value2);


}