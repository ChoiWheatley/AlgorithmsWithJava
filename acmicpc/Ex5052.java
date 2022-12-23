package acmicpc;

import java.util.Arrays;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex5052 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine(); // "1 2 3 4 5"
      int t = Integer.valueOf(line);
      while (t-- > 0) {
        line = br.readLine();
        int n = Integer.valueOf(line);
        String[] numbers = new String[n];
        for (int i = 0; i < n; ++i) {
          numbers[i] = br.readLine();
        }
        boolean submit = Solver5052.solve(numbers);
        if (submit)
          bw.write("YES\n");
        else
          bw.write("NO\n");
        bw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

class Solver5052 {
  /**
   * 1. 전화번호를 오름차순으로 정렬
   * 2. 전화번호 각 자릿수를 읽어가며 트리를 순회
   * 3. 매 순회 시 선점 여부를 확인하고, 선점이 된 노드에 방문했다면 이 전화번호부는 일관성이 없는 것.
   * 4. 전화번호를 모두 읽었다면 해당 노드를 선점한다.
   * 5. 전화번호부의 다음 전화번호를 읽는다. goto 2
   */
  public static boolean solve(String[] numbers) {
    Arrays.sort(numbers);
    Node head = new Node();
    for (var number : numbers) {
      char[] charArr = number.toCharArray();
      Node cursor = head;
      for (char c : charArr) {
        cursor = cursor.get(c - '0');
        if (cursor.isPreempted())
          return false;
      }
      cursor.setPreemtion(true);
    }
    return true;
  }
}

final class Node {
  private Node[] nodes = new Node[10];
  private boolean preempted = false;

  public Node get(int idx) {
    if (nodes[idx] == null) {
      nodes[idx] = new Node();
    }
    return nodes[idx];
  }

  public boolean isPreempted() {
    return preempted;
  }

  public boolean setPreemtion(boolean to) {
    if (preempted)
      return false;
    preempted = to;
    return true;
  }
}