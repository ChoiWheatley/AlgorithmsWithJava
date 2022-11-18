package acmicpc;

import java.util.ArrayList;
import java.util.List;

import acmicpc.AbstractCellFactory.InvalidPurifiers;

public class Ex17144 {
  public static void main(String[] args) {

  }
}

class Solver17144 {

}

class Room {
  public final int maxRow;
  public final int maxCol;

  private Cell[][] cells;
  private List<Dust> dusts;
  private List<Purifier> purifiers;
  private int[][] cachedState;
  private boolean cached = false;

  public Room(AbstractCellFactory factory) {
    cells = factory.cells;
    dusts = factory.dusts;
    purifiers = factory.purifiers;
    maxRow = cells.length;
    maxCol = cells[0].length;
    cachedState = new int[maxRow][maxCol];
  }

  public int[][] getCurrentState() {
    if (cached) {
      return cachedState;
    }
    // Do get current state
    for (int i = 0; i < maxRow; ++i) {
      for (int j = 0; j < maxCol; ++j) {
        cachedState[i][j] = cells[i][j].getAmount();
      }
    }
    cached = true;
    return cachedState;
  }

  public void spread() {
    int[][] sum = new int[maxRow][maxCol];
    // 모든 Dust들을 순회하며 sum을 쌓는다.
    dusts.forEach(e -> e.spread(cells, sum));
    // sum을 한 번에 적용한다.
    dusts.forEach(e -> {
      int r = e.row();
      int c = e.col();
      var targetAmount = e.getAmount() + sum[r][c];
      e.setAmount(targetAmount);
    });
    cached = false;
  }

  public void purify() {
    purifiers.forEach(e -> e.purify(cells));
    cached = false;
  }
}

/**
 * 엄밀히 말하면 팩토리가 아니긴 한데... 뭐라고 불러야 하지?
 */
abstract class AbstractCellFactory {
  public class ValidationFailure extends Exception {
  }

  public class InvalidPurifiers extends ValidationFailure {
  }

  public class InvalidDusts extends ValidationFailure {
  }

  public Cell[][] cells;
  public List<Purifier> purifiers;
  public List<Dust> dusts;
  public final int maxRow;
  public final int maxCol;

  public AbstractCellFactory(int[][] arr) throws ValidationFailure {
    maxRow = arr.length;
    maxCol = arr[0].length;
    cells = new Cell[maxRow][maxCol];
    purifiers = new ArrayList<>(2);
    dusts = new ArrayList<>(maxRow * maxCol);

    boolean isCCW = false;
    for (int i = 0; i < maxRow; ++i) {
      for (int j = 0; j < maxCol; ++j) {
        Cell cell = null;
        if (arr[i][j] < 0) {
          Purifier p = new Purifier(i, j, maxRow, maxCol, isCCW);
          purifiers.add(p);
          cell = p;
          isCCW = !isCCW;
        } else {
          // TODO: Dust init
        }
        cells[i][j] = cell;
      }
    }

    // Open Close Principle :: Template Method Pattern
    if (!validatePurifiers()) {
      throw new InvalidPurifiers();
    }

    if (!validateDusts()) {
      throw new InvalidDusts();
    }
  }

  public List<Purifier> getPurifiers() {
    return this.purifiers;
  }

  public List<Dust> getDusts() {
    return this.dusts;
  }

  public abstract boolean validatePurifiers();

  public abstract boolean validateDusts();
}

class ConcreteCellFactory extends AbstractCellFactory {

  public ConcreteCellFactory(int[][] arr) throws ValidationFailure {
    super(arr);
  }

  /**
   * purifiers는 오직 두개여야 한다.
   * 두 개의 purifiers는 각각 CCW, CW 속성을 가지고 있어야 한다.
   */
  @Override
  public boolean validatePurifiers() {
    if (purifiers.size() == 2 &&
        purifiers.get(0).isCCW == true &&
        purifiers.get(1).isCCW == false) {
      return true;
    }
    return false;
  }

  /**
   * dusts는 maxRow * maxCol - 2 여야 한다.
   * 모든 dusts들의 amount는 음이 아닌 정수여야 한다.
   */
  @Override
  public boolean validateDusts() {
    if (dusts.size() == maxRow * maxCol - 2 &&
        dusts.stream().anyMatch(e -> e.getAmount() >= 0)) {
      return true;
    }
    return false;
  }

}

interface Cell {
  public int row();

  public int col();

  public int maxRow();

  public int maxCol();

  public int getAmount();

  public void setAmount(int amount);
}

class Purifier implements Cell {

  @Override
  public int row() {
    return index.row();
  }

  @Override
  public int col() {
    return index.col();
  }

  @Override
  public int maxRow() {
    return maxRow;
  }

  @Override
  public int maxCol() {
    return maxCol;
  }

  @Override
  public int getAmount() {
    return 0;
  }

  private final Idx2D index;
  private final int maxRow;
  private final int maxCol;

  protected List<Idx2D> indices;

  public final boolean isCCW;

  public Purifier(int row, int col, int maxRow, int maxCol, boolean isCCW) {
    this.index = new Idx2D(row, col);
    this.maxRow = maxRow;
    this.maxCol = maxCol;
    this.isCCW = isCCW;
    indices = initIndices(row, col, maxRow, maxCol, isCCW);
  }

  public Purifier(Idx2D index, int maxRow, int maxCol, boolean isCCW) {
    this.index = index;
    this.maxRow = maxRow;
    this.maxCol = maxCol;
    this.isCCW = isCCW;
    indices = initIndices(index.row(), index.col(), maxRow, maxCol, isCCW);
  }

