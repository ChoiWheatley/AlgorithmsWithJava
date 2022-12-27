package codeforces;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex1772 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    var writer = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.valueOf(reader.readLine());
    while (n-- > 0) {
      String line = reader.readLine();
      int a = line.charAt(0) - '0';
      int b = line.charAt(2) - '0';
      writer.write((a + b) + "\n");
    }

    reader.close();
    writer.flush();
    writer.close();
  }
}
