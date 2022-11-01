package acmicpc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

public class Test24060 extends Ex24060 {
  @Test
  public void testcase1() {
    /**
     * 5 7
     * 4 5 1 3 2
     */
    var unordered = new Long[] { 4l, 5l, 1l, 3l, 2l };
    var k = 7;
    var solver = new Solver24060();
    assertEquals(3L, solver.solve(unordered, k));
  }

  @Test
  public void failTest1() {
    var unordered = new Long[] { 4l, 5l, 1l, 3l, 2l };
    var k = 13;
    var solver = new Solver24060();
    assertEquals(-1l, solver.solve(unordered, k));
  }

  @Test
  public void loongRandomStreamSpeedTest() throws IOException {
    Long[] unordered = (new Random()).longs(500_000)
        .boxed()
        .toArray(Long[]::new);
    System.out.println(unordered[unordered.length - 1]);
  }

  @Test
  public void timeoutTest1() {
    /**
     * 500_000개의 원소를 정렬할 때 과연 얼마나 오래 걸리는지
     */
    Long[] unordered = (new Random()).longs(500_000)
        .boxed()
        .toArray(Long[]::new);
    var k = (int) Math.pow(10, 8);
    var solver = new Solver24060();
    solver.solve(unordered, k);
  }
}
