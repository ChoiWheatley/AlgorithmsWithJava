package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex25945 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      line = br.readLine();
      int[] containers = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
      int result = Solver25945.solve(containers);
      bw.write(result + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver25945 {
  public static int solve(int[] containers) {
    int m = IntStream.of(containers).sum();
    int n = containers.length;
    int average = m / n;
    int over = 0;
    int exceed = 0;
    int less = 0;
    int count = 0;
    for (var each : containers) {
      if (each < average) {
        less += average - each;
      }
      if (each > average) {
        over += each - average;
      }
      if (each > average + 1) {
        exceed += each - (average + 1);
      }
    }
    count = less;
    if (exceed > less) {
      count = exceed;
    }
    return count;
  }
}