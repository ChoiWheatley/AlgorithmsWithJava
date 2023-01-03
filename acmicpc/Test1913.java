package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import useful.Idx2D;

public class Test1913 {
  @Test
  public void sol1() {
    int n = 3;
    int[][] answerArr = {
        { 9, 2, 3 },
        { 8, 1, 4 },
        { 7, 6, 5 }
    };
    var submit = Ex1913.Solution.solution(n, 0);
    assertArrayEquals(answerArr, submit.arr, String.format("""
        answer =
        %s

        submit =
        %s\n
        """, Arrays.deepToString(answerArr), Arrays.deepToString(submit.arr)));
  }
}
