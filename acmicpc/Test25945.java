package acmicpc;

import static acmicpc.Solver25945.solve;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class Test25945 {
  @Test
  public void test1() {
    assertEquals(1, solve(new int[] { 1, 2, 3, 3 }));
  }

  @Test
  public void test2() {
    assertEquals(5, solve(new int[] { 5, 6, 7, 2, 3, 4, 2, 6 }));
  }

  @Test
  public void test3() {
    assertEquals(7, solve(new int[] { 3, 3, 3, 3, 3, 3, 3, 11 }));
  }

  @Test
  public void test4() {
    assertEquals(1, solve(new int[] { 3, 3, 3, 4, 5 }));
  }

  @Test
  public void test5() {
    assertEquals(4, solve(new int[] { 3, 3, 3, 9 }));
  }

  @Test
  public void test6() {
    assertEquals(6, solve(new int[] { 3, 3, 3, 4, 5, 6, 7, 8 }));
  }
}
