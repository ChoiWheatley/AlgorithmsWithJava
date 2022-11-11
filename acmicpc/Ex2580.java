package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex2580 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    var writer = new BufferedWriter(new OutputStreamWriter(System.out));
    String[] lines = new String[Const2580.LEN];
    for (int row = 0; row < Const2580.LEN; ++row) {
      lines[row] = reader.readLine();
    }
    StringBuilder sb = new StringBuilder();
    int[][] unfinished = SudokuStringConverter.convert(lines);
    int[][] result = Solver2580.solve(unfinished);
    for (int i = 0; i < result.length; ++i) {
      for (int j = 0; j < result[i].length; ++j) {
        sb.append(result[i][j] + " ");
      }
      sb.append("\n");
    }

    writer.write(sb.toString());
    writer.flush();
  }
}

class Solver2580 {
  public static int[][] solve(int[][] unfinished) {
    int[][] ret = unfinished.clone();
    List<Pair<Integer, Integer>> zeros = findZeros(ret);
    int top = 0;

    while (top < zeros.size()) {
      var pair = zeros.get(top);
      int r = pair.first;
      int c = pair.second;
      boolean isPossible = false;
      int sample = 1;
      for (sample = 1; sample <= 9; ++sample) {
        if (testDuplicateCenterOf(r, c, sample, ret)) {
          ret[r][c] = sample;
          isPossible = true;
          break;
        }
      }
      if (!isPossible) {
        // revert last element into zero
        top--;
        var pair2 = zeros.get(top);
        var r2 = pair2.first;
        var c2 = pair2.second;
        ret[r2][c2] = 0;
      } else {
        top++;
      }
    }

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

  public static List<Pair<Integer, Integer>> findZeros(int[][] origin) {
    var ret = new ArrayList<Pair<Integer, Integer>>(Const2580.LEN * Const2580.LEN);
    for (int i = 0; i < origin.length; ++i) {
      for (int j = 0; j < origin[i].length; ++j) {
        if (origin[i][j] == 0) {
          ret.add(Pair.of(i, j));
        }
      }
    }
    return ret;
  }

  public static boolean hasDuplicateExceptZero(int[] sample) {
    Set<Integer> memo = new HashSet<>();
    for (var each : sample) {
      if (each != 0 && memo.contains(each))
        return false;
      memo.add(each);
    }
    return true;
  }

  public static boolean testDuplicateCenterOf(int row, int col, int value, int[][] origin) {
    boolean ret = true;
    int[][] safeArea = origin.clone();
    safeArea[row][col] = value;
    // rows,
    var line = safeArea[row];
    ret = ret && hasDuplicateExceptZero(line);
    // cols,
    var transposed = transpose(safeArea);
    line = transposed[col];
    ret = ret && hasDuplicateExceptZero(line);
    // 3x3 box
    Pair<Integer, Integer> idx2d = Const2580.getStartIndex(row, col);
    int[] box = get3x3StartWith(idx2d.first, idx2d.second, safeArea);
    ret = ret && hasDuplicateExceptZero(box);

    safeArea[row][col] = 0;
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

class Pair<A, B> {
  public A first;
  public B second;

  private Pair(A first, B second) {
    this.first = first;
    this.second = second;
  }

  public static <T, U> Pair<T, U> of(T first, U second) {
    return new Pair<>(first, second);
  }
}