package acmicpc;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.IntStream;

import org.junit.Test;

public class Test2580 {
  public static String sampleStr = """
      0 3 5 4 6 9 2 7 8
      7 8 2 1 0 5 6 0 9
      0 6 0 2 7 8 1 3 5
      3 2 1 0 4 6 8 9 7
      8 0 4 9 1 3 5 0 6
      5 9 6 8 2 0 4 1 3
      9 1 7 6 5 2 0 8 0
      6 0 3 7 0 1 9 5 2
      2 5 8 3 9 4 7 6 0""";
  public static String answerStr = """
      1 3 5 4 6 9 2 7 8
      7 8 2 1 3 5 6 4 9
      4 6 9 2 7 8 1 3 5
      3 2 1 5 4 6 8 9 7
      8 7 4 9 1 3 5 2 6
      5 9 6 8 2 7 4 1 3
      9 1 7 6 5 2 3 8 4
      6 4 3 7 8 1 9 5 2
      2 5 8 3 9 4 7 6 1""";

  @Test
  public void converterTest() {
    int[][] answer = new int[][] {
        { 0, 3, 5, 4, 6, 9, 2, 7, 8 },
        { 7, 8, 2, 1, 0, 5, 6, 0, 9 },
        { 0, 6, 0, 2, 7, 8, 1, 3, 5 },
        { 3, 2, 1, 0, 4, 6, 8, 9, 7 },
        { 8, 0, 4, 9, 1, 3, 5, 0, 6 },
        { 5, 9, 6, 8, 2, 0, 4, 1, 3 },
        { 9, 1, 7, 6, 5, 2, 0, 8, 0 },
        { 6, 0, 3, 7, 0, 1, 9, 5, 2 },
        { 2, 5, 8, 3, 9, 4, 7, 6, 0 },
    };

    assertArrayEquals(answer, SudokuStringConverter.convert(sampleStr));
  }

  @Test
  public void checkOne2NineTest() {
    var stream = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    var stream2 = IntStream.range(1, 10);
    assertEquals(true, Solver2580.checkOne2Nine(stream));
    assertEquals(true, Solver2580.checkOne2Nine(stream2));
    var stream3 = IntStream.of(0, 3, 5, 4, 6, 9, 2, 7, 8);
    assertEquals(false, Solver2580.checkOne2Nine(stream3));
    var stream4 = IntStream.of(1, 3, 5, 4, 6, 9, 2, 7, 8);
    assertEquals(true, Solver2580.checkOne2Nine(stream4));
    var stream5 = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 10);
    assertEquals(false, Solver2580.checkOne2Nine(stream5));
  }

  @Test
  public void validationTest() {
    int[][] answerArr = SudokuStringConverter.convert(answerStr);
    assertEquals(true, Solver2580.validate(answerArr));
  }

  @Test
  public void validationTest2() {
    int[][] sampleArr = SudokuStringConverter.convert(sampleStr);
    assertEquals(false, Solver2580.validate(sampleArr));
  }

  @Test
  public void get3x3Test() {
    int[][] sampleArr = SudokuStringConverter.convert(answerStr);
    var start0x0 = new int[] { 1, 3, 5, 7, 8, 2, 4, 6, 9 };
    var start0x3 = new int[] { 4, 6, 9, 1, 3, 5, 2, 7, 8 };
    var start0x6 = new int[] { 2, 7, 8, 6, 4, 9, 1, 3, 5 };
    var start3x0 = new int[] { 3, 2, 1, 8, 7, 4, 5, 9, 6 };
    var start3x3 = new int[] { 5, 4, 6, 9, 1, 3, 8, 2, 7 };
    var start3x6 = new int[] { 8, 9, 7, 5, 2, 6, 4, 1, 3 };
    var start6x0 = new int[] { 9, 1, 7, 6, 4, 3, 2, 5, 8 };
    var start6x3 = new int[] { 6, 5, 2, 7, 8, 1, 3, 9, 4 };
    var start6x6 = new int[] { 3, 8, 4, 9, 5, 2, 7, 6, 1 };

    assertArrayEquals(start0x0, Solver2580.get3x3StartWith(0, 0, sampleArr));
    assertArrayEquals(start0x3, Solver2580.get3x3StartWith(0, 3, sampleArr));
    assertArrayEquals(start0x6, Solver2580.get3x3StartWith(0, 6, sampleArr));
    assertArrayEquals(start3x0, Solver2580.get3x3StartWith(3, 0, sampleArr));
    assertArrayEquals(start3x3, Solver2580.get3x3StartWith(3, 3, sampleArr));
    assertArrayEquals(start3x6, Solver2580.get3x3StartWith(3, 6, sampleArr));
    assertArrayEquals(start6x0, Solver2580.get3x3StartWith(6, 0, sampleArr));
    assertArrayEquals(start6x3, Solver2580.get3x3StartWith(6, 3, sampleArr));
    assertArrayEquals(start6x6, Solver2580.get3x3StartWith(6, 6, sampleArr));
  }

  @Test
  public void testDuplicateCenterOfTest() {
    int[][] sampleArr = SudokuStringConverter.convert(sampleStr);
    // (0,0) -> 1 이면 정답
    assertEquals(true, Solver2580.testDuplicateCenterOf(0, 0, 1, sampleArr));
    // (0,0) -> 3,5,7,8,2,6,4,9 이면 모두 오답
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 2, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 3, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 4, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 5, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 6, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 7, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 8, sampleArr));
    assertEquals(false, Solver2580.testDuplicateCenterOf(0, 0, 9, sampleArr));
  }

  @Test
  public void solve1() {
    int[][] sampleArr = SudokuStringConverter.convert(sampleStr);
    int[][] submit = Solver2580.solve(sampleArr);
    for (int i = 0; i < submit.length; ++i) {
      for (int j = 0; j < submit[i].length; ++j) {
        System.out.printf("%d ", submit[i][j]);
      }
      System.out.println();
    }
    assertEquals(true, Solver2580.validate(submit));
  }

  @Test
  public void timeoutTest() {
    int[][] sampleArr = new int[Const2580.LEN][Const2580.LEN];
    int[][] submit = Solver2580.solve(sampleArr);
    for (int i = 0; i < submit.length; ++i) {
      for (int j = 0; j < submit[i].length; ++j) {
        System.out.printf("%d ", submit[i][j]);
      }
      System.out.println();
    }
  }
}
