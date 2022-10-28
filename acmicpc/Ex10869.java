package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex10869 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      // + - * /(몫) %(나머지)
      String line = br.readLine();
      String[] numbers = line.split(" ");
      int a = Integer.valueOf(numbers[0]);
      int b = Integer.valueOf(numbers[1]);

      bw.write(""
          + (a + b) + "\n"
          + (a - b) + "\n"
          + (a * b) + "\n"
          + (a / b) + "\n"
          + (Math.floorMod(a, b)) + "\n");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }
}