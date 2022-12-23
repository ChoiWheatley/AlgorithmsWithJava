package programmers;

import java.util.ArrayList;
import java.util.List;

public class Ex64064 {
  public static void main(String[] args) {

  }

  public static class Solution {
    public static int solution(String[] user_id, String[] banned_id) {
      // TODO: banned_id 중복제거
      /** matchedIndices: banned_id의 각 원소들과 패턴 매칭이 되는 user_id의 인덱스들 */
      int[][] matchedIndices = new int[banned_id.length][];
      for (int i = 0; i < user_id.length; ++i) {
        matchedIndices[i] = possibleIndicesOf(user_id, banned_id[i]);
      }
      return countPermutation(matchedIndices, 0, new boolean[user_id.length]);
    }

    /** match(frodo, **od*) -> true */
    public static boolean match(String value, String pattern) {
      if (value.length() != pattern.length()) {
        return false;
      }
      for (int i = 0; i < value.length(); ++i) {
        if (pattern.charAt(i) == '*')
          continue;
        if (pattern.charAt(i) != value.charAt(i))
          return false;
      }
      return true;
    }

    public static int[] possibleIndicesOf(String[] matches, String pattern) {
      List<Integer> ret = new ArrayList<>(matches.length);
      for (int i = 0; i < matches.length; ++i) {
        if (match(matches[i], pattern))
          ret.add(i);
      }
      return ret.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static int count(int[][] indices, int lengthOfReferences) {
      int permutation = countPermutation(indices, 0, new boolean[lengthOfReferences]);
      for (int[] each : indices) {
        int combination = factorial(each.length);
        permutation -= (combination - 1);
      }
      return permutation;
    }

    public static int countPermutation(int[][] indices, int currentRow, boolean[] visited) {
      if (currentRow >= indices.length) {
        return 1;
      }
      int cnt = 0;
      for (var next : indices[currentRow]) {
        if (!visited[next]) {
          visited[next] = true;
          cnt += countPermutation(indices, currentRow + 1, visited);
          visited[next] = false;
        }
      }
      return cnt;
    }

    public static int factorial(int n) {
      int ret = 1;
      for (int i = 1; i <= n; ++i)
        ret *= i;
      return ret;
    }
  }
}
