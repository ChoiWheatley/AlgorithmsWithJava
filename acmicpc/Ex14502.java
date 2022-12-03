package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
  /**
   * 벽을 세 개 세우는 최악의 경우의 수는 C(54, 3) = 24804이다. 완탐 돌려도 되겠다.
   */
  public static int solve(int[][] labRaw) {
    // 1. init
    var lab = new Lab(labRaw.length, labRaw[0].length);
    Stat[] ordinals = Stat.values();
    for (int i = 0; i < labRaw.length; ++i) {
      for (int j = 0; j < labRaw[i].length; ++j) {
        lab.set(Idx2D.of(i, j), ordinals[labRaw[i][j]]);
      }
    }
    return recursion(lab, 3, Idx2D.of(0, 0));
  }

  public static int recursion(Lab lab, int remain, Idx2D idx) {
    if (remain == 0) {
      // diffuse and count
      Lab clone = new Lab(lab);
      clone.diffuse();
      int cnt = (int) clone.stream().filter(e -> e == Stat.EMPTY).count();
      return cnt;
    }
    if (lab.isOutOfBounds(idx))
      return 0;

    int best = 0;

    // set up wall
    lab.set(idx, Stat.WALL);
    int yesWall = recursion(lab, remain - 1, lab.nextIdxOf(idx));

    // no wall
    lab.set(idx, Stat.EMPTY);
    int noWall = recursion(lab, remain, lab.nextIdxOf(idx));

    best = Math.max(yesWall, noWall);
    return best;
  }

  public static enum Stat {
    EMPTY, WALL, VIRUS;
  }

  public static class Lab {
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

    public Lab(Lab other) {
      ROW = other.ROW;
      COL = other.COL;
      Stat[][] o = other.getStats();
      for (int i = 0; i < ROW; ++i) {
        for (int j = 0; j < COL; ++j) {
          this.lab[i][j] = o[i][j];
        }
      }
    }

    public Stream<Stat> stream() {
      Stream<Stat> ret = Stream.of();
      for (int i = 0; i < ROW; ++i) {
        ret = Stream.concat(ret, Stream.of(lab[i]));
      }
      return ret;
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
      if (isOutOfBounds(idx)) {
        return Optional.empty();
      }
      return Optional.of(lab[idx.first][idx.second]);
    }

    public void setWall(Idx2D at) {
      set(at, Stat.WALL);
    }

    public void setVirus(Idx2D at) {
      set(at, Stat.VIRUS);
    }

    public boolean isOutOfBounds(Idx2D idx) {
      return idx.row() < 0 || idx.col() < 0 ||
          idx.row() >= ROW || idx.col() >= COL;
    }

    public Idx2D nextIdxOf(Idx2D idx) {
      if (idx.col() >= COL) {
        return Idx2D.of(idx.row() + 1, 0);
      } else {
        return Idx2D.of(idx.row(), idx.col() + 1);
      }
    }

    protected void set(Idx2D at, Stat to) {
      lab[at.first][at.second] = to;
    }

    protected final Stat[][] getStats() {
      return lab;
    }
  }
}
