package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import acmicpc.Ex1449.Solution;

public class Test1449 {

  private void sol(List<Integer> ls, int l, int answer) {
    var submit = Solution.solution(ls, l);
    assertEquals(answer, submit);
  }

  @Test
  public void sol1() {
    var ls = Arrays.asList(1, 2, 100, 101);
    var l = 1;
    int answer = 4;
    int submit = Solution.solution(ls, l);
    assertEquals(answer, submit);
  }

  @Test
  public void sol2() {
    var ls = Arrays.asList(1, 2, 100, 101);
    var l = 2;
    int answer = 2;
    int submit = Solution.solution(ls, l);
    assertEquals(answer, submit);
  }

  @Test
  public void sol3() {
    var ls = Arrays.asList(1, 2, 100, 101);
    var l = 3;
    int answer = 2;
    int submit = Solution.solution(ls, l);
    assertEquals(answer, submit);
  }

  @Test
  public void sol4() {
    sol(Arrays.asList(1, 2, 3, 4), 3, 2);
  }

  @Test
  public void sol5() {
    sol(Arrays.asList(3, 2, 1), 1, 3);
  }
}
