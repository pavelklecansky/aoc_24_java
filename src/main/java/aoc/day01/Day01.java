package aoc.day01;

import aoc.Day;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day01 implements Day<Integer> {

    @Override
    public Integer part1(String input) {
        ListTuple listTuple = parseAndSortInput(input);

        return IntStream
                .range(0, listTuple.minListSize())
                .map(i -> Math.abs(listTuple.first().get(i) - listTuple.second.get(i)))
                .sum();
    }

    @Override
    public Integer part2(String input) {
        ListTuple listTuple = parseAndSortInput(input);

        Map<Integer, Long> frequencies = frequencyMap(listTuple.second());

        return listTuple.first()
                .stream().reduce(0, (accumulator, value) -> {
                    Long frequencyOfValue = frequencies.getOrDefault(value, 0L);
                    return Math.toIntExact(accumulator + (frequencyOfValue * value));
                });
    }

    private ListTuple parseAndSortInput(String input) {
        List<String[]> lines = Stream.of(input.split("\n"))
                .map(s -> s.split("   "))
                .toList();

        List<Integer> first = lines.stream().map(strings -> Integer.parseInt(strings[0])).sorted().toList();
        List<Integer> second = lines.stream().map(strings -> Integer.parseInt(strings[1])).sorted().toList();

        return new ListTuple(first, second);
    }

    private <T> Map<T, Long> frequencyMap(List<T> elements) {
        return elements.stream().collect(
                Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )
        );
    }


    record ListTuple(List<Integer> first, List<Integer> second) {

        public int minListSize() {
            return Math.min(first.size(), second.size());
        }
    }

}
