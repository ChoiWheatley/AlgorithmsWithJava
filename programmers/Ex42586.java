package programmers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42586
 * 기능개발
 */

public class Ex42586 {
  public static final int DEPLOY = 100;

  public int[] solution(int[] progresses, int[] speeds) {
    int[] untilDeploy = new int[progresses.length];
    for (int idx = 0; idx < progresses.length; idx++) {
      untilDeploy[idx] = (int) Math.ceil((double) (DEPLOY - progresses[idx]) / (double) speeds[idx]);
    }

    List<Integer> list = new ArrayList<>();
    for (int cursor1 = 0; cursor1 < untilDeploy.length;) {
      int c1 = untilDeploy[cursor1];
      int cnt = 0;
      for (int cursor2 = cursor1; cursor2 < untilDeploy.length; ++cursor2) {
        int c2 = untilDeploy[cursor2];
        if (c2 <= c1) {
          cnt++;
        } else {
          break;
        }
      }
      list.add(cnt);
      cursor1 += cnt;
    }
    return list
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
  }

  private int[] progresses;
  private int[] speeds;
  private int[] answer;

  @Test
  public void test1() {
    progresses = new int[] { 93, 30, 55 };
    speeds = new int[] { 1, 30, 5 };
    answer = new int[] { 2, 1 };
    assertArrayEquals(answer, solution(progresses, speeds));
  }

  @Test
  public void test2() {
    // [95, 90, 99, 99, 80, 99] [1, 1, 1, 1, 1, 1] [1, 3, 2]
    progresses = new int[] { 95, 90, 99, 99, 80, 99 };
    speeds = new int[] { 1, 1, 1, 1, 1, 1 };
    answer = new int[] { 1, 3, 2 };
    assertArrayEquals(answer, solution(progresses, speeds));
  }

  @Test
  public void testCeil() {
    assertEquals(3, (int) Math.ceil((double) 70 / (double) 30));
    assertEquals(9, (int) Math.ceil(45 / 5));
  }
}
