package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import acmicpc.Ex1911.Range;
import acmicpc.Ex1911.Solution;
import acmicpc.Ex1911.Solution2;
import acmicpc.Ex1911.Solution3;

public class Test1911 {
    @Test
    public void sol1() {
        int plankLen = 3;
        List<Range> holes = Arrays.asList(
                new Range(1, 6),
                new Range(13, 17),
                new Range(8, 12));
        int answer = 5;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);
        assertEquals(answer, submit);
        assertEquals(answer, submit2);

        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol1$1() {
        int plankLen = 4;
        List<Range> holes = Arrays.asList(
                new Range(1, 6),
                new Range(13, 17),
                new Range(8, 12));
        int answer = 4;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);
        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol1$2() {
        List<Range> holes = Arrays.asList(
                new Range(1, 6),
                new Range(13, 17),
                new Range(8, 12));
        int plankLen = 5;
        int answer = 3;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);
        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol1$3() {
        List<Range> holes = Arrays.asList(
                new Range(1, 6),
                new Range(13, 17),
                new Range(8, 12));
        int plankLen = 6;
        int answer = 3;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);
        // assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol2() {
        int plankLen = 1;
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(1_000_000_000 - 1, 1_000_000_000));
        int answer = 2;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void timeout1() {
        int plankLen = 1;
        List<Range> holes = Arrays.asList(
                new Range(0, 1_000_000_000));
        int answer = 1_000_000_000;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol3() {
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(2, 3),
                new Range(4, 5),
                new Range(6, 7),
                new Range(8, 9));
        int plankLen = 10;
        int answer = 1;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol3$1() {
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(2, 3),
                new Range(4, 5),
                new Range(6, 7),
                new Range(8, 9));
        int plankLen = 2;
        int answer = 5;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol3$2() {
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(2, 3),
                new Range(4, 5),
                new Range(6, 7),
                new Range(8, 9));
        int plankLen = 1;
        int answer = 5;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol3$3() {
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(2, 3),
                new Range(4, 5),
                new Range(6, 7),
                new Range(8, 9));
        int plankLen = 100;
        int answer = 1;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol4() {
        int plankLen = 10;
        List<Range> holes = Arrays.asList(
                new Range(1, 2),
                new Range(3, 4),
                new Range(5, 6),
                new Range(7, 8),
                new Range(9, 10));
        int answer = 1;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol5() {
        int plankLen = 1_000_000_000;
        List<Range> holes = Arrays.asList(
                new Range(0, 1));
        int answer = 1;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol6() {
        int plankLen = 10;
        List<Range> holes = Arrays.asList(
                new Range(0, 1),
                new Range(10, 11));
        int answer = 2;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol7() {
        int plankLen = 10;
        List<Range> holes = Arrays.asList(
                new Range(2, 7),
                new Range(9, 10),
                new Range(11, 12));
        int answer = 1;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }

    @Test
    public void sol8() {
        int plankLen = 1;
        List<Range> holes = Arrays.asList(
                Range.of(0, 2),
                Range.of(1, 3),
                Range.of(2, 4),
                Range.of(3, 5),
                Range.of(4, 6),
                Range.of(5, 7));
        int answer = 6;
        int submit = Solution.solution(holes, plankLen);
        int submit2 = Solution2.solution(holes, plankLen);

        assertEquals(answer, submit);
        assertEquals(answer, submit2);
        int submit3 = Solution3.solution(holes, plankLen);
        assertEquals(answer, submit3);
    }
}
