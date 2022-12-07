package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Test1260 {
  @Test
  public void graphTest() {
    int[][] ls = {
        { 2, 5 },
        { 2, 7 },
        { 2, 9 },
        { 1, 2 },
        { 1, 4 },
        { 1, 6 },
        { 1, 8 },
        { 3, 1 },
        { 3, 2 },
        { 3, 4 },
        { 3, 5 },
    };
    List<List<Integer>> answer = List.of(
        List.of(),
        List.of(2, 3, 4, 6, 8),
        List.of(1, 3, 5, 7, 9),
        List.of(1, 2, 4, 5),
        List.of(1, 3),
        List.of(2, 3),
        List.of(1),
        List.of(2),
        List.of(1),
        List.of(2));
    var submit = Solver1260.makeGraph(ls, answer.size());
    for (int i = 0; i <= 9; ++i) {
      assertEquals(answer.get(i), submit.get(i));
    }
  }

  @Test
  public void sample1() {
    int[][] rawGraph = {
        { 1, 2 },
        { 1, 3 },
        { 1, 4 },
        { 2, 4 },
        { 3, 4 },
    };
    int n = 4;
    int v = 1;
    var answerDfs = List.of(1, 2, 4, 3);
    var answerBfs = List.of(1, 2, 3, 4);

    var graph = Solver1260.makeGraph(rawGraph, n);
    var submitDfs = Solver1260.dfs(graph, new boolean[n + 1], v);
    var submitBfs = Solver1260.bfs(graph, n, v);

    assertEquals(answerDfs, submitDfs);
    assertEquals(answerBfs, submitBfs);
  }

  @Test
  public void sample2() {
    int[][] rawGraph = {
        { 5, 2 },
        { 2, 1 },
        { 1, 3 },
        { 3, 4 },
        { 4, 5 },
    };
    List<List<Integer>> answerGraph = List.of(
        List.of(),
        List.of(2, 3),
        List.of(1, 5),
        List.of(1, 4),
        List.of(3, 5),
        List.of(2, 4));
    int n = 5;
    int v = 3;
    var answerDfs = List.of(3, 1, 2, 5, 4);
    var answerBfs = List.of(3, 1, 4, 2, 5);

    var graph = Solver1260.makeGraph(rawGraph, n);
    assertEquals(answerGraph, graph);
    var submitDfs = Solver1260.dfs(graph, new boolean[n + 1], v);
    var submitBfs = Solver1260.bfs(graph, n, v);

    assertEquals(answerDfs, submitDfs);
    assertEquals(answerBfs, submitBfs);
  }

  @Test
  public void edge1() {
    var rawGraph = new int[][] {
        { 1, 3 },
        { 3, 1 }, // 중복
        { 2, 3 }
    };
    var answerGraph = List.of(
        List.of(),
        List.of(3),
        List.of(3),
        List.of(1, 2));
    var graph = Solver1260.makeGraph(rawGraph, 3);
    assertEquals(answerGraph, graph);
    int n = 3;
    int v = 1;

    var answerDfs = List.of(1, 3, 2);
    var answerBfs = answerDfs;

    var submitDfs = Solver1260.dfs(graph, new boolean[n + 1], v);
    var submitBfs = Solver1260.bfs(graph, n, v);

    assertEquals(answerDfs, submitDfs);
    assertEquals(answerBfs, submitBfs);
  }
}