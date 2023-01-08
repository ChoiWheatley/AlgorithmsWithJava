package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import acmicpc.Ex1911.Hole;
import acmicpc.Ex1911.Solution;

public class Test1911 {
    @Test
    public void sol1() {
        /**
         * 3 3
         * 1 6
         * 13 17
         * 8 12
         */
        int plankLen = 3;
        List<Hole> holes = Arrays.asList(
                new Hole(1, 6),
                new Hole(13, 17),
                new Hole(8, 12));
        int answer = 5;
        int submit = Solution.solution(holes, plankLen);

        assertEquals(answer, submit);
    }

    @Test
    public void sol2() {
        int planklen = 1;
        List<Hole> holes = Arrays.asList(
                new Hole(0, 1),
                new Hole(1_000_000_000 - 1, 1_000_000_000));
        int answer = 2;
        int submit = Solution.solution(holes, planklen);

        assertEquals(answer, submit);
    }

    @Test
    public void timeout1() {
        int planklen = 1;
        List<Hole> holes = Arrays.asList(
                new Hole(0, 1_000_000_000));
        int answer = 1_000_000_000;
        int submit = Solution.solution(holes, planklen);

        assertEquals(answer, submit);
    }

    @Test
    public void sol3() {
        int planklen = 10;
        List<Hole> holes = Arrays.asList(
                new Hole(0, 1),
                new Hole(2, 3),
                new Hole(4, 5),
                new Hole(6, 7),
                new Hole(8, 9));
        int answer = 1;
        int submit = Solution.solution(holes, planklen);
        assertEquals(answer, submit);
    }

    @Test
    public void sol4() {
        int planklen = 10;
        List<Hole> holes = Arrays.asList(
                new Hole(1, 2),
                new Hole(3, 4),
                new Hole(5, 6),
                new Hole(7, 8),
                new Hole(9, 10));
        int answer = 1;
        int submit = Solution.solution(holes, planklen);
        assertEquals(answer, submit);
    }

    @Test
    public void sol5() {
        int planklen = 1_000_000_000;
        List<Hole> holes = Arrays.asList(
                new Hole(0, 1));
        int answer = 1;
        int submit = Solution.solution(holes, planklen);
        assertEquals(answer, submit);
    }

    @Test
    public void sol6() {
        int planklen = 10;
        List<Hole> holes = Arrays.asList(
                new Hole(0, 1),
                new Hole(10, 11));
        int answer = 2;
        int submit = Solution.solution(holes, planklen);
        assertEquals(answer, submit);
    }
}
