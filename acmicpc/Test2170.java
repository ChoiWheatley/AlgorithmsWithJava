package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

import acmicpc.Ex2170.Line;
import acmicpc.Ex2170.Solution;
import acmicpc.Ex2170.Solution1_1;

public class Test2170 {
    @Test
    public void lines1() {
        Solution solution = new Solution();
        solution.add(Line.of(0, 1));
        Line answer = Line.of(0, 1);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    @Test
    public void lines1_1() {
        Solution1_1 solution = new Solution1_1();
        solution.add(Line.of(0, 1));
        Line answer = Line.of(0, 1);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    @Test
    public void lines2() {
        Solution solution = new Solution();
        IntStream.range(0, 100).forEach(e -> solution.add(Line.of(0, 1)));
        Line answer = Line.of(0, 1);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    @Test
    public void lines2_1() {
        Solution1_1 solution = new Solution1_1();
        IntStream.range(0, 100).forEach(e -> solution.add(Line.of(0, 1)));
        Line answer = Line.of(0, 1);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    @Test
    public void lines3() {
        int rangeMin = -1_000_000;
        int rangeMax = 1_000_000;
        Solution solution = new Solution();
        IntStream.range(rangeMin, rangeMax + 1).forEach(e -> solution.add(Line.of(0, e)));
        Line answer = Line.of(rangeMin, rangeMax);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    @Test
    public void lines3_1() {
        int rangeMin = -1_000_000;
        int rangeMax = 1_000_000;
        Solution1_1 solution = new Solution1_1();
        IntStream.range(rangeMin, rangeMax + 1).forEach(e -> solution.add(Line.of(0, e)));
        Line answer = Line.of(rangeMin, rangeMax);
        Collection<Line> submit = solution.getLines();
        assertEquals(1, submit.size());
        assertTrue(submit.contains(answer));
    }

    /** 띄엄띄엄 있는 라인의 경우, 한 번에 병합이 가능한지? */
    @Test
    public void lines4() {
        List<Line> sampleLines = Arrays.asList(
                Line.of(0, 1),
                Line.of(2, 3),
                Line.of(4, 5),
                Line.of(6, 7),
                Line.of(8, 9),
                Line.of(10, 11),
                Line.of(12, 13),
                Line.of(14, 15),
                Line.of(16, 17));
        Solution solution = new Solution(sampleLines);
        assertEquals(sampleLines.size(), solution.getLines().size());
        Line merger = Line.of(0, 100);
        solution.add(merger);
        assertEquals(1, solution.getLines().size());
        assertTrue(solution.getLines().contains(merger));
    }

    @Test
    public void lines4_1() {
        List<Line> sampleLines = Arrays.asList(
                Line.of(0, 1),
                Line.of(2, 3),
                Line.of(4, 5),
                Line.of(6, 7),
                Line.of(8, 9),
                Line.of(10, 11),
                Line.of(12, 13),
                Line.of(14, 15),
                Line.of(16, 17));
        Solution1_1 solution = new Solution1_1(sampleLines);
        assertEquals(sampleLines.size(), solution.getLines().size());
        Line merger = Line.of(0, 100);
        solution.add(merger);
        assertEquals(1, solution.getLines().size());
        assertTrue(solution.getLines().contains(merger));
    }

    @Test
    public void timeout1() {
        final int MAX = 1_000_000;
        Random r = new Random();
        List<Line> lines = new ArrayList<>(MAX);
        r.ints().limit((long) MAX * 2).reduce((a, b) -> {
            lines.add(Line.of(a, b));
            return b;
        });

        Solution solution = new Solution(lines);
    }

    @Test
    public void timeout1_1() {
        final int MAX = 1_000_000;
        Random r = new Random();
        List<Line> lines = new ArrayList<>(MAX);
        r.ints().limit((long) MAX * 2).reduce((a, b) -> {
            lines.add(Line.of(a, b));
            return b;
        });

        Solution1_1 solution = new Solution1_1(lines);
    }
}
