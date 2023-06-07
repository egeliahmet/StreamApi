import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamReduction {
    public static void main(String[] args) {

        /*
        * The API has many terminal operations which aggregate a stream to a type or to a primitive:
        *  count(), max(), min(), and sum(). However, these operations work according to the predefined implementation.
        *  So what if a developer needs to customize a Stream's reduction mechanism?
        *  There are two methods which allow us to do this, the reduce() and the collect() methods
        * The reduce() Method
        *There are three variations of this method, which differ by their signatures and returning types.
        * They can have the following parameters:
        * */
        /*
        * IDENTITIY – the initial value for an accumulator,
        * or a default value if a stream is empty and there is nothing to accumulate
        ACCUMULATOR – a function which specifies the logic of the aggregation of elements.
        * As the accumulator creates a new value for every step of reducing,
        * the quantity of new values equals the stream's size and only the last value is useful.
        * This is not very good for the performance.

        COMBINER – a function which aggregates the results of the accumulator.
        * We only call combiner in a parallel mode to reduce the results of accumulators from different threads.
        * */

        OptionalInt reduced =
                IntStream.range(1, 4).reduce((a, b) ->{
                    System.out.println("a:"+a+"b:"+b);
                    return a + b;} );
        System.out.println("reduced:"+reduced);

        int reducedTwoParams =
                IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        System.out.println("reducedTwoParams:"+reducedTwoParams);


        /*
        *The result will be the same as in the previous example (16), and there will be no login,
        *  which means that combiner wasn't called. To make a combiner work, a stream should be parallel:
        * */
        int reducedParams = Stream.of(1, 2, 3)
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    System.out.println("combiner was called");
                    return a + b;
                });
        System.out.println(reducedParams);

        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    System.out.println("combiner was called");
                    return a+b;
                });
        System.out.println("reducedParallel:"+reducedParallel);
        /*
        * The result here is different (36), and the combiner was called twice.
        * Here the reduction works by the following algorithm:
        * the accumulator ran three times by adding every element of the stream to identity.
        * These actions are being done in parallel. As a result, they have (10 + 1 = 11; 10 + 2 = 12; 10 + 3 = 13;).
        *  Now combiner can merge these three results. It needs two iterations for that (12 + 13 = 25; 25 + 11 = 36).
        * */

        //Collect metod

        /*
        * The reduction of a stream can also be executed by another terminal operation, the collect() method.
        *  It accepts an argument of the type Collector, which specifies the mechanism of reduction.
        * There are already created, predefined collectors for most common operations.
        * They can be accessed with the help of the Collectors type.

        In this section, we will use the following List as a source for all streams:
        * */
        List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                new Product(14, "orange"), new Product(13, "lemon"),
                new Product(23, "bread"), new Product(13, "sugar"));

        List<String> collectorCollection =
                productList.stream().map(Product::getName).collect(Collectors.toList());


        /*
        * The joiner() method can have from one to three parameters (delimiter, prefix, suffix).
        *  The most convenient thing about using joiner()
        *  is that the developer doesn't need to check if the stream reaches its end to apply the suffix
        * and not to apply a delimiter.
        * Collector will take care of that.
        * */
        String listToString = productList.stream().map(Product::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        double averagePrice = productList.stream()
                .collect(Collectors.averagingDouble(Product::getPrice));
        double summingPrice = productList.stream()
                .collect(Collectors.summingDouble(Product::getPrice));

        /*
        * By using the resulting instance of type IntSummaryStatistics,
        * the developer can create a statistical report by applying the toString() method.
        *  The result will be a String common to this one
        * “IntSummaryStatistics{count=5, sum=86, min=13, average=17,200000, max=23}.”
        * */
        DoubleSummaryStatistics statistics = productList.stream()
                .collect(Collectors.summarizingDouble(Product::getPrice));
                  statistics.getAverage();
        //Grouping of stream’s elements according to the specified function:
        Map<Double, List<Product>> collectorMapOfLists = productList.stream()
                .collect(Collectors.groupingBy(Product::getPrice));
        //Dividing stream’s elements into groups according to some predicate:
        Map<Boolean, List<Product>> mapPartioned = productList.stream()
                .collect(Collectors.partitioningBy(element -> element.getPrice() > 15));

        //  Pushing the collector to perform additional transformation:

        Set<Product> unmodifiableSet = productList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toSet(),
                        Collections::unmodifiableSet));
    }

}
