package acmicpc;

import static acmicpc.Ex17359.Solution;
import static acmicpc.Ex17359.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;

public class Test17359 {
  @Test
  public void cnt1() {
    String a = "10111000";
    int answer = 3;
    int submit = countSwitch(a);
    assertEquals(answer, submit);
  }

  @Test
  public void sol1() {
    List<String> ls = Arrays.asList("11100", "0000101", "011100");
    int answer = 6;
    int submit = Solution.solution(ls);
    assertEquals(answer, submit);

    submit = Solution2.solution(ls);
    assertEquals(answer, submit);
  }

  @Test
  public void sol2() {
    List<String> ls = Arrays.asList("00", "01", "10", "11");
    int answer = 2;
    int submit = Solution.solution(ls);
    assertEquals(answer, submit);

    submit = Solution2.solution(ls);
    assertEquals(answer, submit);
  }

  @Test
  public void sol3() {
    List<String> ls = Arrays.asList("1011", "1000");
    int answer = 3;
    int submit = Solution.solution(ls);
    assertEquals(answer, submit);

    submit = Solution2.solution(ls);
    assertEquals(answer, submit);
  }

  @Test
  public void timeout1() {
    int n = 10;
    int strLen = 4;
    Random r = new Random();
    List<String> ls = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      ls.add(r.ints(0, 2)
          .limit(strLen)
          .boxed()
          .map(String::valueOf)
          .reduce((a, b) -> a += b)
          .get());
    }
    // ls.forEach(System.out::println);
    ls.forEach(Ex17359::countSwitch);
    Solution.solution(ls);
    // Solution.recur(ls, new BitSet(ls.size()));
    // assertEquals(factorial(n), Solution.callCount);
  }

  @Test
  public void timeout2() {
    int n = 10;
    int strLen = 100;
    Random r = new Random();
    List<String> ls = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      ls.add(r.ints(0, 2)
          .limit(strLen)
          .boxed()
          .map(String::valueOf)
          .reduce((a, b) -> a += b)
          .get());
    }
    Solution2.solution(ls);
  }

  public int factorial(int n) {
    int ret = n;
    while (n-- > 1)
      ret *= n;
    return ret;
  }

  @Test
  @Timeout(1)
  @RepeatedTest(1)
  public void timeoutCountSwitch() {
    int strLen = 100000;
    Random r = new Random();
    String str = r.ints(0, 2)
        .limit(strLen)
        .mapToObj(String::valueOf)
        .reduce((a, b) -> a += b)
        .get();
    countSwitch(str);
  }
}
