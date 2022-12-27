package acmicpc;

import static acmicpc.Ex2401.Solution.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import acmicpc.Ex2401.Solution2;
import acmicpc.Ex2401.SparseMatrix;
import useful.Idx2D;

public class Test2401 {
  private String L;
  private List<String> S;
  private int answer;
  private int submit;
  private int submit2;

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
    submit2 = Solution2.solution(L, S);
    assertEquals(answer, submit2);

    assertEquals(answer, submit);
  }

  /** 새로운 문자열에 의해 대체되는 경우 */
  @Test
  public void sol2() {
    L = "abcd";
    S = Arrays.asList("a", "b", "c", "abcd");
    answer = L.length();
    submit = solution(L, S);
    submit = solution(L, S);
    submit2 = Solution2.solution(L, S);
    assertEquals(answer, submit2);

    assertEquals(answer, submit);
  }

  /** 중간에 문자열을 대체하는 경우 */
  @Test
  public void sol3() {
    L = "abcdABCD";
    S = Arrays.asList("abcd", "ABC", "ABCD");
    answer = L.length();
    submit = solution(L, S);
    submit = solution(L, S);
    submit2 = Solution2.solution(L, S);
    assertEquals(answer, submit2);

    assertEquals(answer, submit);
  }

  @Test
  public void sol4() {
    String L = "aabcc";
    S = Arrays.asList("aab", "bcc");
    answer = 3;
    submit = solution(L, S);
    submit = solution(L, S);
    submit2 = Solution2.solution(L, S);
    assertEquals(answer, submit2);

    assertEquals(answer, submit);
  }

  @Test
  public void sol5() {
    L = "abcdefghijklmnopqrstuvwxyz";
    S = Arrays.asList("abcdefg", "bcdefghijkl", "cdefghij", "mnopqrstuvwxyz");
    answer = 25;
    submit = solution(L, S);
    submit = solution(L, S);
    submit2 = Solution2.solution(L, S);
    assertEquals(answer, submit2);

    assertEquals(answer, submit);
  }

  @Test
  public void sparseMat1() {
    SparseMatrix<Integer> mat = new SparseMatrix<>(5, 13);
    List<Idx2D> indices = Arrays.asList(Idx2D.of(0, 3), Idx2D.of(0, 8), Idx2D.of(0, 11),
        Idx2D.of(1, 5),
        Idx2D.of(2, 3), Idx2D.of(2, 12),
        Idx2D.of(3, 5),
        Idx2D.of(4, 0), Idx2D.of(4, 1), Idx2D.of(4, 4), Idx2D.of(4, 6), Idx2D.of(4, 7), Idx2D.of(4, 9),
        Idx2D.of(4, 10));
    indices.forEach(idx -> mat.set(idx.row(), idx.col(), 1));
    assertEquals(Optional.empty(), mat.get(0, 0));
    indices.forEach(idx -> {
      assertEquals(true, mat.get(idx.row(), idx.col()).isPresent());
    });

    Set<Map.Entry<Idx2D, Integer>> set = mat.getR(0);
    Set<Map.Entry<Idx2D, Integer>> answerSet = new HashSet<>();
    for (int i = 0; i < 3; ++i) {
      answerSet.add(Map.entry(indices.get(i), 1));
    }
    assertEquals(set, answerSet);
  }
}
