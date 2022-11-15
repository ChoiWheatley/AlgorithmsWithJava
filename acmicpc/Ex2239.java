package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex2239 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String[] lines = new String[Const2580.ID_MAX];
      for (int row = 0; row < Const2580.ID_MAX; ++row) {
        lines[row] = br.readLine();
      }

      // 이 문제는 띄어쓰기가 없다.
      int[][] sudoku = new int[Const2580.ID_MAX][Const2580.ID_MAX];
      for (int row = 0; row < Const2580.ID_MAX; ++row) {
        String line = lines[row];
        char[] sequence = line.toCharArray();
        for (int col = 0; col < Const2580.ID_MAX; ++col) {
          sudoku[row][col] = sequence[col] - '0';
        }
      }

      // do solve
      int[][] answer = Solver2580.solve(sudoku);
      for (int row = 0; row < Const2580.ID_MAX; ++row) {
        for (int col = 0; col < Const2580.ID_MAX; ++col) {
          bw.write(answer[row][col] + '0');
        }
        bw.write("\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
