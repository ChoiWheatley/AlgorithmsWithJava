package acmicpc;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.Test;

public class Ex24060 {
  public static void main(String[] args) throws IOException {
    var br = new BufferedReader(new InputStreamReader(System.in));
    var bw = new BufferedWriter(new OutputStreamWriter(System.out));
    String line = br.readLine();
    String[] strings = line.split(" ");
    int n = Integer.valueOf(strings[0]);
    int k = Integer.valueOf(strings[1]);
    line = br.readLine();
    strings = line.split(" ");
    Integer[] unordered = Stream.of(strings)
        .mapToInt(Integer::valueOf)
        .boxed()
        .toArray(Integer[]::new);

    var solver = new Solver();
    var answer = solver.solve(unordered, k);
    // bw.write(answer + "\n");
    System.out.println(answer);
  }

  @Test
  public void testcase1() {
    /**
     * 5 7
     * 4 5 1 3 2
     */
    var unordered = new Integer[] { 4, 5, 1, 3, 2 };
    var k = 7;
    var solver = new Solver();
    assertEquals(3, solver.solve(unordered, k));
  }

  @Test
  public void loongRandomStreamSpeedTest() throws IOException {
    Integer[] unordered = (new Random()).ints(500_000)
        .boxed()
        .toArray(Integer[]::new);
    System.out.println(unordered[unordered.length - 1]);
  }

  @Test
  public void timeoutTest1() {
    /**
     * 500_000개의 원소를 정렬할 때 과연 얼마나 오래 걸리는지
     */
    Integer[] unordered = (new Random()).ints(50_000)
        .boxed()
        .toArray(Integer[]::new);
    var k = (int) Math.pow(10, 8);
    var solver = new Solver();
    assertEquals(-1, solver.solve(unordered, k));
  }
}

class Solver {
  static final boolean DEBUG = false;

  int result = -1;

  public int solve(Integer[] unordered, int k) {
    MergeCountObserver<Integer> observer = new MergeCountObserver<>(k);
    MergeSort<Integer> sorter = new MergeSort<>(
        unordered,
        (a, b) -> a - b,
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
      var q = (int) (Math.ceil((double) (p + r) / (double) 2));
      sort(p, q);
      sort(q + 1, r);
      merge(p, q, r);
    }
    return this;
  }

  private MergeSort<T> merge(int p, int q, int r) {
    var i = p;
    var j = q;
    var t = 0;

    T[] tmp = a.clone();
    while (i < q && j < r) {
      if (comparator.compare(a[i], a[j]) < 0) {
        tmp[t] = a[i];
        t++;
        i++;
      } else {
        tmp[t] = a[j];
        t++;
        j++;
      }
    }
    // remainders
    while (i < q) {
      tmp[t] = a[i];
      t++;
      i++;
    }
    while (j < r) {
      tmp[t] = a[j];
      t++;
      j++;
    }
    doMerge(tmp, p, r);
    return this;
  }

  private void doMerge(T[] newArr, int p, int r) {
    int t = 0;
    while (p < r) {
      /**
       * 여기에서 count체크 및 리턴을 수행함.
       * 원래는 Exception을 사용하도록 지시했는데, 시간초과가 발생했다.
       */
      if (mergeCountObserver.doesCountExceedsK()) {
        return;
      }
      a[p] = newArr[t];
      mergeCountObserver.setCurrent(newArr[t]);
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
