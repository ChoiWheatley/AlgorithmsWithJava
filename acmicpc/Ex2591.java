package acmicpc;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Ex2591 {
  public static final int MAX = 34;
  public static final Collection<String> exceptCases = List.of(
      "10", "20", "30");

  public static void main(String[] args) {
    var reader = new Scanner(System.in);

    String line = reader.nextLine();
    int submit = Solution.solution(line);
    System.out.println(submit);

    reader.close();
  }

  public static class Solution {
    /**
     * a(i)를 i개의 숫자로 만들 수 있는 경우의 수라고 하자,
     * a(0) = 1
     * a(1) = 1
     * a(i) = {
     * .... a(i-1) ........ => if Integer of (str[i-1] + str[i]) > 34
     * ........................... OR str[i-1] is "0"
     * ........................... OR str[i-1] + str[i] is "10" or "20" or "30"
     * ........................... OR str[i] + str[i+1] is "10" or "20" or "30"
     * .... a(i-1) + a(i-2) => if Integer of (str[i-1] + str[i]) <= 34
     * }
     */
    public static int solution(String str) {
      int[] a = new int[str.length() + 1];
      a[0] = 1;
      a[1] = 1;
      for (int len = 2; len <= str.length(); ++len) {

        String left = str.substring(len - 2, len);
        String right = "";
        if (len < str.length())
          right = str.substring(len - 1, len + 1);

        if (Integer.valueOf(left) > MAX ||
            str.charAt(len - 2) == '0' ||
            exceptCases.stream().anyMatch(right::equals) ||
            exceptCases.stream().anyMatch(left::equals)) {
          a[len] = a[len - 1];
        } else {
          a[len] = a[len - 1] + a[len - 2];
        }
      }
      return a[str.length()];
    }
  }
}
