package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Ex1786 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    String text = reader.readLine();
    String pattern = reader.readLine();

    List<Integer> submit = Solution.solution(text, pattern);
    System.out.println(submit.size());
    submit.forEach(e -> System.out.print(e + " "));
    System.out.println();

    reader.close();
  }

  public static class Solution {
    /** pattern이 text에서 나타나는 위치를 리턴한다. */
    public static List<Integer> solution(String text, String pattern) {
      KMP kmp = new KMP(pattern);
      List<Integer> ls = new ArrayList<>(text.length());
      kmp.compare(text, idx -> {
        // 성공했을 때의 인덱스임
        // text의 [i,i+m) 문자와 pattern의 [1,m] 문자가 일치한다면 i가 출력이 되는 셈.
        // 문제는 숫자를 1부터 센다는 점 유의
        ls.add(idx + 1);
      });
      return ls;
    }
  }

  public static class KMP {
    public String pattern;

    private int[] failArr;

    public KMP(String pattern) {
      this.pattern = pattern;
      this.failArr = createFail(pattern);
    }

    public int fail(int idx) {
      return failArr[idx];
    }

    private static int[] createFail(String pattern) {
      int[] failArr = new int[pattern.length()];
      int i = 1; // fail(0) 은 무조건 0이므로 생략해야 한다.
      int j = 0;
      for (; i < pattern.length(); ++i) {
        // 글자 불일치
        while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
          j = failArr[j - 1];
        }
        // 글자 일치
        if (pattern.charAt(i) == pattern.charAt(j)) {
          failArr[i] = ++j;
        }
      }

      return failArr;
    }

    public int[] fail() {
      return Arrays.copyOf(failArr, failArr.length);
    }

    public void compare(String text, Consumer<Integer> whenEqual) {
      int i = 0;
      int j = 0;
      for (; i < text.length(); ++i) {
        // 글자 불일치
        while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
          j = fail(j - 1);
        }
        // 글자 일치
        if (text.charAt(i) == pattern.charAt(j)) {
          // 단어 일치
          if (j == pattern.length() - 1) {
            // 현재 i는 패턴 처음이 아닌, 패턴의 끝에 도달해 있다.
            // 따라서 패턴과 텍스트가 처음 만나는 위치로 조절해 주어야 한다.
            whenEqual.accept(i - (pattern.length() - 1));
            // 모든 occurrences를 찾는 수를 가정한다. 따라서 j를 다시 fail 시켜야 한다.
            j = failArr[j];
          } else {
            ++j;
          }
        }
      }
    }
  }
}
