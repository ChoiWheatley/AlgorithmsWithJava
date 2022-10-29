package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex10872 {
  public static int factorial(int a) {
    if (a == 0) {
      return 1;
    }
    return a * factorial(a - 1);
  }

  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      int number = Integer.valueOf(br.readLine());
      bw.write(factorial(number) + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
