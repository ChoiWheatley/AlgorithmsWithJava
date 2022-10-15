package programmers;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12916
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Ex12916 {

  @Test
  public void test1() {
    var s = "pPoooyY";
    var answer = true;
    assertEquals(answer, solution(s));
    assertEquals(answer, solution2(s));
  }

  public boolean solution(String s) {
    final char[] FIND = { 'p', 'y' };
    int[] found = { 0, 0 };

    var lowercase = s.toLowerCase();
    for (char c : lowercase.toCharArray()) {
      for (int idx = 0; idx < FIND.length; ++idx) {
        if (c == FIND[idx]) {
          found[idx]++;
        }
      }
    }
    return found[0] == found[1];
  }

  public boolean solution2(String s) {
    var lowercase = s.toLowerCase();
    return (lowercase
        .chars()
        .filter(e -> e == 'p')
        .count()) == (lowercase
            .chars()
            .filter(e -> e == 'y')
            .count());
  }

}
