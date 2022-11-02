package acmicpc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Test3649 {

  @Test
  public void searchTest1() {
    final int SIZE = 500;

    List<Long> ls = (new Random()).longs(SIZE).sorted().boxed().toList();
    SearchParty<Long> sp = new SearchParty<>(ls);
    for (int idx = 0; idx < ls.size(); ++idx) {
      assertEquals("idx = " + idx, idx, sp.find(ls.get(idx)));
    }
  }

  @Test
  public void searchFailTest1() {
    List<Long> ls = Arrays.asList(1l, 2l, 3l, 4l, 5l);
    SearchParty<Long> sp = new SearchParty<>(ls);
    assertEquals(-1, sp.find(300l));
  }

  @Test
  public void caseTest1() {
    long holeSize = 1;
    List<Long> ls = Arrays.asList(4l, 9999998l, 1l, 2l, 9999999l);
    Solver3649 solver = new Solver3649();
    var answer = new Answer3649(true, 1l, 9999999l);
    var solve = solver.solve(holeSize, ls);
    // assertTrue("answer: " + answer + ", solve: " + solve, answer.equals(solve));
    assertEquals(answer, solve);
  }

  @Test
  public void caseTest2() {
    long holeSize = 1;
    List<Long> ls = Arrays.asList(1l, 9999999l, 2l, 9999998l, 3l, 9999997l);
    Answer3649 answer = new Answer3649(true, 1l, 9999999l);
    Answer3649 solve = (new Solver3649()).solve(holeSize, ls);
    assertEquals(answer, solve);
  }

  @Test
  public void caseTest3() {
    long holeSize = 1;
    List<Long> ls = Arrays.asList(1l, 2l, 3l, 4l);
    Answer3649 answer = Answer3649.getFalse();
    Answer3649 solve = (new Solver3649()).solve(holeSize, ls);
    assertEquals(answer, solve);
  }

  @Test
  public void caseTest4() {
    long holeSize = 1;
    List<Long> ls = Arrays.asList(5_000_000l, 5_000_000l);
    Answer3649 answer = new Answer3649(true, 5000000l, 5000000l);
    Answer3649 solve = (new Solver3649()).solve(holeSize, ls);
    assertEquals(answer, solve);
  }

  @Test
  public void caseTest5() {
    long holeSize = 1;
    final long PIVOT = 5_000_000l;
    List<Long> ls = Arrays.asList(PIVOT - 1, PIVOT + 1, PIVOT - 2, PIVOT + 2);
    Answer3649 answer = new Answer3649(true, PIVOT - 2, PIVOT + 2);
    Answer3649 solve = (new Solver3649()).solve(holeSize, ls);
    assertEquals(answer, solve);
  }

  @Test
  public void equalsTest() {
    var v1 = new Answer3649(true, 1l, 999l);
    var v2 = new Answer3649(true, 1l, 999l);
    assertEquals(v1, v2);
  }
}
