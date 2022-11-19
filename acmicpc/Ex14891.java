package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex14891 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      int[] cogs = new int[4];
      for (int i = 0; i < 4; ++i) {
        String line = br.readLine();
        cogs[i] = Integer.valueOf(line, 2);
      }

      int k = Integer.valueOf(br.readLine());
      int[] indices = new int[k];
      boolean[] isCCWs = new boolean[k];
      for (int i = 0; i < k; ++i) {
        String line = br.readLine();
        String[] strings = line.split(" ");
        int cogNo = Integer.valueOf(strings[0]);
        boolean isCCW = Integer.valueOf(strings[1]) == -1 ? true : false;
        indices[i] = cogNo;
        isCCWs[i] = isCCW;
      }

      int result = Solver14891.solve(cogs, indices, isCCWs);
      bw.write(result + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver14891 {
  public static final int COGS = 8;

  public static int solve(int[] cogs, int[] indices, boolean[] isCCWs) {
    if (indices.length != isCCWs.length) {
      throw new RuntimeException("lengths are different!");
    }
    var solver = new Solver14891(cogs);
    for (int i = 0; i < indices.length; ++i) {
      solver.rotate(indices[i] - 1, isCCWs[i]);
    }

    /**
     * 점수의 합 구하기
     * 1번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 1점
     * 2번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 2점
     * 3번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 4점
     * 4번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 8점
     */
    int[] result = solver.get();
    int sum = 0;
    for (int i = 0; i < result.length; ++i) {
      if (getBit(result[i], COGS - 1) == 1) {
        sum += (1 << i);
      }
    }
    return sum;
  }

  /**
   * big endianness when referencing bit
   */
  public static int getBit(int origin, int bit) {
    int mask = 1;
    mask <<= bit;
    int ret = origin & mask;
    ret >>= bit;
    return ret;
  }

  public static int setBit(int origin, int bit, int target) {
    int check = getBit(origin, bit);
    if (check == target)
      return origin;
    if (check == 1)
      return origin - (1 << bit);
    return origin + (1 << bit);
  }

  /**
   * bit rotating with 8bit boundary
   * origin: 0b1010_1111
   * isCCW: true
   * return: 0b0101_1111
   * 
   * @param idx
   * @param isCCW
   */
  public static int doRotate(int origin, int bits, boolean isCCW) {
    int lastOne = getBit(origin, bits - 1);
    int firstOne = getBit(origin, 0);
    if (isCCW) {
      int tmp = setBit(origin, bits - 1, 0);
      tmp <<= 1;
      tmp += lastOne;
      origin = tmp;
    } else {
      origin >>= 1;
      origin = setBit(origin, bits - 1, firstOne);
    }
    return origin;
  }

  private int[] cogs;

  public Solver14891(int[] cogs) {
    this.cogs = cogs;
  }

  /**
   * can recurse through cogs
   */
  public void rotate(int idx, boolean isCCW) {
    boolean[] visited = new boolean[cogs.length];
    rotate(idx, isCCW, visited);
  }

  public int get(int idx) {
    return cogs[idx];
  }

  public int[] get() {
    return cogs;
  }

  /**
   * used by recursion
   */
  private void rotate(int idx, boolean isCCW, boolean[] visited) {
    visited[idx] = true;
    var clock3 = getBit(cogs[idx], 5);
    var clock9 = getBit(cogs[idx], 1);
    // my 3o'clock and next 9o'clock
    if (idx + 1 < cogs.length &&
        visited[idx + 1] == false &&
        clock3 != getBit(cogs[idx + 1], 1)) {
      rotate(idx + 1, !isCCW, visited);
    }
    // my 9o'clock and next 3o'clock
    if (idx - 1 >= 0 &&
        visited[idx - 1] == false &&
        clock9 != getBit(cogs[idx - 1], 5)) {
      rotate(idx - 1, !isCCW, visited);
    }
    cogs[idx] = doRotate(get(idx), COGS, isCCW);
  }
}
