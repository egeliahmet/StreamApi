import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamReference {
    public static void main(String[] args) {
        Stream<String> stream =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        Optional<String> anyElement = stream.findAny();
        /*the below code will throw illegalStateException becouse stream has terminate operator*/
       // Optional<String> firstElement = stream.findFirst();

        List<String> elements =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"))
                        .collect(Collectors.toList());
        Optional<String> any2Element = elements.stream().findAny();
        Optional<String> firstElement = elements.stream().findFirst();

    }
}
