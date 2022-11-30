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
    long[] prices = new long[] { 4, 6, 2, 10, 8, 12 };
    int answer = 5;
    assertEquals(answer, Solver25947.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt1.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt2.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt3.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt4.solve(prices, b, a));
  }

  @Test
  public void test2() {
    int b = 23;
    int a = 1;
    long[] prices = new long[] { 4, 6, 2, 12, 8, 14 };
    int answer = 4;
    assertEquals(answer, Solver25947.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt1.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt2.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt3.solve(prices, b, a));
    assertEquals(answer, Solver25947Alt4.solve(prices, b, a));
  }

  @Test
  @RepeatedTest(100)
  public void correctTest() {
    int maxPrice = 100;
    // int maxPrice = 100_000_001;
    Random r = new Random();
    int n = 70;
    // int n = 100000;
    int b = r.nextInt(maxPrice + 1) + 1;
    int a = r.nextInt(n + 1);
    long[] prices = r.longs(2, maxPrice).filter(e -> e % 2 == 0).limit(n).toArray();

    int answer1 = Solver25947Alt1.solve(prices, b, a);
    // int answer2 = Solver25947Alt2.solve(prices, b, a);
    // int answer3 = Solver25947Alt3.solve(prices, b, a);
    int answer4 = Solver25947Alt4.solve(prices, b, a);

    Arrays.sort(prices);
    // assertEquals(answer1, answer2, String.format("""
    // prices: %s
    // budget: %d
    // coupon: %d
    // """, Arrays.toString(prices), b, a));
    // assertEquals(answer1, answer3, String.format("""
    // prices: %s
    // budget: %d
    // coupon: %d
    // """, Arrays.toString(prices), b, a));
    assertEquals(answer1, answer4, String.format("""
        prices: %s
        budget: %d
        coupon: %d
          """, Arrays.toString(prices), b, a));
  }

  @Test
  public void edgeTest() {
    var prices = new long[] { 2, 16, 16, 22, 38, 46, 52, 60, 72, 80, 92 };
    var budget = 286;
    var coupon = 6;

    // int answer1 = Solver25947Alt1.solve(prices, budget, coupon);
    // int answer2 = Solver25947Alt2.solve(prices, budget, coupon);
    // int answer3 = Solver25947Alt3.solve(prices, budget, coupon);
    int answer4 = Solver25947Alt4.solve(prices, budget, coupon);

    Arrays.sort(prices);
    // assertEquals(answer1, answer2);
    // assertEquals(answer1, answer3);
    // assertEquals(answer1, answer4);
  }

  @Test
  public void edgeTest2() {
    var prices = new long[] { 2, 2, 2, 2, 2, 1000000 };
    var budget = 10;
    var coupon = prices.length;
    var answer = 5;

    int submit = Solver25947Alt4.solve(prices, budget, coupon);
    assertEquals(answer, submit);
  }

  @Test
  public void edgeTest3() {
    var prices = new long[] { 1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000, };
    var budget = 1_000_000_000;
    var coupon = 2;
    var answer = 2;
    int submit = Solver25947Alt4.solve(prices, budget, coupon);
    assertEquals(answer, submit);
  }

  @Test
  @Timeout(1)
  @RepeatedTest(10)
  public void timeoutTest() {
    Random r = new Random();

    int maxPrice = 100_000_0001;
    // int maxPrice = 100001;
    int n = 100000;
    // int n = 7500;
    int b = r.nextInt(maxPrice) + 1;
    int a = r.nextInt(n + 1);

    long[] prices = r.longs(2, maxPrice).filter(e -> e % 2 == 0).limit(n).toArray();

    // int answer1 = Solver25947Alt1.solve(prices, b, a);
    // int answer2 = Solver25947Alt2.solve(prices, b, a);
    // int answer3 = Solver25947Alt3.solve(prices, b, a);
    int answer4 = Solver25947Alt4.solve(prices, b, a);
  }

  @Test
  public void maxIntTest() {
    System.out.println(Integer.MAX_VALUE);
  }
}
