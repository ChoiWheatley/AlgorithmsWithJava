package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import useful.KMP;
import useful.SparseMatrix;

public class Ex2401 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));

    String L = reader.readLine();
    int N = Integer.valueOf(reader.readLine());
    List<String> S = new ArrayList<>(N);
    while (N-- > 0) {
      S.add(reader.readLine());
    }

    int submit = Solution3.solution(L, S);
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

    @Deprecated
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

  public static class Solution2 {
    public static int solution(String L, List<String> S) {
      return solution(failureTable(L, S), S.size(), L.length());
    }

    /** KMP 알고리즘을 응용하여 모든 S의 원소에 대하여 L과 매칭이 끝나는 위치에 각 원소의 길이를 적은 테이블을 리턴한다. */
    static SparseMatrix<Integer> failureTable(String L, List<String> S) {
      SparseMatrix<Integer> ret = new SparseMatrix<>(S.size(), L.length());

      IntStream.range(0, S.size()).forEach(i -> {
        String Si = S.get(i);
        KMP kmp = new KMP(Si);
        kmp.compare(L, j -> ret.set(i, j + Si.length() - 1, Si.length()));
      });

      return ret;
    }

    /** dynamic programming을 사용, i번째 인덱스까지 L에 붙여넣을 S의 원소들의 가장 긴 길이를 저장한다. */
    static int solution(SparseMatrix<Integer> failTable, int rowLen, int colLen) {
      int[] sum = new int[colLen]; // cumulative sum
      IntStream.range(0, colLen).forEach(col -> {
        if (col > 0)
          sum[col] = sum[col - 1];
        var cols = failTable.getC(col);
        cols.forEach(e -> {
          var currentLength = e.getValue();
          var startIdx = col - currentLength;
          var rangedSum = sumBetween(sum, startIdx, col);
          if (rangedSum < currentLength) {
            sum[col] += currentLength - rangedSum;
          }
        });
      });
      return sum[colLen - 1];
    }

    static int sumBetween(int[] cumulative, int start, int end) {
      if (start < 0)
        return cumulative[end];
      return cumulative[end] - cumulative[start];
    }
  }

  public static class Solution3 {
    /** KMP를 사용하여 일치 문자열의 인덱스를 받아올 때마다 DP를 업데이트해보자. */
    public static int solution(String L, List<String> S) {
      int[] sum = new int[L.length()]; // cumulative
      int[][] fails = new int[S.size()][]; // S의 원소들에 대한 fail함수들을 저장

      IntStream.range(0, S.size()).forEach(row -> {
        var kmp = new KMP(S.get(row));
        fails[row] = kmp.fail();
      });

      // 유사 KMP compare 알고리즘
      int[] kmpPos = new int[S.size()]; // for 문을 보면 알 수 있듯이, L 글자 하나마다 모든 S를 방문하여 비교위치와 dp에 사용되는 sum값을 수정한다.
      for (int col = 0; col < L.length(); ++col) {
        if (col > 0) {
          sum[col] = sum[col - 1];
        }
        for (int row = 0; row < S.size(); ++row) {
          String s = S.get(row);
          // 글자 불일치
          while (kmpPos[row] > 0 && L.charAt(col) != s.charAt(kmpPos[row])) {
            kmpPos[row] = fails[row][kmpPos[row] - 1]; // j = fail(f - 1)
          }
          // 글자 일치
          if (L.charAt(col) == s.charAt(kmpPos[row])) {
            kmpPos[row] += 1;
            // 단어 일치
            if (kmpPos[row] == s.length()) {
              // DP사용
              var currentLength = kmpPos[row];
              var startIdx = col - currentLength;
              var rangedSum = sumBetween(sum, startIdx, col);
              if (rangedSum < currentLength) {
                sum[col] += currentLength - rangedSum;
              }
              // 모든 occurrences를 찾기 위해 다시 fail 시킨다.
              kmpPos[row] = fails[row][kmpPos[row] - 1];
            }
          }
        }
      }

      return sum[L.length() - 1];
    }
  }

  static int sumBetween(int[] cumulative, int start, int end) {
    if (start < 0)
      return cumulative[end];
    return cumulative[end] - cumulative[start];
  }

}
