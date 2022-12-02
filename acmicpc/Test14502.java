package acmicpc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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

  @Test
  public void diffuseTest() {
    var lab = new Lab(5, 5);
    lab.setVirus(Idx2D.of(0, 0));
    System.out.println(Arrays.deepToString(lab.getStatus()));
    lab.diffuse();
    System.out.println(Arrays.deepToString(lab.getStatus()));
  }

}
