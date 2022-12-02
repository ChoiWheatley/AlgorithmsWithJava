package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class Test5052 {
  @Test
  public void sample1() {
    String[] sample = {
        "91125426",
        "911",
        "97625999",
    };
    Arrays.sort(sample);
    System.out.println(Arrays.toString(sample));
    boolean answer = false;
    assertEquals(answer, Solver5052.solve(sample));
  }

  @Test
  public void sample2() {
    String[] sample = {
        "113",
        "12340",
        "123440",
        "12345",
        "98346",
    };
    boolean answer = true;
    assertEquals(answer, Solver5052.solve(sample));
  }

  @Test
  public void test1() {
    String[] sample = {
        "123",
        "12340",
        "123440",
        "12345",
        "98346",
    };
    boolean answer = false;
    assertEquals(answer, Solver5052.solve(sample));
  }

  @Test
  public void test2() {
    String[] sample = {
        "911567",
        "911570",
    };
    boolean answer = true;
    assertEquals(answer, Solver5052.solve(sample));
  }
}
