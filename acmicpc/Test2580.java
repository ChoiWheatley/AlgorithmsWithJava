package acmicpc;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.function.Function;
import java.util.stream.IntStream;

import org.junit.Test;

import useful.Pair;

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
    assertEquals(true, checkOne2Nine(stream));
    assertEquals(true, checkOne2Nine(stream2));
    var stream3 = IntStream.of(0, 3, 5, 4, 6, 9, 2, 7, 8);
    assertEquals(false, checkOne2Nine(stream3));
    var stream4 = IntStream.of(1, 3, 5, 4, 6, 9, 2, 7, 8);
    assertEquals(true, checkOne2Nine(stream4));
    var stream5 = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 10);
    assertEquals(false, checkOne2Nine(stream5));
  }

  @Test
  public void validationTest() {
    int[][] answerArr = SudokuStringConverter.convert(answerStr);
    assertEquals(true, validate(answerArr));
  }

  @Test
  public void validationTest2() {
    int[][] sampleArr = SudokuStringConverter.convert(sampleStr);
    assertEquals(false, validate(sampleArr));
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

    assertArrayEquals(start0x0, get3x3StartWith(0, 0, sampleArr));
    assertArrayEquals(start0x3, get3x3StartWith(0, 3, sampleArr));
    assertArrayEquals(start0x6, get3x3StartWith(0, 6, sampleArr));
    assertArrayEquals(start3x0, get3x3StartWith(3, 0, sampleArr));
    assertArrayEquals(start3x3, get3x3StartWith(3, 3, sampleArr));
    assertArrayEquals(start3x6, get3x3StartWith(3, 6, sampleArr));
    assertArrayEquals(start6x0, get3x3StartWith(6, 0, sampleArr));
    assertArrayEquals(start6x3, get3x3StartWith(6, 3, sampleArr));
    assertArrayEquals(start6x6, get3x3StartWith(6, 6, sampleArr));
  }

  @Test
  public void solve1() {
    int[][] sampleArr = SudokuStringConverter.convert(sampleStr);
    int[][] submit = Solver2580.solve(sampleArr);
    Const2580.print(submit);
    assertEquals(true, validate(submit));
  }

  @Test
  public void timeoutTest() {
    int[][] sampleArr = new int[Const2580.ID_MAX][Const2580.ID_MAX];
    int[][] submit = Solver2580.solve(sampleArr);
    Const2580.print(submit);
  }

  public static boolean validate(int[][] sudoku) {
    // every rows
    for (int row = 0; row < sudoku.length; ++row) {
      IntStream line = IntStream.of(sudoku[row]);
      if (!checkOne2Nine(line))
        return false;
    }
    // every cols
    int[][] transposed = transpose(sudoku);
    for (int col = 0; col < transposed.length; ++col) {
      IntStream line = IntStream.of(transposed[col]);
      if (!checkOne2Nine(line))
        return false;
    }
    // every 3x3 boxes
    for (int i = 0; i < 9; i += 3) {
      for (int j = 0; j < 9; j += 3) {
        var rowcol = getStartIndex(i, j);
        int[] box = get3x3StartWith(rowcol.first, rowcol.second, sudoku);
        if (!checkOne2Nine(IntStream.of(box))) {
          return false;
        }
      }
    }
    return true;
  }

  public static boolean checkOne2Nine(IntStream stream) {
    boolean[] checklist = new boolean[10];
    checklist[0] = true; // not used
    try {
      stream.forEach(e -> checklist[e] = true);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
    for (boolean each : checklist) {
      if (!each)
        return false;
    }
    return true;
  }

  // i행과 j열을 뒤바꾼다.
  public static int[][] transpose(int[][] origin) {
    int[][] ret = new int[origin.length][origin[0].length];
    for (int j = 0; j < origin.length; ++j) {
      for (int i = 0; i < origin[0].length; ++i) {
        ret[i][j] = origin[j][i];
      }
    }
    return ret;
  }

  /**
   * (row, col) 위치에서부터 3x3 소행렬을 구해 리턴한다.
   * 
   * @param origin 9x9 스도쿠
   */
  public static int[] get3x3StartWith(int row, int col, int[][] origin) {
    int[] ret = new int[Const2580.ID_MAX];
    int cnt = 0;
    for (int rowCnt = 0; rowCnt < 3; ++rowCnt) {
      for (int colCnt = 0; colCnt < 3; ++colCnt) {
        try {
          int r = row + rowCnt;
          int c = col + colCnt;
          var sample = origin[r][c];
          ret[cnt++] = sample;
        } catch (IndexOutOfBoundsException e) {
          e.printStackTrace();
          System.exit(1);
        }
      }
    }
    return ret;
  }

  public static Pair<Integer, Integer> getStartIndex(int row, int col) {
    Function<Integer, Integer> criterion = (Integer e) -> {
      int ret = 0;
      if (e < 3)
        ret = 0;
      if (3 <= e && e < 6)
        ret = 3;
      if (6 <= e)
        ret = 6;
      return ret;
    };
    return Pair.of(criterion.apply(row), criterion.apply(col));
  }
}
