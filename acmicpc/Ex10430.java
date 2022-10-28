package acmicpc;

import static java.lang.Math.floorMod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

@FunctionalInterface
interface Fun {
  int calc(int a, int b, int c);
}

public class Ex10430 {
  public static final Fun[] functions = {
      (a, b, c) -> floorMod((a + b), c),
      (a, b, c) -> floorMod(floorMod(a, c) + floorMod(b, c), c),
      (a, b, c) -> floorMod((a * b), c),
      (a, b, c) -> floorMod((floorMod(a, c) * floorMod(b, c)), c)
  };

  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      String[] numbeStrings = line.split(" ");
      int[] ints = Stream.of(numbeStrings)
          .mapToInt(Integer::valueOf)
          .toArray();
      for (var f : functions) {
        bw.write(f.calc(ints[0], ints[1], ints[2]) + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
