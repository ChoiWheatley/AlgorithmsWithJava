package acmicpc;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.Test;

public class Test2661 {
  @Test
  public void testGoodSequence() {
    int[] ls = new int[] { 1 };
    boolean answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 1 };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1 };
    answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 1 };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 2 };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3 };
    answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1 };
    answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 2 };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 1, 3 };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, };
    answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));
  }

  @Test
  public void edgeTest1() {
    var ls = new int[] { 1, 2, 3, 2, 3, 1 };
    var answer = false;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 2,
        3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1 };
    answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 2,
        3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1 };
    answer = true;
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));

    ls = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 2,
        3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 2, 1, 2, 3, 1, 3, 2, 1, 2 };
    assertEquals(answer, Solver2661.isGoodSequence(ls, ls.length));
  }

  @Test
  public void test1() {
    int[] answer = new int[] { 1 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2, 1 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2, 1, 3 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 2,
        3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));

    answer = new int[] { 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 3, 1, 2, 1, 3, 2, 1, 2,
        3, 1, 2, 1, 3, 1, 2, 3, 1, 3, 2, 1, 2, 3, 1, 2, 1, 3, 2, 1, 2, 3, 1, 3, 2, 1, 2 };
    assertArrayEquals(answer, Solver2661.solve(answer.length));
  }
}
