package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
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

  /**
   * Solution ~ Solution3까지는 naive한 코드들, 재귀를 사용함. Solution3을 제외하곤 1,2는 모두 시간초과
   * 발생함.
   * Solution4는 next_permutation 함수를 직접 구현하여 문제를 풂. 약 400ms로 통과함.
   */

  /**
   * Solution 쪽은 재귀를 사용하는데 `current`라고 지금까지 탐색한 인덱스들을 하나씩 붙여놓은 문자열.
   * 재귀 종료조건에 보면 최종 current의 전구상태가 바뀌는 횟수를 리턴하고 있음.
   * 그래서 재귀함수의 리턴 중 최솟값만을 취해 던지면 정답이 된다.
   */
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

  /**
   * Solution1이 안되는 이유를 String 붙여넣기에 오버헤드가 발생했다고 가정하여 인덱스만을 저장하고 종료조건에서 한 번에 계산하는
   * 것으로 작성. 이때 굳이 스트링을 다 붙여넣지 말고 경계에 대해서만 계산하고 내부에 발생하는 전구가 바뀌는 경우는 변하지 않으니
   * 미리 계산하는 것으로 변경. 비록 시간초과가 발생했지만 내부적으로 전구가 바뀌는 걸 세는 것은 올바름.
   */
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

  /**
   * Solution2의 indexOrder가 분명 시간초과를 발생시켰으리라 생각하고 조금 더 간소화 작업을 수행함. 바로 이전 스트링의 마지막
   * char만 다음 호출시 넘기는 것이다. 왜 lastBoundaryChar의 타입이 String 이냐면, 초기엔 이전 스트링이 없기 때문에
   * 빈 문자열을 넘기기 위해서이다. 그래서 문자열이 비어있는지를 확인하는 부분이 존재한다.
   */
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

  /**
   * @kati 코드를 보고 next_permutation을 사용하면 되겠다 판단. nextPermutation 자체가 n!가 아닌 n만큼의
   *       시간복잡도를 사용하기 때문에 이전보다 더 빠른 경우의 수를 가져올 수 있다.
   */
  public static class Solution4 {
    public static int solution(List<String> ls) {
      int constCnt = 0;
      for (int i = 0; i < ls.size(); ++i)
        constCnt += countSwitch(ls.get(i));
      int boundaryCnt = Integer.MAX_VALUE;
      List<Integer> indices = new ArrayList<>(ls.size());
      IntStream.range(0, ls.size()).forEach(indices::add);

      do {
        int cnt = 0;
        for (int i = 1; i < ls.size(); ++i) {
          String left = ls.get(indices.get(i - 1));
          String right = ls.get(indices.get(i));
          if (left.charAt(left.length() - 1) != right.charAt(0))
            cnt++;
        }
        boundaryCnt = Math.min(boundaryCnt, cnt);
      } while (nextPermutation(indices, Comparator.naturalOrder()));
      // first and last

      return constCnt + boundaryCnt;
    }

  }

  /**
   * Index에 대한 permutation을 사용하면 AAB와 같이 중복된 전구묶음이 존재할 경우 같은 연산을 여러 번 수행할 수
   * 있다는 단점이 존재한다. 이 경우, 전구묶음 자체를 permutation 하게 되면 그 중복을 방지할 수 있다.
   * 
   * @kati
   */
  public static class Solution5 {
    public static int solution(List<String> ls) {
      Collections.sort(ls); // sort를 사용해야 permutation을 처음부터 쓸 수 있음.
      int constCnt = 0;
      for (int i = 0; i < ls.size(); ++i)
        constCnt += countSwitch(ls.get(i));
      int boundaryCnt = Integer.MAX_VALUE;

      do {
        int cnt = 0;
        for (int i = 1; i < ls.size(); ++i) {
          String left = ls.get(i - 1);
          String right = ls.get(i);
          if (left.charAt(left.length() - 1) != right.charAt(0))
            cnt++;
        }
        boundaryCnt = Math.min(boundaryCnt, cnt);
      } while (nextPermutation(ls, Comparator.naturalOrder()));

      return constCnt + boundaryCnt;
    }

  }

  /**
   * 전구가 바뀌는 횟수를 리턴한다. 마침 어제 자바책에서 이터레이터를 알려주기에 사용해봤다.
   * forEach문 안에서 일반 int형 변수를 변경하면 effectively final이 아니라서 징징댄다. 그래서
   * AtomicInteger라는 스레드 안전한 개체를 사용했다.
   * 
   * @param bulbs
   * @return
   */
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
    int left = i;
    int right = lastIdx;
    while (left < right) {
      tmp = ls.get(left);
      ls.set(left, ls.get(right));
      ls.set(right, tmp);
      left++;
      right--;
    }

    return true;
  }

}
