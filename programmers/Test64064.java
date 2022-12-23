package programmers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static programmers.Ex64064.*;

import java.util.Arrays;

import org.junit.Test;

public class Test64064 {
  @Test
  public void replaceTest() {
    var origin = "fr*d**";
    var answer = "fr?d??";
    assertEquals(answer, origin.replace("*", "?"));
  }

  @Test
  public void pattern() {
    var value = "frodo";
    var pattern = "*ro**";
    var answer = true;
    assertEquals(answer, Solution.match(value, pattern));

    pattern = "*****";
    assertEquals(answer, Solution.match(value, pattern));
  }

  @Test
  public void matchedIndices() {
    String[] matches = {
        "frodo",
        "fradi",
        "crodo",
        "abc123",
        "frodoc",
    };
    String[] patterns = {
        "fr*d*",
        "*rodo",
        "******",
        "******",
    };
    int[][] answers = {
        { 0, 1 },
        { 0, 2 },
        { 3, 4 },
        { 3, 4 },
    };
    for (int i = 0; i < patterns.length; ++i) {
      int[] submit = Solution.possibleIndicesOf(matches, patterns[i]);
      assertArrayEquals(answers[i], submit,
          String.format("answers[%d]: %s, submit: %s\n",
              i,
              Arrays.toString(answers[i]),
              Arrays.toString(submit)));
    }
  }

  @Test
  public void count() {
    int[][] matchedIndices = {
        { 0, 1 },
        { 0, 2 },
        { 3, 4 },
        // { 3, 4 }, // 중복은 사전에 제거해야 할듯
    };
    int answer = 3;
    assertEquals(answer, Solution.count(matchedIndices, 5));
  }

  @Test
  public void sample1() {

  }
}
