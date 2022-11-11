package acmicpc;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex2580 {
  public static void main(String[] args) {

  }
}

class Solver2580 {
  public static int[][] solve(int[][] unfinished) {
    int[][] ret = unfinished.clone();

    return ret;
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
    return true;
  }

  public static boolean checkOne2Nine(IntStream stream) {
    boolean[] checklist = new boolean[10];
    checklist[0] = true; // not used
    stream.forEach(e -> checklist[e] = true);
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
    int[] ret = new int[Const2580.LEN];
    int cnt = 0;
    for (int rowCnt = 0; rowCnt < 3; ++rowCnt) {
      for (int colCnt = 0; colCnt < 3; ++colCnt) {
        var sample = origin[row + rowCnt][col + colCnt];
        ret[cnt++] = sample;
      }
    }
    return ret;
  }

}

class SudokuStringConverter {
  public static int[][] convert(String lines) {
    String[] splitted = lines.split("\n");
    return convert(splitted);
  }

  public static int[][] convert(String[] lines) {
    int[][] ret = new int[Const2580.LEN][Const2580.LEN];
    for (int row = 0; row < lines.length; ++row) {
      String line = lines[row];
      String[] splitted = line.split(" ");
      int[] converted = Stream.of(splitted).mapToInt(Integer::valueOf).toArray();
      for (int col = 0; col < converted.length; ++col) {
        ret[row][col] = converted[col];
      }
    }
    return ret;
  }
}

class Const2580 {
  public static final int LEN = 9;
}