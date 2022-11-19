package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.function.Function;
import java.util.stream.Stream;

import useful.Pair;

public class Ex2580 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    var writer = new BufferedWriter(new OutputStreamWriter(System.out));
    String[] lines = new String[Const2580.ID_MAX];
    for (int row = 0; row < Const2580.ID_MAX; ++row) {
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

final class Solver2580 {

  private int[][] sudoku;
  private SudokuChecker rowChecker;
  private SudokuChecker colChecker;
  private SudokuChecker squareChecker;

  public static int[][] solve(int[][] unfinished) {
    Solver2580 solver = new Solver2580(unfinished);
    // init
    for (int i = 0; i < solver.sudoku.length; ++i) {
      for (int j = 0; j < solver.sudoku[i].length; ++j) {
        var num = solver.sudoku[i][j];
        if (num != 0) {
          solver.rowChecker.register(i, num);
          solver.colChecker.register(j, num);
          solver.squareChecker.register(Const2580.getSquareId(i, j), num);
        }
      }
    }
    // do dfs
    solver.dfs(RowCol.of(0, 0));
    return solver.sudoku;
  }

  private boolean dfs(RowCol position) {
    var row = position.row();
    var col = position.col();
    if (row >= Const2580.ID_MAX ||
        col >= Const2580.ID_MAX) {
      return true;
    }
    var current = sudoku[row][col];
    // test start
    if (current == 0) {
      // 이제부터 1 ~ 9 다 실험해본다.
      for (int num = 1; num <= Const2580.ID_MAX; ++num) {
        if (rowChecker.isRegistered(row, num) == false &&
            colChecker.isRegistered(col, num) == false &&
            squareChecker.isRegistered(Const2580.getSquareId(row, col), num) == false) {
          rowChecker.register(row, num);
          colChecker.register(col, num);
          squareChecker.register(Const2580.getSquareId(row, col), num);
          sudoku[row][col] = num;
          // do recursion
          if (dfs(position.next())) {
            return true;
          }
          // 틀린 경우, 원상복귀 후 새 num으로 다시시도
          rowChecker.deregister(row, num);
          colChecker.deregister(col, num);
          squareChecker.deregister(Const2580.getSquareId(row, col), num);
          sudoku[row][col] = 0;
        }
      }
      // 여기까지 왔다면 이 경우 해결할 수 없는 문제임.
      return false;
    }
    // else
    return dfs(position.next());
  }

  private Solver2580(int[][] unfinished) {
    this.rowChecker = new SudokuChecker();
    this.colChecker = new SudokuChecker();
    this.squareChecker = new SudokuChecker();
    this.sudoku = new int[Const2580.ID_MAX][Const2580.ID_MAX];
    for (int i = 0; i < Const2580.ID_MAX; ++i) {
      for (int j = 0; j < Const2580.ID_MAX; ++j) {
        this.sudoku[i][j] = unfinished[i][j];
      }
    }
  }

}

class SudokuStringConverter {
  public static int[][] convert(String lines) {
    String[] splitted = lines.split("\n");
    return convert(splitted);
  }

  public static int[][] convert(String[] lines) {
    int[][] ret = new int[Const2580.ID_MAX][Const2580.ID_MAX];
    for (int row = 0; row < Const2580.ID_MAX; ++row) {
      String line = lines[row];
      String[] splitted = line.split(" ");
      int[] converted = Stream.of(splitted).mapToInt(Integer::valueOf).toArray();
      for (int col = 0; col < Const2580.ID_MAX; ++col) {
        ret[row][col] = converted[col];
      }
    }
    return ret;
  }
}

class Const2580 {
  public static final int ID_MAX = 9;

  public static void print(int[][] arr) {
    for (int i = 0; i < arr.length; ++i) {
      for (int j = 0; j < arr[i].length; ++j) {
        System.out.printf("%d ", arr[i][j]);
      }
      System.out.println();
    }
  }

  /**
   * each square is 3x3
   * +---+---+---+
   * | 0 | 1 | 2 |
   * +---+---+---+
   * | 3 | 4 | 5 |
   * +---+---+---+
   * | 6 | 7 | 8 |
   * +---+---+---+
   */
  public static int getSquareId(int row, int col) {
    Function<Integer, Integer> criterion = (Integer e) -> {
      int ret = 0;
      if (e < 3)
        ret = 0;
      if (3 <= e && e < 6)
        ret = 1;
      if (6 <= e)
        ret = 2;
      return ret;
    };
    int r = criterion.apply(row);
    int c = criterion.apply(col);
    return r * 3 + c;
  }
}

// class Pair<A, B> {
// public A first;
// public B second;

// protected Pair(A first, B second) {
// this.first = first;
// this.second = second;
// }

// public static <T, U> Pair<T, U> of(T first, U second) {
// return new Pair<>(first, second);
// }
// }

final class RowCol extends Pair<Integer, Integer> {
  private RowCol(Integer first, Integer second) {
    super(first, second);
  }

  public static RowCol of(Integer first, Integer second) {
    return new RowCol(first, second);
  }

  public int row() {
    return this.first;
  }

  public int col() {
    return this.second;
  }

  public RowCol next() {
    int nextRow = row();
    int nextCol = col();
    if (col() + 1 >= Const2580.ID_MAX) {
      nextRow++;
      nextCol = 0;
    } else {
      nextCol++;
    }
    return new RowCol(nextRow, nextCol);
  }
}

class SudokuChecker {
  private boolean[][] checkList = new boolean[Const2580.ID_MAX + 1][Const2580.ID_MAX + 1];

  public void register(int id, int num) {
    checkList[id][num] = true;
  }

  public void deregister(int id, int num) {
    checkList[id][num] = false;
  }

  public boolean isRegistered(int id, int num) {
    return checkList[id][num];
  }
}