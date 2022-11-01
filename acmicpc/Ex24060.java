package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Ex24060 {
  public static void main(String[] args) throws IOException {
    var br = new BufferedReader(new InputStreamReader(System.in));
    var bw = new BufferedWriter(new OutputStreamWriter(System.out));
    String line = br.readLine();
    String[] strings = line.split(" ");
    var n = Integer.valueOf(strings[0]);
    var k = Integer.valueOf(strings[1]);
    line = br.readLine();
    strings = line.split(" ");
    Long[] unordered = Stream.of(strings)
        .mapToLong(Long::valueOf)
        .boxed()
        .toArray(Long[]::new);

    var solver = new Solver24060();
    var answer = solver.solve(unordered, k);
    // bw.write(answer + "\n");
    System.out.println(answer);
  }
}

class Solver24060 {
  static final boolean DEBUG = true;

  Long result = -1L;

  public long solve(Long[] unordered, int k) {
    MergeCountObserver<Long> observer = new MergeCountObserver<>(k);
    MergeSort<Long> sorter = new MergeSort<>(
        unordered,
        (a, b) -> (int) (a - b),
        observer);
    try {
      sorter.sort(0, unordered.length);
    } catch (MergeCountException e) {
      var current = observer.getCurrent();
      return current;
    }
    return result;
  }
}

class MergeSort<T extends Comparable<?>> {
  T[] a;
  T latestElement;
  Comparator<T> comparator;
  MergeCountObserver<T> mergeCountObserver;

  public MergeSort(T[] a, Comparator<T> comparator, MergeCountObserver<T> observer) {
    this.a = a;
    this.comparator = comparator;
    this.mergeCountObserver = observer;
  }

  public final T[] getArray() {
    return a;
  }

  public int getMergeCount() {
    return mergeCountObserver.getCount();
  }

  /**
   * sort from index p to r
   * 
   * @param p inclusive left-most index
   * @param r exclusive right-most index
   * @return this
   */
  public MergeSort<T> sort(int p, int r) {
    if (r - p > 1) {
      int q = (int) Math.ceil((double) (p + r) / 2.0);
      sort(p, q);
      sort(q, r);
      merge(p, q, r);
    }
    return this;
  }

  private MergeSort<T> merge(int p, int q, int r) {
    int i = p;
    int j = q;
    int t = 0;

    // Map<Long, T> tmp = new HashMap<>();
    final var SIZE = r - p;
    List<T> tmp = new ArrayList<>(SIZE);
    for (int idx = 0; idx < SIZE; ++idx) {
      tmp.add(a[0]);
    }
    while (i < q && j < r) {
      if (comparator.compare(a[i], a[j]) < 0) {
        tmp.set(t, a[i]);
        t++;
        i++;
      } else {
        tmp.set(t, a[j]);
        t++;
        j++;
      }
    }
    // remainders
    while (i < q) {
      tmp.set(t, a[i]);
      t++;
      i++;
    }
    while (j < r) {
      tmp.set(t, a[j]);
      t++;
      j++;
    }
    doMerge(tmp, p, r);
    return this;
  }

  private void doMerge(List<T> newArr, int p, int r) {
    int t = 0;
    while (p < r) {
      final var value = newArr.get(t);
      a[p] = value;
      mergeCountObserver.doCount(value);
      p++;
      t++;
    }
  }
}

class MergeCountException extends RuntimeException {
}

/**
 * 원하는 merge 횟수에 도달하게 되면 exception을 발생시킴.
 */
class MergeCountObserver<T> {
  int mergeCount = 0;
  int k;
  T current;

  public MergeCountObserver(int k) {
    this.k = k;
  }

  public void doCount(T current) throws MergeCountException {
    this.current = current;
    mergeCount++;
    if (mergeCount == k) {
      throw new MergeCountException();
    }
  }

  public int getCount() {
    return mergeCount;
  }

  public T getCurrent() {
    return this.current;
  }
}