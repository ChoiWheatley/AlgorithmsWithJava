package programmers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12951
 * JadenCase 문자열 만들기
 */

public class Ex12951 {
  public static String jadenify(String s) {
    StringBuilder lowercased = new StringBuilder(s.toLowerCase());
    if (lowercased.length() == 0) {
      return "";
    }
    lowercased.setCharAt(0, Character.toUpperCase(lowercased.charAt(0)));
    return lowercased.toString();
  }

  public String solution(String s) {
    // 1. 쪼갠다
    List<String> splitted = new ArrayList<>();
    var tokenizer = new StringTokenizer(s, " ", true);
    while (tokenizer.hasMoreTokens()) {
      splitted.add(tokenizer.nextToken());
    }
    // 2. 첫 글자만 대문자로, 나머지 글자는 소문자로 만든다.
    String[] jadened = Stream.of(splitted.toArray(String[]::new))
        .map(e -> jadenify(e))
        .toArray(String[]::new);
    // 3. 붙인다
    StringBuilder result = new StringBuilder();
    for (var each : jadened) {
      result.append(each);
    }
    return result.toString();
  }

  @Test
  public void test1() {
    String s = "3people unFollowed me";
    String answer = "3people Unfollowed Me";
    assertEquals(answer, solution(s));
  }

  @Test
  public void test2() {
    String s = "for the last week";
    String answer = "For The Last Week";
    assertEquals(answer, solution(s));
  }

  @Test
  public void test3() {
    String s = "hello        my name is Choi    WheaTLEY";
    String answer = "Hello        My Name Is Choi    Wheatley";
    assertEquals(answer, solution(s));
  }

  @Test
  public void test4() {
    String s = "a aa ";
    String answer = "A Aa ";
    assertEquals(answer, solution(s));
  }
}
