package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ex3649 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String firstLine = br.readLine();
    do {
      int holeSize = Integer.valueOf(firstLine);
      int n = Integer.valueOf(br.readLine().trim());
      List<Long> ls = new ArrayList<>(n);
      for (var i = 0; i < n; ++i) {
        ls.add(Long.valueOf(br.readLine().trim()));
      }
      System.out.println((new Solver3649()).solve(holeSize, ls));

      firstLine = br.readLine();
    } while (firstLine != null);
  }
}

class Answer3649 implements Comparable<Answer3649> {
  public boolean possible;
  public Long l1;
  public Long l2;

  public Answer3649(boolean possible, Long l1, Long l2) {
    this.possible = possible;
    this.l1 = l1;
    this.l2 = l2;
  }

  @Override
  public String toString() {
    if (possible) {
      if (l2 < l1) {
        var tmp = l2;
        l2 = l1;
        l1 = tmp;
      }
      return "yes" + " " + l1 + " " + l2;
    }
    return "danger";
  }

  @Override
  public boolean equals(Object answer) {
    if (!(answer instanceof Answer3649)) {
      return false;
    }
    var a = (Answer3649) answer;
    if (!this.possible && !a.possible) {
      return true;
    }
    return possible == a.possible &&
        l1.equals(a.l1) &&
        l2.equals(a.l2);
  }

  /**
   * | l1 - l2 |가 가장 큰 것을 출력한다.
   */
  @Override
  public int compareTo(Answer3649 o) {
    if (!this.possible && !o.possible) {
      return 0;
    }
    if (o.possible && this.possible) {
      long mine = Math.abs(l1 - l2);
      long yours = Math.abs(o.l1 - o.l2);
      return (int) (mine - yours);
    }
    return -1;
  }

  public static Answer3649 getFalse() {
    return new Answer3649(false, null, null);
  }
}

class Solver3649 {
  public static final long CENTI_PER_NANO = 10000000L;

  public static long centimeterToNanometer(long centi) {
    return Math.multiplyExact(centi, CENTI_PER_NANO);
  }

  public Answer3649 solve(long centiHoleSize, List<Long> ls) {
    Answer3649 max = null;
    var nanoHoleSize = centimeterToNanometer(centiHoleSize);
    ls.sort((a, b) -> (int) (a - b));

    for (int idx = 0; idx < ls.size(); ++idx) {
      var nanoSize1 = ls.get(idx);
      var nanoSize2 = nanoHoleSize - nanoSize1;
      /**
       * find size2 in remainder of ls
       */
      var searchParty = new SearchParty<>(ls);
      var searchResult = searchParty.find(nanoSize2);
      if (searchResult != idx && searchResult != -1) {
        var newAnswer = new Answer3649(true, nanoSize1, nanoSize2);
        if (max == null || max.compareTo(newAnswer) < 0) {
          max = newAnswer;
        }
      }
    }
    if (max != null) {
      return max;
    }
    return Answer3649.getFalse();
  }

}

class SearchParty<T extends Comparable<T>> {
  List<T> ls;

  public SearchParty(List<T> ls) {
    this.ls = ls;
  }

  /**
   * binary search
   * 
   * @return 인덱스를 리턴한다. 없으면 -1을 리턴한다.
   */
  public int find(T value) {
    int left = 0;
    int right = ls.size();
    // left, right 거리가 충분히 가까워질 때까지 이분검색
    while (right - left > 1) {
      int center = (left + right) / 2;
      T centerVal = ls.get(center);
      int compareResult = value.compareTo(centerVal);
      if (compareResult == 0) {
        return center;
      } else if (compareResult > 0) {
        // move right
        left = center;
      } else if (compareResult < 0) {
        // move left
        right = center;
      }
    }
    // ls[left], ls[right] 직접 검사하기
    if (ls.get(left) == value) {
      return left;
    } else if (right < ls.size() && ls.get(right) == value) {
      return right;
    }
    return -1;
  }

}