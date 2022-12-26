package useful;

import java.util.Arrays;
import java.util.function.Consumer;

public class KMP {
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

  public final int[] fail() {
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