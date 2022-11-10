package acmicpc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Ex17144 {
  public static void main(String[] args) {

  }
}

class Solver17144 {

}

class AirPurifiersCountsOff extends Throwable {
  final int count;

  public AirPurifiersCountsOff(int count) {
    this.count = count;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + "count: " + count;
  }
}

class AirPurifierInAWronLocation extends Throwable {
  final int[] row;
  final int[] col;

  public AirPurifierInAWronLocation(int[] r, int[] c) {
    row = r;
    col = c;
  }

  public AirPurifierInAWronLocation(int r1, int r2, int c1, int c2) {
    this(new int[] { r1, r2 }, new int[] { c1, c2 });
  }

  @Override
  public String getMessage() {
    String one = "{ row: " + row[0] + ", col: " + col[0] + " }";
    String two = "{ row: " + row[1] + ", col: " + col[1] + " }";
    return super.getMessage() + one + "\n" + two + "\n";
  }
}

class Room {
  AirPurifier[] purifiers;
  Dust[] dusts;
  int[][] cachedDustStat;
  boolean cached = false;

  public final int row;
  public final int col;

  protected Room(AirPurifier[] purifiers, Dust[] dusts, int r, int c) {
    this.purifiers = purifiers;
    this.dusts = dusts;
    row = r;
    col = c;
    cachedDustStat = new int[row][col];
  }

  /**
   * 모든 Dust들의 값들을 해당 인덱스에 넣어 2차원 배열로 리턴한다.
   */
  public int[][] getDustStat() {
    if (cached) {
      return cachedDustStat;
    }
    cachedDustStat = new int[row][col];
    for (var dust : dusts) {
      int r = dust.pos.row;
      int c = dust.pos.col;
      cachedDustStat[r][c] = dust.getAmount();
    }
    return cachedDustStat;
  }

  /**
   * 2d int 배열을 Room으로 변환. arr 검증과 order-sensitive한 배열 cells를 만들어낸다.
   */
  public static Room create(int[][] arr) throws Throwable {
    int maxRow = arr.length;
    int maxCol = arr[0].length;

    // MARK: 값을 읽고 AirPurifier와 Dust를 생성한다.
    List<AirPurifier> purifiers = new ArrayList<>(2);
    Map<Position, Dust> dusts = new HashMap<>();
    for (int i = 0; i < maxRow; ++i) {
      for (int j = 0; j < maxCol; ++j) {
        var amount = arr[i][j];
        if (amount < 0) {
          purifiers.add(new AirPurifier(i, j, maxRow, maxCol));
        } else {
          Dust dust = new Dust(i, j, maxRow, maxCol, amount);
          dusts.put(dust.pos, dust);
        }
      }
    }

    // MARK: validate를 통해 arr가 잘 들어왔는지 검증한다.
    validate(purifiers);

    // MARK: link를 이어준다. order-sensitive하다.
    for (Dust d : dusts.values()) {
      // 4-ways
      int r = d.pos.row;
      int c = d.pos.col;
      Position up = new Position(r - 1, c, maxRow, maxCol);
      Position down = new Position(r + 1, c, maxRow, maxCol);
      Position left = new Position(r, c - 1, maxRow, maxCol);
      Position right = new Position(r, c + 1, maxRow, maxCol);

      d.addDust(Optional.ofNullable(dusts.get(up)));
      d.addDust(Optional.ofNullable(dusts.get(down)));
      d.addDust(Optional.ofNullable(dusts.get(left)));
      d.addDust(Optional.ofNullable(dusts.get(right)));
    }
    // upper air purifier
    AirPurifier first = purifiers.get(0);
    var order = positionsCCW(first.pos, maxRow, maxCol);
    for (Position pos : order) {
      var dust = dusts.get(pos);
      assert dust != null;
      first.addDust(dust);
    }

    // lower air purifier
    AirPurifier second = purifiers.get(1);
    order = positionsCW(second.pos, maxRow, maxCol);
    for (Position pos : order) {
      var dust = dusts.get(pos);
      assert dust != null;
      second.addDust(dust);
    }

    // MARK: build Room instance
    AirPurifier[] purifierArr = purifiers.toArray(AirPurifier[]::new);
    Dust[] dustArr = dusts.values().toArray(Dust[]::new);
    Room room = new Room(purifierArr, dustArr, maxRow, maxCol);
    return room;
  }

