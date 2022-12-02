package acmicpc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import useful.Idx2D;

public class Test14502 {
  @Test
  public void singletonTest() {
    var o1 = Idx2D.of(0, 0);
    var o2 = Idx2D.of(0, 0);
    assertEquals(o1, o2);
    assertTrue("""
        o1 = %s
        o2 = %s""", o1 == o2);
  }
}
