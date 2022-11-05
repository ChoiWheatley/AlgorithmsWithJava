package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import acmicpc.Solver1700.Option;

public class Test1700 {
  @Test
  public void test1() {
    int holeCount = 2;
    int[] schedule = new int[] { 2, 3, 2, 3, 1, 2, 7 };
    int answer = 2;

    for (holeCount = 1; holeCount < 10; holeCount++) {
      var solver = new Solver1700(schedule, holeCount);
      assertEquals(
          solver.solve(Option.NAIVE),
          solver.solve(Option.SMART),
          "holeCount: " + holeCount);
    }
  }

  @Test
  public void test2() {
    int holeCount = 3;
    int[] schedule = new int[] { 1, 2, 3, 4, 1, 2 };
    int answer = 1;

    for (holeCount = 1; holeCount < 10; holeCount++) {
      var solver = new Solver1700(schedule, holeCount);
      assertEquals(
          solver.solve(Option.NAIVE),
          solver.solve(Option.SMART));
    }
  }

  @Test
  public void test3() {
    int holeCount = 5;
    int[] schedule = new int[] { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };
    var solver = new Solver1700(schedule, holeCount);
    assertEquals(0, solver.solve(Option.SMART));
  }

  @Test
  public void randomTest() {
    Random random = new Random();
    int k = random.nextInt(1, 20);
    int[] schedule = random
        .ints(k, 1, k + 1)
        .toArray();

    for (int holeCount = 1; holeCount < k; ++holeCount) {
      var solver = new Solver1700(schedule, holeCount);
      assertEquals(solver.solve(Option.NAIVE), solver.solve(Option.SMART));
    }
  }

  @Test
  public void edgeCase() {
    int holeCount = 3;
    int[] schedule = new int[] { 1, 1, 1, 1, 2 };
    var solver = new Solver1700(schedule, holeCount);
    assertEquals(0, solver.solve(Option.SMART));
  }

  @Test
  @RepeatedTest(100)
  public void timeoutTest() {
    Random random = new Random();
    int k = random.nextInt(1, 101);
    int[] schedule = random
        .ints(k, 1, k + 1)
        .toArray();
    for (int holeCount = 1; holeCount < 100; ++holeCount) {
      var solver = new Solver1700(schedule, holeCount);
      solver.solve(Option.SMART);
    }
  }

}
