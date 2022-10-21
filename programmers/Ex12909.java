package programmers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12909
 * 올바른 괄호
 */

public class Ex12909 {
  public boolean solution(String s) {
    char[] chArr = s.toCharArray();
    Stack<Object> stack = new Stack<>();

    if (chArr[chArr.length - 1] == '(') {
      return false;
    }

    try {
      for (var each : chArr) {
        if (each == '(') {
          stack.push(new Object());
        } else if (each == ')') {
          stack.pop();
        } else {
          return false;
        }
      }
    } catch (EmptyStackException e) {
      return false;
    }
    return stack.empty();
  }

  @Test
  public void test() {
    assertEquals(true, solution("()()"));
    assertEquals(true, solution("(())()"));
    assertEquals(false, solution(")()("));
    assertEquals(false, solution("(()("));
    assertEquals(false, solution("(()()"));
    assertEquals(false, solution("())))(((()")); // 이게 되겠냐?
  }
}
