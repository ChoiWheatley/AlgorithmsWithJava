package programmers;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12933
 * 정렬문제
 */

public class Ex12933 {
  public long solution(long x) {
    var str = String.valueOf(x)
        .chars()
        .boxed()
        .sorted((a, b) -> b - a)
        .collect(StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();

    return Long.valueOf(str);
  }

  private <T> List<T> myMergeSort(List<T> ls, Comparator<T> comp) {
    if (ls.size() <= 1) {
      return ls;
    }
    // divide
    int midIdx = ls.size() / 2;
    var left = ls.subList(0, midIdx);
    var right = ls.subList(midIdx, ls.size());
    // merge
    var leftSorted = myMergeSort(left, comp);
    var rightSorted = myMergeSort(right, comp);

    List<T> ret = new ArrayList<T>(ls.size());
    int leftIdx = 0;
    int rightIdx = 0;
    while (leftIdx < leftSorted.size() && rightIdx < rightSorted.size()) {
      var l = leftSorted.get(leftIdx);
      var r = rightSorted.get(rightIdx);
      if (comp.compare(l, r) > 0) {
        ret.add(r);
        rightIdx++;
      } else {
        ret.add(l);
        leftIdx++;
      }
    }
    // remainders
    while (leftIdx < leftSorted.size()) {
      ret.add(leftSorted.get(leftIdx));
      leftIdx++;
    }
    while (rightIdx < rightSorted.size()) {
      ret.add(rightSorted.get(rightIdx));
      rightIdx++;
    }
    return ret;
  }

  protected List<Integer> longToList(long x) {
    List<Integer> ret = new ArrayList<>();
    while (x > 0) {
      int digit = Math.floorMod(x, 10);
      x /= 10;
      ret.add(digit);
    }

    Collections.reverse(ret);
    return ret;
  }

  protected long listToLong(List<Integer> ls) {
    int idx = ls.size() - 1;
    int cnt = 0;
    long ret = 0;
    while (idx >= 0) {
      long pow = 1;
      for (int i = 0; i < cnt; ++i) {
        pow *= 10;
      }
      ret += (ls.get(idx) * pow);
      idx--;
      cnt++;
    }
    return ret;
  }

  public long solution1(long x) {
    List<Integer> ls = longToList(x);
    List<Integer> sorted = myMergeSort(ls, (a, b) -> b - a);
    return listToLong(sorted);
  }

  @Test
  public void testcase1() {
    assertEquals(873211, solution1(118372));
  }

  @Test
  public void testcase2() {
    assertEquals(solution(8000000000L), solution1(8000000000L));
  }

  @Test
  public void longtolistTest() {
    long x = 118372L;
    var ls = Arrays.asList(1, 1, 8, 3, 7, 2);
    assertArrayEquals(ls.toArray(), longToList(x).toArray());
  }

  @Test
  public void longtolistTest2() {
    long x = 8000000000L;
    var ls = Arrays.asList(8, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    assertEquals(ls, longToList(x));
  }

  @Test
  public void listtolongTest() {
    var ls = Arrays.asList(1, 1, 2, 3, 7, 8);
    long answer = 112378L;
    assertEquals(answer, listToLong(ls));
  }

  @Test
  public void listtolongTest2() {
    var ls = Arrays.asList(8, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    long answer = 8000000000L;
    assertEquals(answer, listToLong(ls));
  }

  @Test
  public void sortTest1() {
    var ls = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var answer = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1);
    assertEquals(answer, myMergeSort(ls, (a, b) -> b - a));
  }

  @Test
  public void sortTest2() {
    var x = 8000000000L;
    var answer = 8000000000L;
    assertEquals(answer, solution1(x));
  }

}