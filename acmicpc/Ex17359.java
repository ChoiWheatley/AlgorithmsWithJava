package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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

  public static class Solution3 {
    public static int solution(List<String> ls) {
      int constCnt = 0;
      for (int i = 0; i < ls.size(); ++i)
        constCnt += countSwitch(ls.get(i));
      int boundaryCnt = go(ls, new BitSet(ls.size()), "");
      return constCnt + boundaryCnt;
    }

    public static int go(List<String> ls, BitSet visited, String lastBoundaryChar) {
      if (visited.stream().count() == ls.size())
        return 0;
      int min = Integer.MAX_VALUE;
      for (int idx = 0; idx < ls.size(); ++idx) {
        String cur = ls.get(idx);
        if (visited.get(idx) == false) {
          visited.set(idx);

          int cnt = 0;
          if (lastBoundaryChar.length() == 1 &&
              lastBoundaryChar.charAt(0) != cur.charAt(0))
            cnt++;
          cnt += go(ls, visited, String.valueOf(cur.charAt(cur.length() - 1)));
          min = Math.min(min, cnt);

          visited.clear(idx);
        }
      }
      return min;
    }
  }

  public static class Solution4 {
    public static int solution(List<String> ls) {
      int constCnt = 0;
      for (int i = 0; i < ls.size(); ++i)
        constCnt += countSwitch(ls.get(i));
      int boundaryCnt = 0;
      List<Integer> indices = new ArrayList<>(ls.size());
      IntStream.range(0, ls.size()).forEach(indices::add);

      do {
        int cnt = 0;
        for (int i = 1; i < indices.size(); ++i) {
          String left = ls.get(indices.get(i - 1));
          String right = ls.get(indices.get(i));
          if (left.charAt(left.length() - 1) != right.charAt(0))
            cnt++;
        }
        boundaryCnt = Math.min(boundaryCnt, cnt);
      } while (nextPermutation(indices, Comparator.naturalOrder()));

      return constCnt + boundaryCnt;
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

  /**
   * 사전순 상에 다음으로 오는 조합을 collection에 저장한다.
   * 
   * @param ls         out
   * @param comparator 사전순서 정의
   * @return if has no any next permutation, return false, else, return true
   */
  public static <T extends Comparable<T>> boolean nextPermutation(List<T> ls, Comparator<T> comparator) {
    final int lastIdx = ls.size() - 1;
    // 1. 단조감소수열이 끝나는 인덱스 i를 찾는다.
    int i = lastIdx;
    for (; i > 0; --i) {
      if (comparator.compare(ls.get(i - 1), ls.get(i)) < 0)
        break;
    }
    if (i == 0)
      return false;

    // 2. 이진탐색을 활용하여 i - 1에 위치한 원소와 그보다 큰 원소들 중 가장 작은 원소(upperbound)를
    // 찾아 서로 바꾼다. 참고: [i:last]는 현재 reversed이다.
    int lo = i;
    int hi = ls.size();
    T tmp = ls.get(i - 1);
    while (hi - lo > 1) {
      int mid = (lo + hi) / 2;
      if (comparator.compare(ls.get(mid), tmp) <= 0) {
        // go left
        hi = mid - 1;
      } else {
        // go right
        lo = mid;
      }
    }
    ls.set(i - 1, ls.get(lo));
    ls.set(lo, tmp);

    // 3. i번째 원소부터 단조증가수열로 만든다.
    for (int len = 0; len < (ls.size() - i + 1) / 2; ++len) {
      int leftIdx = i + len;
      int rightIdx = ls.size() - len - 1;
      tmp = ls.get(leftIdx);
      ls.set(leftIdx, ls.get(rightIdx));
      ls.set(rightIdx, tmp);
    }

    return true;
  }

}
