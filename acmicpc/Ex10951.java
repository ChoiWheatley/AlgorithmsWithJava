package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Ex10951 {
  public static void main(String[] args) throws IOException {
    var bufReader = new BufferedReader(new InputStreamReader(System.in));
    var bufWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    var scanner = new Scanner(System.in);
    String line;
    // while ((line = bufReader.readLine()) != null) {
    // var strarr = line.split(" ");
    // long a = Long.valueOf(strarr[0]);
    // long b = Long.valueOf(strarr[1]);
    // bufWriter.write((a + b) + "\n");
    // }
    while (scanner.hasNext()) {
      long a = scanner.nextLong();
      long b = scanner.nextLong();
      bufWriter.write((a + b) + "\n");
    }
    bufWriter.flush();
    bufWriter.close();
  }
}
