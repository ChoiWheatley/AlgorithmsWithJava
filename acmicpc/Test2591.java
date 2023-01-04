package acmicpc;

import static acmicpc.Ex2591.Solution.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class Test2591 {
  @Test
  public void sol1() {
    String str = "27123";
    int answer = 6;
    assertEquals(answer, solution(str));
  }

  public void test(String str, int answer) {
    assertEquals(answer, solution(str));
  }

  @Test
  public void sol2() {
    test("11111", 8);
  }

  @Test
  public void sol3() {
    test("1010", 1);
  }

  @Test
  public void sol4() {
    test("10111", 3);
  }

  @Test
  public void sol5() {
    test("21024", 2);
  }

  @Test
  public void sol6() {
    test("210220230", 1);
  }

  @Test
  public void sol7() {
    test("110", 1);
  }
}