  /** 시계방향으로 공기를 순환시키는 링크를 반환 */
  public static List<Position> positionsCCW(Position start, int maxRow, int maxCol) {
    List<Position> ret = new ArrayList<>();
    // go down
    for (int row = start.row + 1; row < maxRow; ++row) {
      ret.add(new Position(row, start.col, maxRow, maxCol));
    }
    // go right
    for (int col = 0; col < maxCol; ++col) {
      ret.add(new Position(maxRow - 1, col, maxRow, maxCol));
    }
    // go up
    for (int row = maxRow - 1; row >= start.row; --row) {
      ret.add(new Position(row, maxCol - 1, maxRow, maxCol));
    }
    // go left
    for (int col = maxCol - 1; col > 0; --col) {
      ret.add(new Position(start.row, col, maxRow, maxCol));
    }

    return ret;
  }

  /** 반시계방향으로 공기를 순환시키는 링크를 반환 */
  public static List<Position> positionsCW(Position start, int maxRow, int maxCol) {
    List<Position> ret = new ArrayList<>();
    // go up
    for (int row = start.row - 1; row >= 0; --row) {
      ret.add(new Position(row, start.col, maxRow, maxCol));
    }
    // go right
    for (int col = 0; col < maxCol; ++col) {
      ret.add(new Position(0, col, maxRow, maxCol));
    }
    // go down
    for (int row = 0; row <= start.row; ++row) {
      ret.add(new Position(row, maxCol - 1, maxRow, maxCol));
    }
    // go left
    for (int col = maxCol - 1; col > 0; --col) {
      ret.add(new Position(start.row, col, maxRow, maxCol));
    }

    return ret;
  }

  public static void validate(List<AirPurifier> purifiers) throws Throwable {
    // AirPurifier 개수가 정확히 2개인지
    if (purifiers.size() != 2)
      throw new AirPurifiersCountsOff(purifiers.size());
    // AirPurifier가 1열에 서로 붙어있는지
    var p1 = purifiers.get(0);
    var p2 = purifiers.get(1);
    if (p1.pos.col != 0 || p2.pos.col != 0 ||
        p2.pos.row - p1.pos.row != 1) {
      throw new AirPurifierInAWronLocation(p1.pos.row, p2.pos.row, p1.pos.col, p2.pos.col);
    }
  }
}

class Position {
  public final int row;
  public final int col;
  public final int maxRow;
  public final int maxCol;

  public Position(int row, int col, int maxRow, int maxCol) {
    this.row = row;
    this.col = col;
    this.maxRow = maxRow;
    this.maxCol = maxCol;
  }

  @Override
  public int hashCode() {
    return row * maxCol + col;
  }

  @Override
  public boolean equals(Object obj) {
    return this.hashCode() == obj.hashCode();
  }
}

class AirPurifier {
  public Position pos;

  protected List<Dust> dusts;

  public AirPurifier(Position pos) {
    this.pos = pos;
    dusts = new ArrayList<>(pos.row * pos.col);
  }

  public AirPurifier(int r, int c, int maxRow, int maxCol) {
    this(new Position(r, c, maxRow, maxCol));
  }

  public void addDust(Dust d) {
    dusts.add(d);
  }

  // /**
  // * links 순서대로 값을 한 칸씩 당긴다. 첫번째 원소는 에어컨에 빨려들어가 사라진다고 가정한다.
  // */
  public void purify() {
    for (int idx = 0; idx < dusts.size() - 1; ++idx) {
      var cur = dusts.get(idx);
      var next = dusts.get(idx + 1);
      cur.setDustAmount(next.getAmount());
    }
    // last element becomes 0
    dusts.get(dusts.size() - 1).setDustAmount(0);
  }
}

class Dust {
  public Position pos;

  private List<Dust> dusts;
  private int amount;

  public Dust(Position pos, int amount) {
    this.pos = pos;
    this.amount = amount;
    this.dusts = new ArrayList<>(4);
  }

  public Dust(int r, int c, int maxRow, int maxCol, int amount) {
    this(new Position(r, c, maxRow, maxCol), amount);
  }

  public void addDust(Optional<Dust> d) {
    if (d.isPresent()) {
      dusts.add(d.get());
    }
  }

  public int getAmount() {
    return this.amount;
  }

  protected List<Dust> getLinkedDusts() {
    return dusts;
  }

  public void setDustAmount(int amount) {
    this.amount = amount;
  }

  public void spread() {
    if (this.amount == 0)
      return;
    int cnt = dusts.size();
    int spreadAmount = getAmount() / 5;
    for (Dust linked : getLinkedDusts()) {
      int yours = linked.getAmount();
      yours = yours + spreadAmount;
      linked.setDustAmount(yours);
    }
    int remainder = getAmount() - (spreadAmount * cnt);
    setDustAmount(remainder);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Dust) {
      Dust other = (Dust) obj;
      if (this.pos.equals(other.pos) &&
          this.getAmount() == other.getAmount()) {
        return true;
      }
    }
    return false;
  }
}
