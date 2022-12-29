package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Ex25916 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      String[] nmStr = line.split(" ");
      long n = Long.valueOf(nmStr[0]);
      long m = Long.valueOf(nmStr[1]);

      line = br.readLine();
      var tokenizer = new StringTokenizer(line);
      List<Long> holes = new ArrayList<>((int) n);
      while (n-- > 0) {
        holes.add(Long.valueOf(tokenizer.nextToken()));
      }

      var submit = Solution2.solution(holes, m);
      bw.write(submit + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class Solution {
    /**
     * 누적합 + two pointer + 파라메트릭 서치
     * 임의의 인덱스 i에 대하여 SUM{i}{j}{hole} <= hamster를 만족하는
     * 최대 j에 대한 SUM을 구하는 문제. 이때 i: 0..<hole.length
     */
    public static long solution(List<Long> holes, long hamster) {
      List<Long> cumulative = new ArrayList<>(holes);
      for (int i = 1; i < cumulative.size(); ++i) {
        cumulative.set(i, cumulative.get(i - 1) + cumulative.get(i));
      }
      AtomicLong max = new AtomicLong(0l);

      // 모든 리스트를 순회하며 max를 수정한다.
      IntStream.range(0, cumulative.size()).forEach(startIdx -> {
        int j = upperBound(startIdx, cumulative.size(),
            idx -> sumBetween(cumulative, startIdx, idx) <= hamster);
        j -= 1; // 현재 j는 hamster보다 큰 원소 중 첫번째 원소를 가리키고 있기 때문.
        var sum = sumBetween(cumulative, startIdx, j);
        if (max.get() < sum) {
          max.set(sum);
        }
      });

      return max.get();
    }

    public static int upperBound(int startIdx, int endIdx, Predicate<Integer> whenMoveRight) {
      int lo = 0;
      int hi = endIdx - startIdx;
      while (hi - lo > 1) {
        int mid = (lo + hi) / 2;
        if (whenMoveRight.test(mid + startIdx)) {
          // move right
          lo = mid;
        } else {
          // move left
          hi = mid;
        }
      }
      while (lo < endIdx - startIdx && whenMoveRight.test(lo + startIdx)) {
        lo++;
      }
      return lo + startIdx;
    }

    public static long sumBetween(List<Long> cummulative, int startIdx, int endIdx) {
      if (endIdx < 0)
        return 0l;
      if (startIdx < 1)
        return cummulative.get(endIdx);
      return cummulative.get(endIdx) - cummulative.get(startIdx - 1);
    }

    public static List<Long> cumulate(List<Long> ls) {
      List<Long> cumulative = new ArrayList<>(ls);
      for (int i = 1; i < ls.size(); ++i) {
        cumulative.set(i, cumulative.get(i) + cumulative.get(i - 1));
      }
      return cumulative;
    }

  }

  public static class Solution2 {
    /** two pointer 전략을 사용하자. */
    public static long solution(List<Long> holes, Long hamster) {
      int start = 0;
      int end = 0;
      long sum = 0;
      long max = 0;

      while (end < holes.size()) {
        sum += holes.get(end);
        while (start < holes.size() && sum > hamster) { // 초과한 경우
          sum -= holes.get(start);
          start++;
        }
        max = Math.max(max, sum);
        end++;
      }

      return max;
    }
  }

}
