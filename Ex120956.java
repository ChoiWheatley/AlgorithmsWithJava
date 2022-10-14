
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
    // for (var b : babbling) {
    // StringBuilder babble = new StringBuilder(b);
    // List<String> mutableWords = Arrays.asList(words);
    // for (var word : words) {
    // var startidx = babble.indexOf(word);
    // if (startidx != -1) {
    // var endidx = startidx + word.length();
    // babble.delete(startidx, endidx);
    // }
    // }
    // if (babble.length() == 0) {
    // answer++;
    // }
    // }

    for (var b : babbling) {
      var found = false;
      while (!found) {
      }
      if (found) {
        answer++;
      }
    }

    return answer;
  }

  @Test
  public void testAssess1() {
    List<String> words = Arrays.asList("aa", "bb", "cc");
    String babble = "aabbcc";
    assertEquals(true, assess(babble, words));
  }

  @Test
  public void testAssess2() {
    List<String> words = Arrays.asList("aa", "bb", "cc");
    String babble = "aabbcccc";
    assertEquals(false, assess(babble, words));
  }

  public static boolean assess(String babble, List<String> words) {
    var ret = true;
    for (var w : words) {
      var remain = deleteWord(babble, w);
      if (remain.equals(babble)) {
        // not found
        continue;
      }
      // found
      List<String> tmpWords = new ArrayList<>(words);
      tmpWords.remove(w);
      ret = assess(remain, tmpWords);
    }
    return ret;
  }

  /**
   * 문자열에 내가 원하는 단어를 제거한다.
   * 
   * @param babble 평가 하려는 문자열
   * @param word   비교
   * @return babble에서 찾은 첫 번째 word를 제거한 문자열
   */
  public static String deleteWord(String babble, String word) {
    var startidx = babble.indexOf(word);
    var endidx = startidx + word.length();
    var ret = new StringBuilder(babble);
    ret.delete(startidx, endidx);
    return ret.toString();
  }
}