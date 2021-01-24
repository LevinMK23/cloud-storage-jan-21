package stream;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Example {

    // java6, java7
    // java8 Stream Api, Method Reference, Lambda

    public static void main(String[] args) {

        Consumer<String> consumer = str -> {
            System.out.println("Hello " + str);
        };

        consumer.accept("world!");

        Supplier<HashMap<String, Integer>> supplier = HashMap::new;

        System.out.println(supplier.get());

        Predicate<Integer> predicate = arg -> arg > 5;

        System.out.println(predicate.test(7));

        Function<String, Integer> length = String::length;

        System.out.println(length.apply("123"));

        System.out.println(length.compose(String::valueOf).apply(1234));

        System.out.println(length.andThen(a -> a * 2).apply("1234"));

        //length.compose(String::valueOf).apply("123");
        //System.out.println();
        // System.out.println(sum.calculate(1,2));
        // Lisp, Haskell, Scala, Rust
    }
}
