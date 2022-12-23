package programmers;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ex64064 {
  public static void main(String[] args) {

  }

  public static class Solution {
    public static int solution(String[] user_id, String[] banned_id) {
      /** matchedIndices: banned_id의 각 원소들과 패턴 매칭이 되는 user_id의 인덱스들 */
      int[][] matchedIndices = new int[banned_id.length][];
      for (int i = 0; i < banned_id.length; ++i) {
        matchedIndices[i] = possibleIndicesOf(user_id, banned_id[i]);
      }
      return allCaseOf(matchedIndices).size();
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

    public static final int MAX_LENGTH = 32;

    public static Set<BitSet> allCaseOf(int[][] indices) {
      return caseRecursive(indices, 0, new BitSet(MAX_LENGTH));
    }

    public static Set<BitSet> caseRecursive(int[][] indices, int currentRow, BitSet visited) {
      if (currentRow >= indices.length)
        return Set.of((BitSet) visited.clone());
      Set<BitSet> ret = new HashSet<>();
      for (var next : indices[currentRow]) {
        if (!visited.get(next)) {
          visited.set(next);
          ret.addAll(caseRecursive(indices, currentRow + 1, visited));
          visited.clear(next);
        }
      }
      return ret;
    }
  }

  public static class Solution2 {
    Set<Integer> set;

    public static int solution(String[] user_id, String[] banned_id) {
      var solver = new Solution2();
      solver.go(0, user_id, banned_id, 0);
      return solver.set.size();
    }

    private Solution2() {
      set = new HashSet<>();
    }

    private void go(int index, String[] user_id, String[] banned_id, int bit) {
      if (index >= banned_id.length) {
        set.add(bit);
        return;
      }

      String regex = banned_id[index].replace("*", ".");
      for (int i = 0; i < user_id.length; ++i) {
        if (((bit >> i) & 1) == 1 || // already visited
            !user_id[i].matches(regex)) { // doesn't match with regex
          continue;
        }
        bit |= 1 << i; // check as visited
        go(index + 1, user_id, banned_id, bit);
      }
    }
  }
}
