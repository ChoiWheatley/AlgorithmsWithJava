package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Ex10953 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    var n = Integer.valueOf(br.readLine());
    for (int i = 0; i < n; ++i) {
      var line = br.readLine();
      var sum = Stream.of(line.split(",")).mapToInt(Integer::valueOf).sum();
      System.out.println(sum);
    }
  }
}