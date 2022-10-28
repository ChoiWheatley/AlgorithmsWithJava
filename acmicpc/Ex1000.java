package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex1000 {
  public static void main(String[] args) {
    // 빠른 입출력을 위한 BufferedReader
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      String[] numbers = line.split(" ");
      int a = Integer.valueOf(numbers[0]);
      int b = Integer.valueOf(numbers[1]);

      bw.write("" + (a + b) + "\n");
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
  }
}
