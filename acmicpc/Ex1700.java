package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Ex1700 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    String[] line = reader.readLine().split(" ");
    int n = Integer.valueOf(line[0]);
    int k = Integer.valueOf(line[1]);
    line = reader.readLine().split(" ");
    int[] schedule = Stream.of(line)
        .mapToInt(Integer::valueOf)
        .toArray();
    System.out.println((new Solver1700(schedule, n)).solve(Solver1700.Option.SMART));
  }
}

class Solver1700 {
  public enum Option {
    NAIVE,
    SMART
  }

  final int[] SCHEDULE;
  final int HOLE_COUNT;

  public Solver1700(int[] schedule, int holeCount) {
    SCHEDULE = schedule;
    HOLE_COUNT = holeCount;
  }

  public int solve() {
    return solve(Option.NAIVE);
  }

  public int solve(Option opt) {
    int[] multiTab = new int[HOLE_COUNT];
    switch (opt) {
      case NAIVE:
        return recurNaively(multiTab, 0, 0);
      case SMART:
        return recurSmartly(multiTab, 0, 0);
      default:
        return solve(Option.NAIVE);
    }
  }

  /**
   * 최소 swapCount를 리턴하는 재귀함수
   */
  private int recurNaively(int[] multiTab, int cursor, int swapCount) {
    int min = Integer.MAX_VALUE;
    // 아직 multitab이 가득 차지 않았다면 가득 채운다.
    if (cursor == 0) {
      Set<Integer> set = new HashSet<>(multiTab.length);
      while (set.size() < HOLE_COUNT && cursor < SCHEDULE.length) {
        set.add(SCHEDULE[cursor]);
        cursor++;
      }
      multiTab = set.stream().mapToInt(Integer::valueOf).toArray();
    }
    // end condition
    if (SCHEDULE.length <= cursor) {
      return swapCount;
    }
    // 하나씩 넣어보고 다시 뺀다. 각각의 경우에 대한 최솟값만을 리턴한다.
    for (int idx = 0; idx < HOLE_COUNT; ++idx) {
      var old = multiTab[idx];
      var next = SCHEDULE[cursor];
      if (old != next) {
        multiTab[idx] = next;
        min = Integer.min(min,
            recurNaively(multiTab, cursor + 1, swapCount + 1));
        multiTab[idx] = old;
      } else {
        min = Integer.min(min,
            recurNaively(multiTab, cursor + 1, swapCount));
      }
    }
    return min;
  }

  /**
   * 최소 swapCount를 리턴하는 재귀함수
   */
  private int recurSmartly(int[] multiTab, int cursor, int swapCount) {
    // 아직 multitab이 가득 차지 않았다면 가득 채운다.
    if (cursor == 0) {
      Set<Integer> set = new HashSet<>(multiTab.length);
      while (set.size() < HOLE_COUNT && cursor < SCHEDULE.length) {
        set.add(SCHEDULE[cursor]);
        cursor++;
      }
      multiTab = set.stream().mapToInt(Integer::valueOf).toArray();
    }
    // end condition
    if (SCHEDULE.length <= cursor) {
      return swapCount;
    }
    // 가지치기: 이번에 사용할 전자기기가 이미 꽂혀있는 경우 굳이 다른 플러그를
    // 뽑을 필요가 없다.
    for (int idx = 0; idx < HOLE_COUNT; ++idx) {
      var old = multiTab[idx];
      var next = SCHEDULE[cursor];
      if (old == next) {
        return recurSmartly(multiTab, cursor + 1, swapCount);
      }
    }
    // 재사용 안할 플러그를 뽑는다.
    int[] uselessPoint = heuristic(multiTab, cursor);
    int next = maxIndexAmong(uselessPoint);
    multiTab[next] = SCHEDULE[cursor];

    return recurSmartly(multiTab, cursor + 1, swapCount + 1);
  }

  private int[] initializeTab(int[] multiTab) {
    Set<Integer> set = new HashSet<>(multiTab.length);
    for (int each : SCHEDULE) {
      set.add(each);
    }
    return set.stream().mapToInt(Integer::valueOf).toArray();
  }

  /**
   * 꽂혀있는 제품들이 재사용 되기까지 앞으로 얼마나 남았는가?
   */
  private int[] heuristic(int[] multiTab, int cursor) {
    int[] ret = new int[HOLE_COUNT];
    for (int idx = 0; idx < HOLE_COUNT; ++idx) {
      ret[idx] = 0;
      for (int j = cursor; j < SCHEDULE.length; ++j) {
        if (multiTab[idx] == SCHEDULE[j])
          break;
        ret[idx]++;
      }
    }
    return ret;
  }

  public static int maxIndexAmong(int[] ls) {
    int ret = -1;
    int max = 0;
    for (int idx = 0; idx < ls.length; ++idx) {
      var each = ls[idx];
      if (max < each) {
        max = each;
        ret = idx;
      }
    }
    return ret;
  }
}