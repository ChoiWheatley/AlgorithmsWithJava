package programmers;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42587
 * 프린트
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

import org.junit.Test;

public class Ex42587 {
  class Value {
    int value;

    public Value(int value) {
      this.value = value;
    }
  }

  public int solution(int[] priorities, int location) {
    Queue<Value> q = new LinkedList<>();
    Value watching = null;
    for (int idx = 0; idx < priorities.length; ++idx) {
      var each = new Value(priorities[idx]);
      if (idx == location) {
        watching = each;
      }
      q.add(each);
    }

    int cnt = 1;
    while (!q.isEmpty()) {
      var sample = q.remove();
      if (q.stream().anyMatch(e -> e.value > sample.value)) {
        q.add(sample);
        continue;
      }

      // same reference?
      if (sample == watching) {
        break;
      }
      cnt++;
    }

    return cnt;
  }

  /**
   * location을 직접 변경해 가면서 수행하는 코드
   */
  public int solution2(int[] priorities, int location) {
    Queue<Integer> q = new LinkedList<>();
    for (int i : priorities) {
      q.add(i);
    }

    // priorities 정렬을 수행하는 이유: 결국 우선순위 순서대로 출력할 예정이기 때문.
    Arrays.sort(priorities);

    int size = priorities.length - 1;
    int printCnt = 0;
    while (!q.isEmpty()) {
      // 1. 하나를 꺼낸다.
      Integer dequeue = q.poll();
      Integer soonPrint = priorities[size - printCnt];
      // 2. 프린트 할 놈인지, 뒤에다 다시 집어넣을 놈인지 구별한다.
      if (dequeue == soonPrint) {
        // do print
        printCnt++;
        // 만약 내가 방금 프린트 한 녀석이 내가 찾던 녀석이라면?
        location--;
        if (location < 0) {
          break;
        }
      } else {
        // enqueue
        q.add(dequeue);
        // 내가 찾던 녀석이 맨 뒤로 옮겨가게 된다면 값을 바꿔줘야지.
        location--;
        if (location < 0) {
          location = q.size() - 1;
        }
      }
    }

    return printCnt;
  }

  public int solution3(int[] priorities, int location) {
    int answer = 0;
    int l = location;

    Queue<Integer> que = new LinkedList<Integer>();
    for (int i : priorities) {
      que.add(i);
    }

    Arrays.sort(priorities);
    int size = priorities.length - 1;

    while (!que.isEmpty()) {
      Integer i = que.poll();
      if (i == priorities[size - answer]) {
        answer++;
        l--;
        if (l < 0)
          break;
      } else {
        que.add(i);
        l--;
        if (l < 0)
          l = que.size() - 1;
      }
    }

    return answer;
  }

  int[] priorities;
  int[] answers;

  @Test
  public void test1() {
    priorities = new int[] { 2, 1, 3, 2 };
    answers = new int[] { 3, 4, 1, 2 };
    for (var idx = 0; idx < priorities.length; ++idx) {
      assertEquals(answers[idx], solution3(priorities, idx));
    }
  }

  @Test
  public void test2() {
    priorities = new int[] { 1, 1, 9, 1, 1, 1 };
    answers = new int[] { 5, 6, 1, 2, 3, 4 };
    for (var idx = 0; idx < priorities.length; ++idx) {
      assertEquals(answers[idx], solution3(priorities, idx));
    }
  }

  @Test
  public void priorityqueue() {
    var q = new PriorityQueue<Integer>(
        Arrays.asList(1, 4, 2, 9, 3, 16, 4, 25, 5, 36, 6, 49, 7, 64, 8, 81, 9, 100, 10));
    while (!q.isEmpty()) {
      System.out.println(q.remove());
    }
  }

  @Test
  public void valuestream() {
    Value[] values = {
        new Value(1),
        new Value(2),
        new Value(3)
    };
    Stream.of(values).mapToInt(e -> e.value).forEach(System.out::println);
  }
}
