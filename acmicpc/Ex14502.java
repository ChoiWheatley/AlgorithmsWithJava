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
    ROW = row + 2;
    COL = col + 2;
    lab = new Stat[ROW][COL];
    for (int i = 0; i < ROW; ++i) {
      for (int j = 0; j < COL; ++j) {
        lab[i][j] = Stat.EMPTY;
      }
    }
    // 바깥쪽 부분에 한 겹의 벽을 생성한다.
    for (int j = 0; j < COL; ++j) {
      lab[0][j] = Stat.WALL;
      lab[ROW - 1][j] = Stat.WALL;
    }
    for (int i = 0; i < ROW; ++i) {
      lab[i][0] = Stat.WALL;
      lab[i][COL - 1] = Stat.WALL;
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
    while (!stack.isEmpty()) {
      var cursor = stack.pop();
      if (get(cursor) == Stat.EMPTY) {
        set(cursor, Stat.VIRUS);
        stack.push(cursor.add(Direction.UP));
        stack.push(cursor.add(Direction.DOWN));
        stack.push(cursor.add(Direction.LEFT));
        stack.push(cursor.add(Direction.RIGHT));
      }
    }
  }

  public final Stat get(Idx2D idx) {
    return lab[idx.first][idx.second];
  }

  public final Stat[][] getStatus() {
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