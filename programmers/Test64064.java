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
  public void sample1() {
    int[][] matchedIndices = {
        { 0, 1 },
        { 0, 2 },
        { 3, 4 },
        { 3, 4 }, // 중복은 사전에 제거해야 할듯
    };
    int answer = 3;
    assertEquals(answer, Solution.allCaseOf(matchedIndices).size());
  }

  @Test
  public void sample2() {
    int[][] matchedIndices = {
        { 0, 1, 5 },
        { 0, 2 },
        { 3, 4 },
    };
    int answer = 10;
    assertEquals(answer, Solution.allCaseOf(matchedIndices).size());
  }

  @Test
  public void sample3() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "fr*d*", "abc1**" };
    int answer = 2;
    assertEquals(answer, Solution.solution(user_id, banned_id));
  }

  @Test
  public void sample4() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "*rodo", "*rodo", "******" };
    int answer = 2;
    assertEquals(answer, Solution.solution(user_id, banned_id));
  }

  @Test
  public void sample5() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "fr*d*", "*rodo", "******", "******" };
    int answer = 3;
    assertEquals(answer, Solution.solution(user_id, banned_id));
  }

  @Test
  public void solution2_1() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "fr*d*", "abc1**" };
    int answer = 2;
    assertEquals(answer, Solution2.solution(user_id, banned_id));
  }

  @Test
  public void solution2_2() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "*rodo", "*rodo", "******" };
    int answer = 2;
    assertEquals(answer, Solution.solution(user_id, banned_id));
  }

  @Test
  public void solution2_3() {
    String[] user_id = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
    String[] banned_id = { "fr*d*", "*rodo", "******", "******" };
    int answer = 3;
    assertEquals(answer, Solution.solution(user_id, banned_id));
  }

}
