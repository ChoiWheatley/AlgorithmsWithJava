package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

public class Ex3003 {
  public static final int[] PIECES = { 1, 1, 2, 2, 2, 8 };

  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      String[] numbers = line.split(" ");
      int[] ints = Stream.of(numbers)
          .mapToInt(Integer::valueOf)
          .toArray();

      for (int idx = 0; idx < PIECES.length; ++idx) {
        ints[idx] = PIECES[idx] - ints[idx];
      }

      for (var i : ints) {
        bw.write("" + i + " ");
      }
      bw.write("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
