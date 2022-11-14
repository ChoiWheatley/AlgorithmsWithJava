package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.stream.Stream;

public class Ex25943 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      line = br.readLine();
      int[] arr = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
      int answer = Solver25943.solve(arr);
      bw.write(answer + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

class Solver25943 {
  public static int solve(int[] arr) {
    // bias - left + right
    int bias = 0;
    bias -= arr[0];
    bias += arr[1];
    for (int idx = 2; idx < arr.length; ++idx) {
      if (bias < 0) {
        bias += arr[idx];
      } else {
        bias -= arr[idx];
      }
    }
    bias = Math.abs(bias);

    // bias를 0으로 만드는 무게추의 최소 개수 구하기
    return findMinCount(bias);
  }

  public static final int[] weights = new int[] { 1, 2, 5, 10, 20, 50, 100 };

  public static int findMinCount(int bias) {
    int idx = weights.length - 1;
    int cnt = 0;
    while (bias != 0) {
      if (bias >= weights[idx]) {
        bias -= weights[idx];
        cnt++;
      } else {
        idx--;
      }
    }
    return cnt;
  }
}
