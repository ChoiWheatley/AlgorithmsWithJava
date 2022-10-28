package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex1008 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));) {
      String line = br.readLine();
      String[] numbers = line.split(" ");
      double a = Double.valueOf(numbers[0]);
      double b = Double.valueOf(numbers[1]);

      bw.write("" + (a / b) + "\n");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }
}
