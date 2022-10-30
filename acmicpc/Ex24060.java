package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Map;

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

    var solver = new Solver();
    var answer = solver.solve(unordered, k);
    bw.write(answer + "\n");
  }
}

class Solver {
  static final boolean DEBUG = false;

  Long result = -1L;

  public long solve(Long[] unordered, int k) {
    MergeCountObserver<Long> observer = new MergeCountObserver<>(k);
    MergeSort<Long> sorter = new MergeSort<>(
        unordered,
        (a, b) -> (int) (a - b),
        observer);
    sorter.sort(0, unordered.length);
    var log = observer.getCurrent();
    if (DEBUG) {
      System.out.println("Solver::solve::log=");
      log.forEach(System.out::println);
      System.out.println("end of DEBUG");
    }
    if (!log.isEmpty()) {
      result = log.get(log.size() - 1);
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
    if (p < r) {
      var q = (p + r) / 2;
      sort(p, q);
      sort(q + 1, r);
      merge(p, q, r);
    }
    return this;
  }

  private MergeSort<T> merge(int p, int q, int r) {
    int i = p;
    int j = q;
    long t = 0;

    Map<Long, T> tmp = new HashMap<>();
    while (i < q && j < r) {
      if (comparator.compare(a[i], a[j]) < 0) {
        // tmp.set(t, a[i]);
        tmp.put(t, a[i]);
        t++;
        i++;
      } else {
        // tmp.set(t, a[j]);
        tmp.put(t, a[j]);
        t++;
        j++;
      }
    }
    // remainders
    while (i < q) {
      tmp.put(t, a[i]);
      t++;
      i++;
    }
    while (j < r) {
      tmp.put(t, a[j]);
      t++;
      j++;
    }
    doMerge(tmp, p, r);
    return this;
  }

  private void doMerge(Map<Long, T> newArr, int p, int r) {
    long t = 0;
    while (p < r) {
      /**
       * 여기에서 count체크 및 리턴을 수행함.
       * 원래는 Exception을 사용하도록 지시했는데, 시간초과가 발생했다.
       */
      if (mergeCountObserver.doesCountExceedsK()) {
        return;
      }
      a[p] = newArr.get(t);
      mergeCountObserver.setCurrent(newArr.get(t));
      mergeCountObserver.doCount();
      p++;
      t++;
    }
  }
}

/**
 * 원하는 merge 횟수에 도달하게 되면 exception을 발생시킴.
 */
class MergeCountObserver<T> {
  int mergeCount = 1;
  int k;
  List<T> current;

  public MergeCountObserver(int k) {
    this.k = k;
    this.current = new LinkedList<>();
  }

  public void doCount() {
    mergeCount++;
  }

  public int getCount() {
    return mergeCount;
  }

  public boolean doesCountExceedsK() {
    return this.k <= getCount();
  }

  public void setCurrent(T current) {
    this.current.add(current);
  }

  public List<T> getCurrent() {
    return current;
  }
}