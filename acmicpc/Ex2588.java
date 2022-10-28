package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ex2588 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      int no1 = Integer.valueOf(br.readLine());
      int no2 = Integer.valueOf(br.readLine());
      int no2Dup = no2;
      // no2로부터 각 자리수를 뽑아낸다. little endianness
      int[] digits = new int[3];
      for (int idx = 0; idx < digits.length; ++idx) {
        digits[idx] = Math.floorMod(no2Dup, 10);
        no2Dup /= 10;
      }

      for (int digit : digits) {
        var result = digit * no1;
        bw.write(result + "\n");
      }
      bw.write(no1 * no2 + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
