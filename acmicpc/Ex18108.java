package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex18108 {
  public static final int BUDDHIST_CHRISTIAN = 543;

  public static int buddhistToChristian(int b) {
    return b - BUDDHIST_CHRISTIAN;
  }

  public static int christianToBuddhist(int c) {
    return c + BUDDHIST_CHRISTIAN;
  }

  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      int input = Integer.valueOf(line);

      bw.write("" + buddhistToChristian(input) + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
