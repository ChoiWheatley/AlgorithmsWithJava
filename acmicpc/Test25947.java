package acmicpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Timeout;

public class Test25947 {
  @Test
  public void test1() {
    int b = 26;
    int a = 2;
    int[] prices = new int[] { 4, 6, 2, 10, 8, 12 };
    int answer = 5;
    assertEquals(answer, Solver25947.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt1.solve(prices, b, a));
  }

  @Test
  public void test2() {
    int b = 23;
    int a = 1;
    int[] prices = new int[] { 4, 6, 2, 12, 8, 14 };
    int answer = 4;
    assertEquals(answer, Solver25947.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt1.solve(prices, b, a));
  }

  @Test
  @Timeout(1)
  @RepeatedTest(10)
  public void correctTest() {
    int maxPrice = 50;
    // int maxPrice = 100_000_001;
    Random r = new Random();
    int n = 10;
    // int n = 100000;
    int b = r.nextInt(1, maxPrice + 1);
    int a = r.nextInt(0, n + 1);
    int[] prices = r.ints(2, maxPrice).filter(e -> e % 2 == 0).limit(n).toArray();

    int answer = Solver25947.solve(prices, b, a);
    int answer1 = Solver25947Alt1.solve(prices, b, a);
    int answer2 = Solver25947Alt2.solve(prices, b, a);

    Arrays.sort(prices);
    assertEquals(answer1, answer2, String.format("""
        prices: %s
        budget: %d
        coupon: %d
          """, Arrays.toString(prices), b, a));
  }

  @Test
  @Timeout(1)
  public void timeoutTest() {
    Random r = new Random();

    int maxPrice = 100_000_0001;
    // int maxPrice = 100001;
    int n = 100000;
    // int n = 200;
    int b = r.nextInt(1, maxPrice + 1);
    int a = r.nextInt(0, n + 1);

    int[] prices = r.ints(2, maxPrice).filter(e -> e % 2 == 0).limit(n).toArray();

    // int answer1 = Solver25947Alt1.solve(prices, b, a);
    int answer2 = Solver25947Alt2.solve(prices, b, a);
  }
}
