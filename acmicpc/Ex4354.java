package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex4354 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    var writer = new BufferedWriter(new OutputStreamWriter(System.out));

    while (true) {
      String line = reader.readLine();
      if (line.compareTo(".") == 0) {
        break;
      }
      writer.write(Solution.solution(line) + "\n");
    }

    reader.close();
    writer.flush();
    writer.close();
  }

  public static class Solution {
    /**
     * KMP fail 함수를 계산할 때 임의의 x에 대하여
     * fail(x) 가 x - fail(x) + 1의 배수인 경우, 우리는
     * 길이 x - fail(x) + 1의 중복되는 문자열을 찾았다고
     * 말할 수 있다.
     *
     * S = a^i 라고 하자, 이때 a는 어떤 문자열의 지수승이 존재하지 않다고 가정한다.
     * m을 a의 길이라고 하자,
     * | x | S[0:x] | fail(x) | x - fail(x) - 1 |
     * | 0 | a | 0 | 1 |
     * | m*1-1 | a | 0 | m |
     * | m*2-1 | a*a | m | m |
     * | m*3-1 | a*a*a | m*2 | m |
     * | ... | ... | ... | ... |
     * | m*i-1 | a^i | m * (i - 1) | m |
     *
     * fail 함수는 가장 긴 일치하는 접두사와 접미사의 길이를 리턴한다는 점을 이용했다.
     */
    public static int solution(String pattern) {
      int max = 1;

      int[] fail = getFail(pattern);
      int lastIndex = fail.length - 1;
      int m = lastIndex - fail[lastIndex] + 1;
      if (fail[lastIndex] % m == 0) {
        max = fail.length / m;
      }

      return max;
    }

    public static int[] getFail(String pattern) {
      int[] fail = new int[pattern.length()];
      int x = 1; // 1부터 시작한다, 0번째부터 비교하는 의미가 없기 때문.
      int j = 0;
      for (; x < pattern.length(); ++x) {
        // 글자 불일치
        while (j > 0 && (pattern.charAt(x) != pattern.charAt(j))) {
          j = fail[j - 1];
        }
        // 글자 일치
        if (pattern.charAt(x) == pattern.charAt(j)) {
          j += 1;
          fail[x] = j;
        }
      }
      return fail;
    }
  }
}
