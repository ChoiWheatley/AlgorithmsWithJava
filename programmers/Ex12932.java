package programmers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12932
 * 자연수 n을 뒤집어 각 자리 숫자를 원소로 가지는 배열 형태로 리턴해주세요. 예를들어 n이 12345이면 [5,4,3,2,1]을
 * 리턴합니다.
 * 
 * long -> StringBuilder -> String -> List<Integer> -> int[] 로 변환하는 과정에 대해 서술한다.
 */

public class Ex12932 {
  public static void main(String[] args) {
    long n = 12345;
    StringBuilder reversed = new StringBuilder(
        String.valueOf(n))
        .reverse();
    String reversedStr = reversed.toString();

    List<Integer> result = new ArrayList<>();
    for (char c : reversedStr.toCharArray()) {
      Integer i = Integer.valueOf(
          String.valueOf(c));
      result.add(i);
    }
    int[] ret = result
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();

    Arrays.stream(ret).forEach(System.out::println);
  }

  @Test
  public void test1() {
    long n = 12345L;
    int[] answer = { 5, 4, 3, 2, 1 };
    assertArrayEquals(answer, solution(n));
  }

  public int[] solution(long n) {
    List<Integer> digits = new ArrayList<>();
    while (n > 0) {
      Integer rem = (int) (n % 10);
      digits.add(rem);
      n /= 10;
    }
    return digits
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
  }

  public int[] solution2(long n) {
    return new StringBuilder()
        .append(n)
        .reverse()
        .chars()
        .map(Character::getNumericValue)
        .toArray();
  }
}
