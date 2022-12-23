package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import static acmicpc.Ex16916.Solution.*;

import org.junit.Test;

public class Test16916 {
  @Test
  public void sample1() {
    String a = "baekjoon";
    String b = "aek";
    boolean answer = true;
    boolean submit = solution(a, b);
    assertEquals(answer, submit);
  }

  @Test
  public void sample2() {
    String a = "baekjoon";
    String b = "bak";
    boolean answer = false;
    boolean submit = solution(a, b);
    assertEquals(answer, submit);
  }

  @Test
  public void sample3() {
    String a = "baekjoon";
    String b = "joo";
    boolean answer = true;
    boolean submit = solution(a, b);
    assertEquals(answer, submit);
  }

  @Test
  public void failureFunction1() {
    String keyword = "aaab";
    int[] answer = { 0, 1, 2, 0 };
    int[] submit = makeFail(keyword);
    assertArrayEquals(answer, submit, String.format("""
        answer = %s
        submit = %s
          """, Arrays.toString(answer), Arrays.toString(submit)));
  }
}
