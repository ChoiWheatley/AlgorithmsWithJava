package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import useful.KMP;

public class Ex2401 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));

    String L = reader.readLine();
    int N = Integer.valueOf(reader.readLine());
    List<String> S = new ArrayList<>(N);
    while (N-- > 0) {
      S.add(reader.readLine());
    }

    int submit = Solution.solution(L, S);
    System.out.println(submit);

    reader.close();
  }

  public static class Solution {
    public static int solution(String L, List<String> S) {
      return solution(failureTable(L, S), L.length());
    }

    /** KMP 알고리즘을 응용하여 모든 S의 원소에 대하여 L과 매칭이 끝나는 위치에 각 원소의 길이를 적은 테이블을 리턴한다. */
    static int[][] failureTable(String L, List<String> S) {
      int[][] ret = new int[S.size()][L.length()];

      IntStream.range(0, S.size()).forEach(i -> {
        String Si = S.get(i);
        KMP kmp = new KMP(Si);
        kmp.compare(L, j -> ret[i][j + Si.length() - 1] = Si.length());
      });

      return ret;
    }

    /** dynamic programming을 사용, i번째 인덱스까지 L에 붙여넣을 S의 원소들의 가장 긴 길이를 저장한다. */
    static int solution(int[][] failTable, int colLen) {
      int[] sum = new int[colLen]; // cumulative sum
      for (int col = 0; col < colLen; ++col) {
        if (col > 0)
          sum[col] = sum[col - 1];
        for (int row = 0; row < failTable.length; ++row) {
          var current = failTable[row][col];
          var startIdx = col - current;
          var rangedSum = sumBetween(sum, startIdx, col);
          if (rangedSum < current) {
            sum[col] += current - rangedSum;
          }
        }
      }
      return sum[colLen - 1];
    }

    static int[] maxTable(int[][] failTable, int colLen) {
      // max table
      int[] maxTable = new int[colLen];
      for (int col = 0; col < colLen; ++col) {
        int max = 0;
        for (int row = 0; row < failTable.length; ++row) {
          max = Math.max(max, failTable[row][col]);
        }
        maxTable[col] = max;
      }
      return maxTable;
    }

    static int sumBetween(int[] cumulative, int start, int end) {
      if (start < 0)
        return cumulative[end];
      return cumulative[end] - cumulative[start];
    }
  }
}