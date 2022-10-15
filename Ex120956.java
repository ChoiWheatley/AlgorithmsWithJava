
/**
 * Ex120956
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  public static final String[] words = { "aya", "ye", "woo", "ma" };

  public static int solution(String[] babbling) {
    var answer = 0;
    for (var b : babbling) {
      StringBuilder babble = new StringBuilder(b);
      List<String> mutableWords = Arrays.asList(words);
      for (var word : words) {
        var startidx = babble.indexOf(word);
        if (startidx != -1) {
          var endidx = startidx + word.length();
          babble.delete(startidx, endidx);
        }
      }
      if (babble.length() == 0) {
        answer++;
      }
    }

    return answer;
  }
}