  /**
   * 현재 Purifier는 List<Idx2D>만을 갖고 있다. 그래서 실제 Cell들의 레퍼런스를 참조해 주어야 한다.
   * 
   * @param arr real objects of arr
   */
  public void purify(Cell[][] arr) {
    // 한 칸씩만 당기면 끗.
    for (int i = 0; i < indices.size() - 1; ++i) {
      var a = indices.get(i);
      var b = indices.get(i + 1);
      arr[a.row()][a.col()].setAmount(
          arr[b.row()][b.col()].getAmount());
    }
    // 마지막 칸은 공기청정기에서 나왔기 때문에 무조건 0임.
    Idx2D last = indices.get(indices.size() - 1);
    arr[last.row()][last.col()].setAmount(0);
  }

  protected static List<Idx2D> initIndices(int row, int col, int maxRow, int maxCol, boolean isCCW) {
    // initialize indices
    List<Idx2D> ret = new ArrayList<>();
    Idx2D current = new Idx2D(row, col);
    if (isCCW) {
      // up
      var next = new Idx2D(0, 0);
      var delta = new Idx2D(-1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // right
      next = new Idx2D(0, maxCol - 1);
      delta = new Idx2D(0, 1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // down
      next = new Idx2D(row, maxCol - 1);
      delta = new Idx2D(1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // left
      next = new Idx2D(row, col + 1);
      delta = new Idx2D(0, -1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
    } else {
      // down
      var next = new Idx2D(maxRow - 1, 0);
      var delta = new Idx2D(1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // right
      next = new Idx2D(maxRow - 1, maxCol - 1);
      delta = new Idx2D(0, 1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // up
      next = new Idx2D(row, maxCol - 1);
      delta = new Idx2D(-1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // left
      next = new Idx2D(row, col + 1);
      delta = new Idx2D(0, -1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
    }
    return ret;
  }

  @Override
  public void setAmount(int amount) {
    // do nothing
  }
}

class Dust implements Cell {

  @Override
  public int row() {
    return index.row();
  }

  @Override
  public int col() {
    return index.col();
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public int maxRow() {
    return maxRow;
  }

  @Override
  public int maxCol() {
    return this.maxCol;
  }

  @Override
  public void setAmount(int amount) {
    this.amount = amount;
  }

  /**
   * 이 코드 안에선 sum만 변경한다. 인접 셀들의 값을 직접 바꾸지 않고 sum에 값을 추가하기만 한다.
   * 빼앗긴 미세먼지의 양도 sum에 적용한다.
   * 
   * @param arr
   * @param sum
   */
  public void spread(Cell[][] arr, int[][] sum) {
    if (getAmount() <= 0) {
      return;
    }
    for (Idx2D index : indices) {
      int r = index.row();
      int c = index.col();
      sum[r][c] += getSpreadAmount();
    }
    sum[this.row()][this.col()] -= getTakedAmount();
  }

  public int getSpreadAmount() {
    return getAmount() / 5;
  }

  public int getRemainAmount() {
    return getAmount() - (getAmount() / 5) * indices.size();
  }

  public int getTakedAmount() {
    return (getAmount() / 5) * indices.size();
  }

  private final Idx2D index;
  private final int maxRow;
  private final int maxCol;

  protected final List<Idx2D> indices;

  private int amount;

  public Dust(Idx2D index, int maxRow, int maxCol, int amount) {
    this.index = index;
    this.maxRow = maxRow;
    this.maxCol = maxCol;
    this.amount = amount;
    indices = initIndices(index.row(), index.col(), maxRow, maxCol);
  }

  public Dust(int row, int col, int maxRow, int maxCol, int amount) {
    this.index = Idx2D.of(row, col);
    this.maxRow = maxRow;
    this.maxCol = maxCol;
    this.amount = amount;
    indices = initIndices(index.row(), index.col(), maxRow, maxCol);
  }

  private static List<Idx2D> initIndices(int row, int col, int maxRow, int maxCol) {
    List<Idx2D> ret = new ArrayList<>(4);
    // up
    if (row > 0) {
      ret.add(Idx2D.of(row - 1, col));
    }
    // left
    if (col > 0) {
      ret.add(Idx2D.of(row, col - 1));
    }
    // right
    if (col < maxCol - 1) {
      ret.add(Idx2D.of(row, col + 1));
    }
    // down
    if (row < maxRow - 1) {
      ret.add(Idx2D.of(row + 1, col));
    }
    return ret;
  }

}

class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
  public final A first;
  public final B second;

  public Pair(A a, B b) {
    first = a;
    second = b;
  }

  @Override
  public int compareTo(Pair<A, B> o) {
    int ret = this.first.compareTo(o.first);
    if (ret == 0) {
      ret = this.second.compareTo(o.second);
    }
    return ret;
  }
}

class Idx2D extends Pair<Integer, Integer> {
  public int row() {
    return first;
  }

  public int col() {
    return second;
  }

  public Idx2D(Integer row, Integer col) {
    super(row, col);
  }

  public static Idx2D of(int row, int col) {
    return new Idx2D(row, col);
  }

  public Idx2D add(int row, int col) {
    return new Idx2D(this.row() + row, this.col() + col);
  }

  public Idx2D add(Idx2D o) {
    return new Idx2D(this.row() + o.row(), this.col() + o.col());
  }

  public Idx2D subtract(int row, int col) {
    return new Idx2D(this.row() - row, this.col() - col);
  }

  public Idx2D subtract(Idx2D o) {
    return new Idx2D(this.row() - o.row(), this.col() - o.col());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Idx2D) {
      return this.compareTo((Idx2D) obj) == 0;
    }
    return false;
  }
}
