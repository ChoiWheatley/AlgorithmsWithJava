package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import useful.Idx2D;

public class Ex1913 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      final int n = Integer.valueOf(br.readLine());
      final int m = Integer.valueOf(br.readLine());
      var submit = Solution.solution(n, m);
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          bw.write(submit.arr[i][j] + " ");
        }
        bw.write("\n");
      }
      bw.write(submit.pos.row() + " " + submit.pos.col() + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isOutOfBound(Idx2D pos, int n) {
    return (pos.row() < 0 || pos.row() >= n) ||
        (pos.col() < 0 || pos.col() >= n);
  }

  public static class Solution {
    public static class AnswerStruct {
      public final int[][] arr;
      public final Idx2D pos;

      public AnswerStruct(int[][] arr, Idx2D pos) {
        this.arr = arr;
        this.pos = pos;
      }

      @Override
      public boolean equals(Object obj) {
        if (obj instanceof AnswerStruct) {
          AnswerStruct other = (AnswerStruct) obj;
          return Arrays.deepEquals(arr, other.arr) && pos.equals(other.pos);
        }
        return false;
      }
    }

    public static AnswerStruct solution(int n, int m) {
      int elem = n * n;
      Idx2D mPos = null;
      assert m <= n * n;
      int[][] arr = new int[n][n];
      Idx2D pos = Idx2D.of(0, 0); // last element
      Queue<Idx2D.Direction> deltas = new LinkedList<>();
      deltas.add(Idx2D.Direction.DOWN);
      deltas.add(Idx2D.Direction.RIGHT);
      deltas.add(Idx2D.Direction.UP);
      deltas.add(Idx2D.Direction.LEFT);

      do {
        if (elem == m)
          mPos = pos.add(Idx2D.of(1, 1));
        arr[pos.row()][pos.col()] = elem;
        Idx2D nextPos = pos.add(deltas.peek());
        if (isOutOfBound(nextPos, n) ||
            arr[nextPos.row()][nextPos.col()] != 0) {
          Idx2D.Direction tmp = deltas.poll();
          deltas.offer(tmp);
          Idx2D.Direction delta = deltas.peek();
          pos = pos.add(delta);
        } else {
          pos = nextPos;
        }
      } while (elem-- > 1);

      return new AnswerStruct(arr, mPos);
    }
  }
}
