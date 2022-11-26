package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Ex25947 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      int[] ints = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
      int n = ints[0];
      int b = ints[1];
      int a = ints[2];
      int[] prices = new int[n];

      line = br.readLine();
      var tokenizer = new StringTokenizer(line);
      for (int i = 0; i < n; ++i) {
        prices[i] = Integer.valueOf(tokenizer.nextToken());
      }

      int result = Solver25947Alt1.solve(prices, b, a);
      bw.write(result + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver25947 {
  /**
   * 가장 저렴한 것부터 담는다.
   * 만약 budget을 초과하면 할인쿠폰을 써서 재시도 한다.
   * 만약 할인쿠폰을 썼는데도 budget을 초과하면 바로 전 단계에서도 할인쿠폰을 써서 재시도 한다.
   * 만약 budget을 초과했는데 쿠폰이 없다면 멈춘다.
   * 
   * @return 최대로 살 수 있는 선물의 수
   */
  public static int solve(int[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    int count = 0;
    for (int i = 0; i < prices.length; ++i) {
      count = Math.max(count, recursion(prices, budget, coupon, i, 0));
    }
    return count;
  }

  public static int recursion(int[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    int curCount = 0;
    for (int i = idx; i < pricesSorted.length; ++i) {
      int price = pricesSorted[i];
      if (remainBudget - price < 0) {
        price /= 2;
        remainCoupon--;
      }
      int result = recursion(pricesSorted, remainBudget - price, remainCoupon, idx + 1, count + 1);
      if (result <= curCount)
        break;
      else
        curCount = result;
    }
    return curCount;
  }
}

class Solver25947Alt1 {

  public static int solve(int[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    return recursion(prices, budget, coupon, 0, 0);
  }

  public static int recursion(int[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    int price = pricesSorted[idx];
    // 쿠폰을 사용했을 때의 결과
    int yesCoupon = recursion(
        pricesSorted,
        remainBudget - (price / 2),
        remainCoupon - 1,
        idx + 1,
        count + 1);
    // 쿠폰을 사용하지 않았을 때의 결과
    int noCoupon = recursion(
        pricesSorted,
        remainBudget - price,
        remainCoupon,
        idx + 1,
        count + 1);

    int result = Math.max(noCoupon, yesCoupon);
    return result;
  }

}

class Solver25947Alt2 {

  public static int solve(int[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    return (new Solver25947Alt2()).recursion(prices, budget, coupon, 0, 0);
  }

  private int best = 0;

  public int recursion(int[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (count < best - remainCoupon)
      return best;
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    int price = pricesSorted[idx];
    // 쿠폰을 사용하지 않았을 때의 결과
    int noCoupon = recursion(
        pricesSorted,
        remainBudget - price,
        remainCoupon,
        idx + 1,
        count + 1);

    // 쿠폰을 사용했을 때의 결과
    int yesCoupon = recursion(
        pricesSorted,
        remainBudget - (price / 2),
        remainCoupon - 1,
        idx + 1,
        count + 1);

    int result = Math.max(noCoupon, yesCoupon);
    best = Math.max(best, result);
    return result;
  }

}

/**
 * https://sites.google.com/site/hannuhelminen/next_combination
 */
class S {
  private final int END;
  private final int MID;
  private int[] indices;
  private int[] ret;

  public S(int n, int a) {
    this.END = n;
    this.MID = a;
    this.indices = new int[END];
    for (int i = 0; i < END; ++i) {
      this.indices[i] = i;
    }
    for (int i = 0; i < MID; ++i) {
      ret[i] = indices[i];
    }
  }

  public boolean hasNext() {
    for (int i = 1; i <= MID; ++i) {
      if (ret[MID - i] != END - i)
        return false;
    }
    return true;
  }

  /**
   * 1. search from mid backwards until you find an element that is smaller than
   * *(end - 1). Call this `head_pos`
   * 
   * 2. search from end backwards until you find the last element that is still
   * larget than *head_pos. Call this `tail_pos`
   * 
   * 3. swap head_pos and tail_pos. Re-order the elements from [head_pos + 1, mid]
   */
  public void next() {
  }

  public final int[] get() {
    return ret;
  }
}