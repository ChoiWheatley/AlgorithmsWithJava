
/**
 * Ex120956
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.*;

public class Ex120956 {
  public static void main(String[] args) {

  }

  @Test
  public void test1() {
    String[] babbling = { "ayaye", "uuu", "yeye", "yemawoo", "ayaayaa" };
    assertEquals(2, solution(babbling));
  }

  @Test
  public void test2() {
    String[] babbling = { "aya", "yee", "u", "maa" };
    assertEquals(1, solution(babbling));
  }

  @Test
  public void test3() {
    String[] babbling = { "ayayeaya" };
    assertEquals(1, solution(babbling));
  }

  @Test
  public void testDup() {
    String[] babbling = { "ayaaya", "yeye", "woowoo", "mama" };
    assertEquals(0, solution(babbling));
  }

  @Test
  public void testLong() {
    String[] babbling = { "ayayewoomaayayewooma" };
    assertEquals(1, solution(babbling));
  }

  @Test
  public void testExceptWords() {
    List<String> expect = new ArrayList<>();
    for (var w : words) {
      expect.add(w + w);
    }
    String[] array = expect.toArray(String[]::new);

    System.out.println("expect = ");
    Stream.of(array)
        .forEach(System.out::println);

    System.out.println("exceptWords = ");
    Stream.of(exceptWords)
        .forEach(System.out::println);

    assertTrue(Arrays.equals(array, exceptWords));
  }

  public static final String[] words = { "aya", "ye", "woo", "ma" };
  public static final String[] exceptWords = Stream
      .of(words)
      .map(e -> e + e)
      .toArray(String[]::new);

  public static int solution(String[] babbling) {
    var answer = 0;
    OUTER_LOOP: for (var b : babbling) {
      for (var except : exceptWords) {
        if (b.contains(except)) {
          continue OUTER_LOOP;
        }
      }

      for (var word : words) {
        // 일치하는 단어를 걍 다 지워버림
        b = b.replaceAll(word, "");
      }
      if (b.length() == 0) {
        answer++;
      }
    }
    return answer;
  }
}