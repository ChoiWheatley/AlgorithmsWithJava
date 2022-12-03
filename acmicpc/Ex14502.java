package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.TreeMap;

import useful.Idx2D;

import static useful.Idx2D.Direction;

public class Ex14502 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver14502 {

}

enum Stat {
  EMPTY, WALL, VIRUS;
}

final class Lab {
  private Stat[][] lab;

  public final int ROW;
  public final int COL;

  public Lab(int row, int col) {
    ROW = row;
    COL = col;
    lab = new Stat[ROW][COL];
    for (int i = 0; i < ROW; ++i) {
      for (int j = 0; j < COL; ++j) {
        lab[i][j] = Stat.EMPTY;
      }
    }
  }

  /**
   * 1. 바이러스 위치 파악 및 스택에 집어넣는다.
   * 2. 스택이 빌 때까지 다음을 반복한다.
   * 2.1. 현재 위치를 스택으로부터 하나 꺼낸다.
   * 2.2. 커서가 EMPTY인 경우에만 해당 위치를 감염시킨다.
   * 2.3. 4방향을 스택에 집어넣는다.
   */
  public void diffuse() {
    // 1
    Stack<Idx2D> stack = new Stack<>();
    for (int i = 0; i < ROW; ++i) {
      for (int j = 0; j < COL; ++j) {
        if (lab[i][j] == Stat.VIRUS) {
          stack.push(Idx2D.of(i, j));
        }
      }
    }
    // 2
    boolean[][] visited = new boolean[ROW][COL];
    while (!stack.isEmpty()) {
      var cursor = stack.pop();
      var r = cursor.row();
      var c = cursor.col();
      getStat(cursor).ifPresent(stat -> {
        if (stat != Stat.WALL && visited[r][c] == false) {
          visited[r][c] = true;
          set(cursor, Stat.VIRUS);
          stack.push(cursor.add(Direction.UP));
          stack.push(cursor.add(Direction.DOWN));
          stack.push(cursor.add(Direction.LEFT));
          stack.push(cursor.add(Direction.RIGHT));
        }
      });
    }
  }

  public final Optional<Stat> getStat(Idx2D idx) {
    if (idx.row() < 0 || idx.col() < 0 ||
        idx.row() >= ROW || idx.col() >= COL) {
      return Optional.empty();
    }
    return Optional.of(lab[idx.first][idx.second]);
  }

  public final Stat[][] getStats() {
    return lab;
  }

  public void setWall(Idx2D at) {
    set(at, Stat.WALL);
  }

  public void setVirus(Idx2D at) {
    set(at, Stat.VIRUS);
  }

  protected void set(Idx2D at, Stat to) {
    lab[at.first][at.second] = to;
  }
}