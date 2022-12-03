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

}
