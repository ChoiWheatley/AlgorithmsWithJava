package acmicpc;

import static acmicpc.Ex4354.Solution.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class Test4353 {
  public String pattern;
  public int answer;

  @Test
  public void sample1() {
    pattern = "abcd";
    answer = 1;
    assertEquals(answer, solution(pattern));
  }

  @Test
  public void sample2() {
    pattern = "aaaa";
    answer = 4;
    assertEquals(answer, solution(pattern));
  }

  @Test
  public void sample3() {
    pattern = "ababab";
    answer = 3;
    assertEquals(answer, solution(pattern));
  }

  @Test
  public void sample4() {
    pattern = "abaabaaba";
    answer = 3;
    assertEquals(answer, solution(pattern));
  }

  @Test
  public void sample5() {
    pattern = "abaabaabaaba";
    answer = 4;
    assertEquals(answer, solution(pattern));
  }

  @Test
  public void failureFunction1() {
    String keyword = "aaab";
    int[] answer = { 0, 1, 2, 0 };
    int[] submit = getFail(keyword);
    assertArrayEquals(answer, submit, String.format("""
        answer = %s
        submit = %s
          """, Arrays.toString(answer), Arrays.toString(submit)));
  }
}
