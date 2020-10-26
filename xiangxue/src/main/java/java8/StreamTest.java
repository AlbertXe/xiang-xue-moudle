package java8;

import ch1_concurrent.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Intermediate：
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 * <p>
 * Terminal：
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 */
public class StreamTest {
    List<User> users = new ArrayList<>();

    @Before
    public void before() {
        users.add(new User("1", "xie"));
        users.add(new User("2", "xie"));
        users.add(new User("3", "xie"));
    }

    @Test
    public void test1() {
        //1 过滤剩下xie  2 排序id降序 3 重新定义集合 4 出口
        List<String> ids = users.parallelStream().filter(t -> t.getName().equals("xie"))
                .sorted(Comparator.comparing(User::getId).reversed())
                .map(User::getId)
                .collect(Collectors.toList());
        System.out.println(ids);

    }

    /**
     * 数字流
     */
    @Test
    public void test2() {
        IntStream.of(1, 2, 3).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * 流输出不同
     */
    @Test
    public void test3() {
        Stream<String> stream = Stream.of("a", "b", "c");
        String s = stream.collect(Collectors.joining(","));
        System.out.println(s);
    }

    /**
     * 一对多
     */
    @Test
    public void test4() {
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1), Arrays.asList(1, 2), Arrays.asList(4, 5, 6));
        //flatMap将中间list结构扁平化  就是将最底层元素抽出来放到一起
        List<Integer> result = stream.flatMap(t -> t.stream()).collect(Collectors.toList());
        System.out.println(result);
    }

    /**
     * forEach会结束流 而peek不会
     */
    @Test
    public void test5() {
        Stream.of("1", "2", "3", "4").peek(System.out::println)
                .map(t -> t + t)
                .peek(System.out::println).collect(Collectors.toList());
    }

    /**
     * Optional解决null
     */
    @Test
    public void test6() {
        String s = null;
        Integer i = Optional.ofNullable(s).map(String::length).orElse(-1);
        System.out.println(i);
    }

    /**
     * 把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、
     * 第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。
     */
    @Test
    public void test7() {
        System.out.println(Stream.of("A", "B", "C").reduce("", String::concat));
        //求最小值
        System.out.println(Stream.of(-1, -4, 5, -9).reduce(10, (a, b) -> a <= b ? a : b));
        //求和 有起始值 实际时1+2+3+4
        System.out.println(IntStream.of(1, 2, 3).reduce(4, Integer::sum));
    }

    /**
     * limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素
     */
    @Test
    public void test8() {
        IntStream.rangeClosed(1, 20).limit(10).skip(3).forEach(System.out::println);
    }

    //distinct 去重
    //concat
    @Test
    public void test9() {
        IntStream.concat(IntStream.range(1, 4), IntStream.range(6, 9)).forEach(System.out::println);
    }

    /**
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     */
    @Test
    public void test10() {
        System.out.println(users.stream().allMatch(t -> Integer.valueOf(t.getId()) > 2));
        System.out.println(users.stream().anyMatch(t -> Integer.valueOf(t.getId()) > 2));
        System.out.println(users.stream().noneMatch(t -> Integer.valueOf(t.getId()) > 2));
    }

    // 生成一个等差数列  与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小。
    @Test
    public void test11() {
        Stream.iterate(1, n -> n + 3).limit(10).forEach(System.out::println);
    }

    //生成 10 个随机整数
    @Test
    public void test12() {
        Random random = new Random();
        Supplier<Integer> supplier = random::nextInt;
        System.out.println(Stream.generate(supplier).limit(10).collect(Collectors.toList()));
    }

    /**
     * 将一个集合转成多个集合
     */
    @Test
    public void test13() {
//        IntStream.rangeClosed(1,100).collect()

    }
}
