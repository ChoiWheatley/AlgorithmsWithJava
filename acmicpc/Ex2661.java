package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ex2661 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.valueOf(reader.readLine());
    int[] answer = Solver2661.solve(n);
    for (int each : answer) {
      System.out.print(each);
    }
    System.out.println();
  }
}

class Solver2661 {
  static final int MAX = 3;

  public static int[] solve(int n) {
    int[] ls = new int[n];

    int top = 1;
    // TODO: loop로 구현하는 스택
    for (int i = 0; i < n;) {
      boolean flag = false;
      for (int j = 0; j < MAX; ++j) {
        ls[i]++;
        if (isGoodSequence(ls, top)) {
          top++;
          flag = true;
          break;
        }
      }
      if (!flag) {
        ls[i] = 0;
        i--;
        top--;
      } else {
        i++;
      }
    }
    return ls;
  }

  /**
   * 부분수열이 좋은수열인지 검증한다.
   * 
   * @param ls  길이 n짜리 수열
   * @param len 현재까지 쌓아올린 수열의 길이
   */
  public static boolean isGoodSequence(int[] ls, int len) {
    // 어차피 log(N,2)번만 반복 수행하면 된다.
    // 맨 뒤에서부터 샘플링
    for (int sampleLength = 2; sampleLength <= len; sampleLength *= 2) {
      int bot = len - sampleLength;
      int mid = bot + (sampleLength / 2);
      boolean isSame = true;
      // check same sequence
      for (int i = 0; i < (sampleLength / 2); ++i) {
        if (ls[bot + i] != ls[mid + i]) {
          isSame = false;
        }
      }
      if (isSame) {
        return false;
      }
    }
    return true;
  }
}
