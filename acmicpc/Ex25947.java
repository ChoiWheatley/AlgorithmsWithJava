package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class Ex25947 {
  public static void main(String[] args) throws IOException {
    var br = new BufferedReader(new InputStreamReader(System.in));
    String line = br.readLine();
    int[] ints = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
    int n = ints[0];
    int b = ints[1];
    int a = ints[2];
    long[] prices = new long[n];

    line = br.readLine();
    var tokenizer = new StringTokenizer(line);
    for (int i = 0; i < n; ++i) {
      prices[i] = Long.valueOf(tokenizer.nextToken());
    }

    int result = Solver25947Alt4.solve(prices, b, a);
    System.out.println(result);
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
  public static int solve(long[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    int count = 0;
    for (int i = 0; i < prices.length; ++i) {
      count = Math.max(count, recursion(prices, budget, coupon, i, 0));
    }
    return count;
  }

  public static int recursion(long[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    int curCount = 0;
    for (int i = idx; i < pricesSorted.length; ++i) {
      var price = pricesSorted[i];
      if (remainBudget - price < 0) {
        price /= 2;
        remainCoupon--;
      }
      int result = recursion(pricesSorted, remainBudget - (int) price, remainCoupon, idx + 1, count + 1);
      if (result <= curCount)
        break;
      else
        curCount = result;
    }
    return curCount;
  }
}

class Solver25947Alt1 {

  public static int solve(long[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    return recursion(prices, budget, coupon, 0, 0);
  }

  public static int recursion(long[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    var price = pricesSorted[idx];
    // 쿠폰을 사용했을 때의 결과
    int yesCoupon = recursion(
        pricesSorted,
        remainBudget - (int) (price / 2),
        remainCoupon - 1,
        idx + 1,
        count + 1);
    // 쿠폰을 사용하지 않았을 때의 결과
    int noCoupon = recursion(
        pricesSorted,
        remainBudget - (int) price,
        remainCoupon,
        idx + 1,
        count + 1);

    int result = Math.max(noCoupon, yesCoupon);
    return result;
  }

}

class Solver25947Alt2 {

  public static long solve(long[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    return (new Solver25947Alt2()).recursion(prices, budget, coupon, 0, 0);
  }

  private long best = 0;

  public long recursion(long[] pricesSorted, int remainBudget, int remainCoupon, int idx, int count) {
    if (remainCoupon < 0)
      return count - 1;
    if (idx >= pricesSorted.length)
      return count - 1;
    if (remainBudget < 0)
      return count - 1;
    var price = pricesSorted[idx];

    // 쿠폰을 사용했을 때의 결과
    var yesCoupon = recursion(
        pricesSorted,
        remainBudget - (int) (price / 2),
        remainCoupon - 1,
        idx + 1,
        count + 1);
    // 쿠폰을 사용했음에도 best가 갱신되지 않았다면
    // 쿠폰을 사용하지 않았어도 마찬가지였을 것이다.
    if (yesCoupon < best)
      return best;
    // 쿠폰을 사용하지 않았을 때의 결과
    var noCoupon = recursion(
        pricesSorted,
        remainBudget - (int) price,
        remainCoupon,
        idx + 1,
        count + 1);

    long result = Math.max(noCoupon, yesCoupon);
    best = Math.max(best, result);
    return best;
  }

}

class Solver25947Alt3 {
  public static int solve(long[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    int max = 0;
    // 맨 뒤부터 할인을 때린다. 할인한 제품을 사든 말든 알바 아님
    for (int i = prices.length - coupon; i >= 0; --i) {
      var saled = prices.clone();
      for (int j = 0; j < coupon; ++j) {
        saled[i + j] /= 2;
      }
      // Arrays.sort(saled);
      // buy until budget remains nothing
      int cnt = 0;
      int curBudget = budget;
      while (curBudget >= 0) {
        curBudget -= saled[cnt];
        cnt += 1;
      }
      cnt -= 1; // 하나를 더 셌으니까
      if (cnt < max)
        return max;
      else
        max = cnt;
    }

    return max;
  }
}

class Solver25947Alt4 {
  /**
   * 파라메트릭 서치: n개의 선물을 구매할 수 있나요? => Y/N 결정문제로 바꿔서 업다운 게임을 할 수 있음.
   * 1. 누적합을 만들면서 할인 없이 가장 많이 살 수 있는 max를 찾을 수 있다. 이때
   * max...prices.length까지 업다운 게임을 실시
   * 
   * 2. 할인쿠폰을 사용하여 가장 많은 선물을 살 수 있는 경우를 리턴한다.
   */
  public static int solve(long[] prices, int budget, int coupon) {
    Arrays.sort(prices);
    // 1. 누적합
    long[] acc = new long[prices.length];
    int max = 0;
    acc[0] = prices[0];
    for (int i = 1; i < prices.length; ++i) {
      acc[i] = acc[i - 1] + prices[i];
    }
    for (int i = 0; i < prices.length; ++i) {
      if (budget < acc[i]) {
        max = i - 1;
        break;
      }
    }

    // 2. up down game from range(max, prices.length)
    int lo = max;
    int hi = prices.length;
    while (lo < hi) {
      int mid = (lo + hi) / 2;
      if (isPossible(acc, budget, coupon, mid)) {
        // go right
        lo = mid + 1;
      } else {
        // go left
        hi = mid;
      }
    }
    return hi - 1;
  }

  /**
   * n개의 선물을 구매할 수 있나요?
   * [n-coupon, n) 구간을 할인을 맥여보고 가능한지 판별한다.
   * 1. Sum{i = 0}{n - coupon - 1}{prices[i]}
   * = acc[n - coupon - 1]
   * 는 원가로 구매한 선물들의 구간합
   * 
   * 2. Sum{i = n - coupon}{n - 1}{prices[i] / 2}
   * = (acc[n - 1] - acc[n - coupon - 1]) / 2
   * 는 할인가로 구매한 선물들의 구간합
   */
  public static boolean isPossible(long[] acc, int budget, int coupon, int n) {
    long sum = 0;
    if (n == 0) {
      return true;
    }
    if (n <= coupon) {
      // [0, n) / 2 모든 상품을 다 할인받을 수 있음.
      sum += acc[n - 1] / 2;
    } else {
      // [0, n - coupon)
      var unSaled = acc[n - coupon - 1];
      // [n - coupon, n)
      var saled = (acc[n - 1] - acc[n - coupon - 1]) / 2;
      sum = unSaled + saled;
    }
    return sum > budget ? false : true;
  }
}