package acmicpc;

import static acmicpc.Solver1813.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.Test;

public class Test1813 {
  public static final boolean T = true;
  public static final boolean F = false;

  @Test
  public void countTest() {
    Boolean[] checklist = new Boolean[] { T, F, F, T };
    assertEquals(2, count(checklist, true));
  }

  @Test
  public void solve1() {
    Integer[] ls = new Integer[] { 0, 1, 2, 3 };
    assertEquals(1, solve(ls));
  }

  @Test
  public void solve2() {
    var ls = new Integer[] { 0 };
    assertEquals(-1, solve(ls));
  }

  @Test
  public void solve3() {
    var ls = new Integer[] { 0, 3, 1, 3, 2, 3 };
    assertEquals(3, solve(ls));
  }

  @Test
  public void solve4() {
    var ls = new Integer[] { 1, 1 };
    assertEquals(0, solve(ls));
  }

  @Test
  public void timeoutTest() {
    Random random = new Random();
    Integer[] ls = random.ints().filter(e -> e <= 50).limit(50).boxed().toArray(Integer[]::new);
    solve(ls);
  }
}
