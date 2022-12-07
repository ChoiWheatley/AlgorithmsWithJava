package acmicpc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex1260 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      int[] nmv = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
      int n = nmv[0];
      int m = nmv[1];
      int v = nmv[2];
      int[][] input = new int[m][2];

      for (int i = 0; i < m; ++i) {
        line = br.readLine();
        String[] edge = line.split(" ");
        input[i][0] = Integer.valueOf(edge[0]);
        input[i][1] = Integer.valueOf(edge[1]);
      }

      List<List<Integer>> graph = Solver1260.makeGraph(input, n);

      var bfs = Solver1260.bfs(graph, n, v);
      var dfs = Solver1260.dfs(graph, new boolean[n + 1], v);

      for (var i : dfs)
        bw.write(i + " ");
      bw.write("\n");
      for (var i : bfs)
        bw.write(i + " ");
      bw.write("\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver1260 {
  /** adjacency list */
  public static List<List<Integer>> makeGraph(int[][] input, int n) {
    List<List<Integer>> graph = new ArrayList<>(n + 1);
    // init
    for (int i = 0; i <= n; ++i) {
      graph.add(new ArrayList<>());
    }

    // fill
    for (int i = 0; i < input.length; ++i) {
      int a = input[i][0];
      int b = input[i][1];
      graph.get(a).add(b);
      graph.get(b).add(a);
    }

    for (int i = 0; i <= n; ++i) {
      // erase duplicate
      graph.set(i, graph.get(i)
          .stream().collect(Collectors.toSet())
          .stream().collect(Collectors.toList()));
      // sort
      graph.get(i).sort((a, b) -> a.compareTo(b));
    }
    return graph;
  }

  public static List<Integer> dfs(List<List<Integer>> graph, boolean[] visited, int n) {
    if (visited[n])
      return List.of();
    visited[n] = true;
    List<Integer> sum = new ArrayList<>();
    sum.add(n);
    graph.get(n).forEach(next -> sum.addAll(dfs(graph, visited, next)));

    return sum;
  }

  public static List<Integer> bfs(List<List<Integer>> graph, int n, int v) {
    List<Integer> ret = new ArrayList<>(n + 1);
    boolean[] visited = new boolean[n + 1];
    Queue<Integer> queue = new LinkedList<>();
    // first case
    visited[v] = true;
    graph.get(v).forEach(queue::add);
    graph.get(v).forEach(e -> visited[e] = true);
    ret.add(v);

    while (!queue.isEmpty()) {
      int cursor = queue.poll();
      visited[cursor] = true;
      ret.add(cursor);
      graph.get(cursor).stream().filter(e -> !visited[e]).forEach(queue::add);
      graph.get(cursor).stream().filter(e -> !visited[e]).forEach(e -> visited[e] = true);
    }

    return ret;
  }
}