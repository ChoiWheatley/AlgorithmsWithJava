package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex10926 {
  static final String EXCLAMATION_MARK = "??!";

  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      bw.write(line + EXCLAMATION_MARK + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
