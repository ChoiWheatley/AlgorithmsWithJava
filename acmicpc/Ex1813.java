package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Ex1813 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      int n = Integer.valueOf(line);
      line = br.readLine();
      StringTokenizer st = new StringTokenizer(line);
      Integer[] ls = new Integer[n];
      for (int i = 0; i < n; ++i) {
        ls[i] = Integer.valueOf(st.nextToken());
      }
      bw.write(Solver1813.solve(ls) + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver1813 {

  /**
   * N개의 말이 참인 말의 개수가 N개.
   * 모순의 경우는 0이 명제에 주어지고 동시에 0개의 말이 참이면 된다.
   * 왜냐면 "0개의 말이 참" 과 "0개의 말이 참이 아니다"가 서로 충돌하기 때문.
   * https://skeptic9999.tistory.com/6
   */
  public static int solve(Integer[] ls) {
    int max = 0;
    boolean flag = false;
    for (int i = 0; i < ls.length; ++i) {
      if (ls[i] == 0)
        flag = true;
      if (ls[i] == count(ls, ls[i])) {
        max = Integer.max(max, ls[i]);
      }
    }
    if (flag)
      return max > 0 ? max : -1;
    return max;
  }

  public static <T> int count(T[] ls, T test) {
    int cnt = 0;
    for (int i = 0; i < ls.length; ++i) {
      if (test == ls[i])
        cnt++;
    }
    return cnt;
  }
}