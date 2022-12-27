package acmicpc;

import static acmicpc.Ex2401.Solution.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Test2401 {
  private String L;
  private List<String> S;
  private int answer;
  private int submit;

  @Test
  public void failTable1() {
    L = "aabc";
    S = Arrays.asList("a", "bc");
    int[][] answer = {
        { 1, 1, 0, 0 },
        { 0, 0, 0, 2 }
    };
    int[][] submit = failureTable(L, S);

    assertArrayEquals(answer, submit);
  }

  @Test
  public void failTable2() {
    L = "aabcabaabaabc";
    S = Arrays.asList(
        "aab",
        "aabcab",
        "c",
        "abcab",
        "a",
        "bc");
    int[][] answer = {
        // a, a, b, c, a, b, a, a, b, a, a, b, c
        { 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 3, 0 }, // aab
        { 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0 }, // aabcab
        { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // c
        { 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0 }, // abcab
        { 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0 }, // a
        { 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2 }, // bc
    };
    int[][] submit = failureTable(L, S);

    assertArrayEquals(answer, submit);
  }

  @Test
  public void sol1() {
    L = "aabcabaabaabc";
    S = Arrays.asList(
        "aab",
        "aabcab",
        "c",
        "abcab",
        "a",
        "bc");
    answer = L.length();
    submit = solution(L, S);

    assertEquals(answer, submit);
  }

  /** 새로운 문자열에 의해 대체되는 경우 */
  @Test
  public void sol2() {
    L = "abcd";
    S = Arrays.asList("a", "b", "c", "abcd");
    answer = L.length();
    submit = solution(L, S);

    assertEquals(answer, submit);
  }

  /** 중간에 문자열을 대체하는 경우 */
  @Test
  public void sol3() {
    L = "abcdABCD";
    S = Arrays.asList("abcd", "ABC", "ABCD");
    answer = L.length();
    submit = solution(L, S);

    assertEquals(answer, submit);
  }

  @Test
  public void sol4() {
    String L = "aabcc";
    S = Arrays.asList("aab", "bcc");
    answer = 3;
    submit = solution(L, S);

    assertEquals(answer, submit);
  }

  @Test
  public void sol5() {
    L = "abcdefghijklmnopqrstuvwxyz";
    S = Arrays.asList("abcdefg", "bcdefghijkl", "cdefghij", "mnopqrstuvwxyz");
    answer = 25;
    submit = solution(L, S);

    assertEquals(answer, submit);
  }
}
