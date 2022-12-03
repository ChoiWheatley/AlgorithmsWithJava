package acmicpc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static acmicpc.Solver14502.*;
import static acmicpc.Solver14502.Lab.*;

import org.junit.Test;

import useful.Idx2D;

public class Test14502 {
  public static final Stat V = Stat.VIRUS;
  public static final Stat W = Stat.WALL;
  public static final Stat E = Stat.EMPTY;

  @Test
  public void singletonTest() {
    var o1 = Idx2D.of(0, 0);
    var o2 = Idx2D.of(0, 0);
    assertEquals(o1, o2);
    assertTrue("""
        o1 = %s
        o2 = %s""", o1 == o2);
  }

  @Test
  public void diffuseTest() {
    var lab = new Lab(5, 5);
    lab.setVirus(Idx2D.of(0, 0));
    Stat[][] answer = {
        { V, E, E, E, E },
        { E, E, E, E, E },
        { E, E, E, E, E },
        { E, E, E, E, E },
        { E, E, E, E, E },
    };
    assertArrayEquals(answer, lab.getStats());
    lab.diffuse();
    answer = new Stat[][] {
        { V, V, V, V, V },
        { V, V, V, V, V },
        { V, V, V, V, V },
        { V, V, V, V, V },
        { V, V, V, V, V },
    };
    assertArrayEquals(answer, lab.getStats());
  }

  @Test
  public void diffuseTest2() {
    var lab = new Lab(5, 5);
    lab.setVirus(Idx2D.of(0, 0));
    lab.setWall(Idx2D.of(0, 1));
    lab.setWall(Idx2D.of(1, 1));
    lab.setWall(Idx2D.of(2, 0));
    lab.diffuse();
    Stat[][] answer = {
        { V, W, E, E, E },
        { V, W, E, E, E },
        { W, E, E, E, E },
        { E, E, E, E, E },
        { E, E, E, E, E },
    };
    assertArrayEquals(answer, lab.getStats());
  }

  @Test
  public void countTest() {
    var lab = new Lab(3, 3);
    int answer = 9;
    int submit = (int) lab.stream().filter(e -> e == Stat.EMPTY).count();
    assertEquals(answer, submit);

    lab.set(Idx2D.of(0, 0), Stat.VIRUS);
    answer = 8;
    submit = (int) lab.stream().filter(e -> e == Stat.EMPTY).count();
    assertEquals(answer, submit);

    lab.diffuse();
    answer = 0;
    submit = (int) lab.stream().filter(e -> e == Stat.EMPTY).count();
    assertEquals(answer, submit);
  }

  @Test
  public void nextIdxTest() {
    var lab = new Lab(3, 3);
    var cursor = Idx2D.of(0, 0);
    var answer = new Idx2D[] {
        Idx2D.of(0, 0),
        Idx2D.of(0, 1),
        Idx2D.of(0, 2),
        Idx2D.of(1, 0),
        Idx2D.of(1, 1),
        Idx2D.of(1, 2),
        Idx2D.of(2, 0),
        Idx2D.of(2, 1),
        Idx2D.of(2, 2),
    };
    for (var a : answer) {
      assertEquals(a, cursor);
      cursor = lab.nextIdxOf(cursor);
    }
  }

  @Test
  public void sample1() {
    var raw = new int[][] {
        { 2, 0, 0, 0, 1, 1, 0, },
        { 0, 0, 1, 0, 1, 2, 0, },
        { 0, 1, 1, 0, 1, 0, 0, },
        { 0, 1, 0, 0, 0, 0, 0, },
        { 0, 0, 0, 0, 0, 1, 1, },
        { 0, 1, 0, 0, 0, 0, 0, },
        { 0, 1, 0, 0, 0, 0, 0, },
    };
    int answer = 27;
    int submit = Solver14502.solve(raw);
    assertEquals(answer, submit);
  }

  @Test
  public void sample2() {
    var raw = new int[][] {
        { 0, 0, 0, 0, 0, 0, },
        { 1, 0, 0, 0, 0, 2, },
        { 1, 1, 1, 0, 0, 2, },
        { 0, 0, 0, 0, 0, 2, },
    };
    int answer = 9;
    int submit = Solver14502.solve(raw);
    assertEquals(answer, submit);
  }

  @Test
  public void sample3() {
    var raw = new int[][] {
        { 2, 0, 0, 0, 0, 0, 0, 2, },
        { 2, 0, 0, 0, 0, 0, 0, 2, },
        { 2, 0, 0, 0, 0, 0, 0, 2, },
        { 2, 0, 0, 0, 0, 0, 0, 2, },
        { 2, 0, 0, 0, 0, 0, 0, 2, },
        { 0, 0, 0, 0, 0, 0, 0, 0, },
        { 0, 0, 0, 0, 0, 0, 0, 0, },
        { 0, 0, 0, 0, 0, 0, 0, 0, },
    };
    int answer = 3;
    int submit = Solver14502.solve(raw);
    assertEquals(answer, submit);
  }

  @Test
  public void timeoutTest() {
    var raw = new int[8][8];
    raw[0][0] = Stat.VIRUS.ordinal();
    int answer = 64 - 4;
    int submit = Solver14502.solve(raw);
    assertEquals(answer, submit);
  }

}
