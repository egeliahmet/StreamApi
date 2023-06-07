import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //stream creation

        Stream<String> stream=Stream.empty();
        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> collectStream=collection.stream();
        //*******stream of array**********
        Stream<String> streamOfArray = Stream.of("a", "b", "c");
        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> arr1=Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);

        Stream<String> s=Stream.<String>builder().add("ege").build();
        Stream<String> streamGenerated =Stream.generate(()->"element").limit(10);
        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        //streamIterated.forEach(System.out::println);

        //*****stream of primitives******
        IntStream intStream = IntStream.range(1, 3);
        intStream.forEach(System.out::println);
        LongStream longStream = LongStream.rangeClosed(1, 3);
        longStream.forEach(System.out::println);
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        IntStream streamOfChars = "abc".chars();
        //The following example breaks a String into sub-strings according to specified RegEx:
        Stream<String> streamOfString =
                Pattern.compile(", ").splitAsStream("a, b, c");

        //stream of file
        Path path = Paths.get("C:\\file.txt");
        try {
            Stream<String> streamOfStrings = Files.lines(path);
            Stream<String> streamWithCharset =
                    Files.lines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Stream<String> streamOf(List<String> list) {
        return list == null || list.isEmpty() ? Stream.empty() : list.stream();
    }
}
