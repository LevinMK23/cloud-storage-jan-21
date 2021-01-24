package stream;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    // type
    // operations: map, flatMap, reduce

    public static void main(String[] args) throws IOException {

        User user = new User(12, "Ivan");

        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(x -> x % 2 == 0)
                .map(String::valueOf)
                .map(str -> {
                    int repeat = Integer.parseInt(str);
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < repeat; i++) {
                        result.append(str).append(" ");
                    }
                    return result.toString();
                })
                .forEach(System.out::println);

        String words = Files.lines(Paths.get("out.txt"))
                .filter(s -> !s.isEmpty())
                .flatMap(line -> Arrays.stream(line.split(" +")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(", "));
        System.out.println(words);

        Map<String, Integer> wordsMap = Files.lines(Paths.get("out.txt"))
                .filter(s -> !s.isEmpty())
                .flatMap(line -> Arrays.stream(line.split(" +")))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toMap(
                        Function.identity(),
                        str -> 1,
                        Integer::sum,
                        TreeMap::new
                ));

        // Stream.of(1, 2, 3)

        System.out.println(wordsMap);

        Optional<Integer> result = Stream.of(1,2,3)
                .filter(x -> x > 1)
                .reduce(Integer::sum);

        System.out.println(result.orElse(0));

        UserService userService = new UserService();
        BalanceService balanceService = new BalanceService();

        User user1 = userService.getUserById(1).get();
        Balance balance = balanceService.getClientBalance(user1.getId()).get();
        Long m = balance.getMoney();

        Long money = userService.getUserById(1)
                .flatMap(u -> balanceService.getClientBalance(u.getId()))
                .map(Balance::getMoney)
                .orElseThrow(() -> new RuntimeException("User balance not found!"));

        System.out.println(money);

        String paths = Files.list(Paths.get("cloud-server/src/main/java"))
                .map(Path::toString)
                .collect(Collectors.joining(", "));
        System.out.println(paths);

        List<Path> files = new ArrayList<>();

        Files.walkFileTree(Paths.get("cloud-server/src/main/java"),
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        files.add(file);
                        return super.visitFile(file, attrs);
                    }
                });

        System.out.println(files);

       // Iterator<Path> iterator = files.get(0).iterator();
        for (Path p : files.get(0)) {
            System.out.println(p);
        }
    }

}
