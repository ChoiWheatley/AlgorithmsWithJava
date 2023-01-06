package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import acmicpc.Ex15578.Solution;

public class Test15578 {
    public void test(int N, int K, int M, List<Integer> answer) {
        assertEquals(answer, Ex15578.Solution.solution(N, K, M));
    }

    @Test
    public void sample1() {
        test(2, 1, 3, Arrays.asList(2, 1));
    }

    @Test
    public void sample2() {
        test(3, 2, 7, Arrays.asList(2, 3, 2));
    }

    @Test
    public void sample3() {
        test(4, 5, 6, Arrays.asList(5, 1, 0, 0));
    }

    @Test
    public void timeout1() {
        Solution.solution(200_000, 12345, 2_000_000_000);
    }
}
