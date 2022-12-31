package acmicpc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Ex1449 {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int N = s.nextInt();
    int L = s.nextInt();
    List<Integer> ls = new ArrayList<>(N);
    while (N-- > 0) {
      ls.add(s.nextInt());
    }
    int submit = Solution.solution(ls, L);
    System.out.println(submit);
  }

  public static class Solution {
    public static int solution(List<Integer> ls, int tapeLength) {
      ls.sort(Comparator.naturalOrder());
      int answer = 0;

      int start = 0;
      int end = 0;
      for (; end < ls.size(); ++end) {
        int len = ls.get(end) - ls.get(start);
        if (len >= tapeLength) {
          // 중간값의 원리에 의해 tapeLength를 지나친다.
          answer++;
          start = end;
        }
      }
      // 마지막 길이를 재는 곳
      int len = ls.get(end - 1) - ls.get(start) + 1;
      if (len <= tapeLength) {
        answer++;
      }

      return answer;
    }
  }
}