package acmicpc;

import static acmicpc.Ex1786.Solution.solution;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import acmicpc.Ex1786.KMP;;

public class Test1786 {
  @Test
  public void sample1() {
    var text = "ABC ABCDAB ABCDABCDABDE";
    var pattern = "ABCDABD";
    List<Integer> answer = Arrays.asList(16);
    assertEquals(answer, solution(text, pattern));
  }

  @Test
  public void failureFunction1() {
    String keyword = "aaab";
    KMP kmp = new KMP(keyword);
    int[] answer = { 0, 1, 2, 0 };
    int[] submit = kmp.fail();

    assertArrayEquals(answer, submit, String.format("""
        answer = %s
        submit = %s
          """, Arrays.toString(answer), Arrays.toString(submit)));
  }

}
