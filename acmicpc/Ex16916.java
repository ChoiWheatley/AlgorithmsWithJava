package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex16916 {
  public static void main(String[] args) throws IOException {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String a = br.readLine();
      String b = br.readLine();

      boolean submit = Solver16916.solve(a, b);
      bw.write(submit ? "1" : "0");
      bw.write("\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver16916 {
  public static boolean solve(String a, String b) {
    char[] aArr = a.toCharArray();
    char[] bArr = b.toCharArray();

    return false;
  }

  /** 접두어, 접미어 일치여부 조사 */
  public static int[] failFunction(String keyword) {
    int[] ret = new int[keyword.length()];
    for (int i = 0; i < keyword.length(); ++i) {
      for (int len = 1; len <= i / 2; ++len) {
        int rightStart = i - len;
        int cnt = 0;
        for (int j = 0; j < len; ++j) {
          if (keyword.charAt(j) == keyword.charAt(rightStart + j))
            cnt++;
        }
        if (cnt == len)
          ret[i] = len;
      }
    }
    return ret;
  }
}