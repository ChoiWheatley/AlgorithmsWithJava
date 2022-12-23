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

      boolean submit = Solution.solution(a, b);
      bw.write((submit ? 1 : 0) + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class Solution {
    /** b가 a의 부분문자열이면 true를 리턴한다. */
    public static boolean solution(String a, String b) {
      int[] fail = makeFail(b);
      int j = 0;
      for (int i = 0; i < a.length(); ++i) {
        // 글자 불일치
        while (j > 0 && a.charAt(i) != b.charAt(j)) {
          j = fail[j - 1];
        }
        // 글자 일치
        if (a.charAt(i) == b.charAt(j)) {
          if (j == b.length() - 1) {
            return true;
          } else {
            j++;
          }
        }
      }
      return false;
    }

    /** KMP 알고리즘을 활용하여 str 안에 접두어와 접미어가 중복되는 부분을 찾자. */
    public static int[] makeFail(String str) {
      int[] fail = new int[str.length()];
      int j = 0;
      for (int i = 1; i < str.length(); ++i) {
        while (j > 0 && str.charAt(i) != str.charAt(j)) { // follow fail backward until j goes to 0 or some occurrences
                                                          // is found
          j = fail[j - 1];
        }
        if (str.charAt(i) == str.charAt(j)) { // occurrence was found
          fail[i] = ++j;
        }
      }
      return fail;
    }
  }
}
