package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex25501 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      int testcases = Integer.valueOf(br.readLine());
      for (; testcases > 0; --testcases) {
        String line = br.readLine();
        var palindrome = new Palindrome();
        bw.write(palindrome.isPalindrome(line) + " " + palindrome.getCallCnt() + "\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Palindrome {
  private int callCnt = 0;

  private int recursion(String s, int l, int r) {
    callCnt++;
    if (l >= r)
      return 1;
    else if (s.charAt(l) != s.charAt(r))
      return 0;
    else
      return recursion(s, l + 1, r - 1);
  }

  public int isPalindrome(String s) {
    return recursion(s, 0, s.length() - 1);
  }

  public int getCallCnt() {
    return callCnt;
  }
}