package programmers;

import static java.util.Map.entry;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.Test;
import junit.framework.*;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12947
 */

public class Ex12947 {
  static Map<Integer, Boolean> tbl = Map.ofEntries(
      entry(10, true),
      entry(12, true),
      entry(11, false),
      entry(13, false),
      entry(18, true));

  @Test
  public void test1() {
    assertEquals(true, solution(10));
  }

  @Test
  public void test2() {
    assertEquals(true, solution(12));
  }

  @Test
  public void test3() {
    assertEquals(false, solution(11));
  }

  @Test
  public void test4() {
    var x = 13;
    assertEquals(4, sumDigit(x));
    assertEquals(false, solution(x));
  }

  @Test
  public void test5() {
    assertEquals(true, solution(18));
  }

  @Test
  public void sumDigitTest() {
    var x = 12345;
    assertEquals(15, sumDigit(x));
  }

  public int sumDigit(int x) {
    int sum = 0;
    while (x > 0) {
      sum += x % 10;
      x /= 10;
    }
    return sum;
  }

  public boolean solution(int x) {
    // 1. 모든 자릿수의 합을 구한다.
    var sum = sumDigit(x);
    // 2. 1번에서 구한 값이 x의 약수인지 구한다.
    return (Math.floorMod(x, sum)) == 0;
  }
}
