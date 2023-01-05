package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import acmicpc.Ex27065.Solution;

public class Test27065 {
    Map<Integer, Set<Integer>> answers = Map.ofEntries(
            Map.entry(0, Set.of()),
            Map.entry(1, Set.of(1)),
            Map.entry(2, Set.of(1, 2)),
            Map.entry(3, Set.of(1, 3)),
            Map.entry(4, Set.of(1, 2, 4)),
            Map.entry(5, Set.of(1, 5)),
            Map.entry(6, Set.of(1, 2, 3, 6)),
            Map.entry(7, Set.of(1, 7)),
            Map.entry(8, Set.of(1, 2, 4, 8)),
            Map.entry(9, Set.of(1, 3, 9)),
            Map.entry(10, Set.of(1, 2, 5, 10)));

    @Test
    public void divisor2() {
        answers.forEach((idx, elem) -> assertEquals(elem, Solution.getDivisors(idx),
                String.format("\nat idx = %d\n", idx)));
    }
}
