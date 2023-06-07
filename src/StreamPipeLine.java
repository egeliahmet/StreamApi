import java.util.*;
import java.util.stream.Stream;

public class StreamPipeLine {
    public static void main(String[] args) {
        /*To perform a sequence of operations over the elements of the data source and aggregate their results,
        we need three parts: the source, intermediate operation(s) and a terminal operation.*/

        Stream<String> onceModifiedStream =
                Stream.of("abcd", "bbcd", "cbcd").skip(1);

        Stream<String> twiceModifiedStream =
                onceModifiedStream.skip(1).map(element -> element.substring(0, 3));
        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        long size = list.stream().skip(1)
                .map(element -> element.substring(0, 3)).sorted().count();
        System.out.println(size);

        /*
Intermediate operations are lazy.
This means that they will be invoked only if it is necessary for the terminal operation execution.    }
         */
        List<String> list2 = Arrays.asList("abc1", "abc2", "abc3");
        Optional<String> s=list2.stream().filter(element->{
            System.out.println(element);
            return element.contains("2");
        }).map(element->{
            System.out.println("-"+element);
            return element.toUpperCase();
        }).findAny();



        Optional<String> s2=list2.stream().filter(element->{
            System.out.println(element);
            return element.contains("2");
        }).map(String::toUpperCase).findAny();

        /*Order of Execution
        From the performance point of view,
        the right order is one of the most important aspects of chaining operations in the stream pipeline:
        first called 3 times 2. called only once*/
        long size2 = list.stream().map(element -> {
            return element.substring(0, 3);
        }).skip(2).count();

        long size3 = list.stream().skip(2).map(element -> {
            return element.substring(0, 3);
        }).count();
    }
}
