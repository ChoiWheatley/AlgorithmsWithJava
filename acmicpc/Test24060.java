package acmicpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Test;

public class Test24060 extends Ex24060 {
  @Test
  public void testcase1() {
    var answers = new Long[] { null, 4l, 5l, 1l, 4l, 5l, 2l, 3l, 1l, 2l, 3l, 4l, 5l };
    for (int idx = 1; idx < answers.length; ++idx) {
      var unordered = new Long[] { 4l, 5l, 1l, 3l, 2l };
      var solver = new Solver24060();
      var answer = solver.solve(unordered, idx);
      assertEquals("idx = " + idx, (long) answers[idx], answer);
    }
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
  public void sortTest1() {
    LongStream unordered = (new Random()).longs(500_000);
    Long[] arr1 = unordered.boxed().toArray(Long[]::new);

    Long[] sorted1 = Stream.of(arr1)
        .sorted((a, b) -> a.compareTo(b))
        .toArray(Long[]::new);
    MergeSort<Long> ms = new MergeSort<>(arr1,
        (a, b) -> a.compareTo(b),
        new MergeCountObserver<>(-1));
    Long[] sorted2 = ms.sort(0, arr1.length).getArray();

    assertArrayEquals(sorted1, sorted2);
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
