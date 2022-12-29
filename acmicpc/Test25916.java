package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import static acmicpc.Ex25916.*;
import static acmicpc.Ex25916.Solution.cumulate;
import static acmicpc.Ex25916.Solution.sumBetween;
import static acmicpc.Ex25916.Solution.upperBound;
import static acmicpc.Ex25916.Solution2;

public class Test25916 {
  @Test
  void testUpperBound() {
    Long[] sortedArr = { 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l, 10l };
    int compared = 5;
    int answer = 5;
    int submit = upperBound(0, sortedArr.length, idx -> sumBetween(Arrays.asList(sortedArr), 0, idx) <= compared);
    assertEquals(answer, submit);

    submit = upperBound(0, sortedArr.length, idx -> sortedArr[idx] <= compared);
  }

  @Test
  public void testUpperBound2() {
    Long[] ls = { 2l, 4l, 6l, 8l, 10l };
    Long compared = 5l;
    int answer = 2;
    int submit = upperBound(0, ls.length, idx -> sumBetween(Arrays.asList(ls), 0, idx) <= compared);
    assertEquals(answer, submit);
  }

  @Test
  public void testUpperBound3() {
    Long[] ls = { 2l, 2l, 2l, 2l, 2l };
    List<Long> cumulative = Arrays.asList(2l, 4l, 6l, 8l, 10l);
    Long compared = 5l;
    int startIdx = 2;
    int answer = 4;
    int submit = upperBound(startIdx, cumulative.size(), idx -> sumBetween(cumulative, startIdx, idx) <= compared);
    assertEquals(answer, submit);
  }

  @Test
  public void testUpperBound4() {
    Integer[] ls = { 1, 1, 1, 1, 1, 1, 1, 1, 1, };
    Integer compared = 1;
    int answer = ls.length;

    var submit = upperBound(0, ls.length, idx -> ls[idx] <= compared);
    assertEquals(answer, submit);
  }

  // SUM{startIdx}{j}{ls} <= compared를 만족하는 최대 j를 구하는 문제
  // SUM{startIdx}{j}{ls} = cumulative[j] - cumulative[startIdx - 1] 이므로
  // cumulative[j] - cumulative[startIdx - 1] <= compared 를 만족하는 최대 j를 구하는 문제인
  // 것이다.
  @Test
  public void testCumulativeUpperBound1() {
    List<Long> ls = Arrays.asList(10l, 20l, 30l, 40l, 50l);
    List<Long> cumulative = cumulate(ls);
    assertEquals(Arrays.asList(10l, 30l, 60l, 100l, 150l), cumulative);
    Long compared = 35l;
    AtomicInteger startIdx = new AtomicInteger(0);
    int answer = 2;
    int submit = upperBound(startIdx.get(), cumulative.size(),
        idx -> sumBetween(cumulative, startIdx.get(), idx) <= compared);
    assertEquals(answer, submit);

    startIdx.set(1);
    answer = 2;
    submit = upperBound(startIdx.get(), cumulative.size(),
        idx -> sumBetween(cumulative, startIdx.get(), idx) <= compared);
    assertEquals(answer, submit);

    startIdx.set(2);
    answer = 3;
    submit = upperBound(startIdx.get(), cumulative.size(),
        idx -> sumBetween(cumulative, startIdx.get(), idx) <= compared);
    assertEquals(answer, submit);

    startIdx.set(3);
    answer = 3;
    submit = upperBound(startIdx.get(), cumulative.size(),
        idx -> sumBetween(cumulative, startIdx.get(), idx) <= compared);
    assertEquals(answer, submit);
  }

  @Test
  void testSolution() {
    List<Long> holes = Arrays.asList(2l, 2l, 2l, 2l, 11l, 2l, 5l, 2l);
    // List<Long> cumulative = cumulate(holes);
    Long hamster = 10l;
    Long answer = 9l;

    Long submit = Solution.solution(holes, hamster);
    assertEquals(answer, submit);

    submit = Solution2.solution(holes, hamster);
    assertEquals(answer, submit);
  }

  /** 1 <= Ai <= 10^9 라서 0이 올 일이 없음. */
  @Test
  public void testSolution2() {
    // List<Long> holes = Arrays.asList(1l, 0l, 1l, 1l, 1l, 1l, 1l, 0l, 1l, 1l, 0l,
    // 1l);
    // // List<Long> cumulative = cumulate(holes);
    // Long hamster = 10l;
    // Long answer = 5l;

    // Long submit = Solution.solution(holes, hamster);
    // assertEquals(answer, submit);

    // submit = Solution2.solution(holes, hamster);
    // assertEquals(answer, submit);
  }

  @Test
  public void testSolution3() {
    List<Long> holes = Arrays.asList(1l, 1l, 1l, 1l, 1l, 1l, 1l);
    Long hamster = 0l;
    Long answer = 0l;
    Long submit = Solution.solution(holes, hamster);
    assertEquals(answer, submit);

    submit = Solution2.solution(holes, hamster);
    assertEquals(answer, submit);
  }
}
