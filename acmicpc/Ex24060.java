package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;

public class Ex24060 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class MergeSort<T extends Number> {
  T[] a;
  T latestElement;
  Comparator<T> comparator;

  public MergeSort(T[] a, Comparator<T> comparator) {
    this.a = a;
    this.comparator = comparator;
  }

  public T[] getArray() {
    return a;
  }

  public boolean hasNext() {

  }

  public T nextMerge() {
    if (!hasNext()) {
      return latestElement;
    }
  }
}