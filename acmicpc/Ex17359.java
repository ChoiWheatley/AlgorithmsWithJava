package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex17359 {
  public static void main(String[] args) {
    try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
      int n = Integer.valueOf(reader.readLine());
      List<String> ls = new ArrayList<>(n);
      while (n-- > 0) {
        ls.add(reader.readLine());
      }
      var submit = Solution.solution(ls);
      System.out.println(submit);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(2);
    }
  }

  public static class Solution {
    public static int callCount = 0;

    public static int solution(List<String> ls) {
      BitSet visited = new BitSet(ls.size());
      return recur(ls, visited, "");
    }

    public static int recur(List<String> ls, BitSet visited, String current) {
      callCount++;
      if (visited.stream().count() == ls.size()) {
        return countSwitch(current);
      }
      int min = Integer.MAX_VALUE;
      StringBuilder newStr = new StringBuilder();

      for (int idx = 0; idx < ls.size(); ++idx) {
        newStr.append(current);
        if (visited.get(idx) == false) {
          visited.set(idx);
          newStr.append(ls.get(idx));
          int cnt = recur(ls, visited, newStr.toString());
          min = Math.min(min, cnt);
          visited.clear(idx);
        }
        newStr.delete(0, newStr.length());
      }
      return min;
    }

  }

  public static class Solution2 {
    public static int solution(List<String> ls) {
      int constCnt = 0;
      for (int i = 0; i < ls.size(); ++i) {
        constCnt += countSwitch(ls.get(i));
      }
      int boundaryCnt = go(ls, new BitSet(ls.size()), new Stack<Integer>());
      return constCnt + boundaryCnt;
    }

    /** 리스트의 경계에서 발생한 switch만 세서 가장 작은 switch 횟수를 리턴한다. */
    public static int go(List<String> ls, BitSet visited, Stack<Integer> indexOrder) {
      if (visited.stream().count() == ls.size()) {
        int cnt = 0;
        for (int i = 1; i < indexOrder.size(); ++i) {
          String before = ls.get(indexOrder.get(i - 1));
          String after = ls.get(indexOrder.get(i));
          if (before.charAt(before.length() - 1) != after.charAt(0))
            cnt++;
        }
        return cnt;
      }
      int min = Integer.MAX_VALUE;
      for (int idx = 0; idx < ls.size(); ++idx) {
        if (visited.get(idx) == false) {
          visited.set(idx);
          indexOrder.push(idx);

          min = Math.min(min, go(ls, visited, indexOrder));

          visited.clear(idx);
          indexOrder.pop();
        }
      }
      return min;
    }
  }

  public static int countSwitch(String bulbs) {
    AtomicInteger cnt = new AtomicInteger(0);
    var iter = bulbs.chars().iterator();
    if (!iter.hasNext())
      return cnt.get();
    AtomicInteger cur = new AtomicInteger(iter.next());
    iter.forEachRemaining((Integer next) -> {
      if (!next.equals(cur.get())) {
        cur.set(next);
        cnt.incrementAndGet();
      }
    });

    return cnt.get();
  }
}
