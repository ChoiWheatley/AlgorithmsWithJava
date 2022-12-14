package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import useful.Idx2D;

public class Ex17144 {
  public static void main(String[] args) {
    try (var br = new BufferedReader(new InputStreamReader(System.in));
        var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
      String line = br.readLine();
      int[] ints = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
      int row = ints[0];
      int col = ints[1];
      int time = ints[2];

      int[][] rawData = new int[row][];
      for (int i = 0; i < row; ++i) {
        line = br.readLine();
        ints = Stream.of(line.split(" ")).mapToInt(Integer::valueOf).toArray();
        rawData[i] = ints;
      }

      bw.write(Solver17144.solve(rawData, time) + "\n");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Solver17144 {
  public static int solve(int[][] rawData, int time) {
    try {
      var factory = new ConcreteCellFactory(rawData);
      var room = new Room(factory);
      while (time > 0) {
        room.spread();
        room.purify();
        time--;
      }
      int[][] matrix = room.getCurrentState();
      int sum = 0;
      for (var rows : matrix) {
        for (var e : rows) {
          sum += e;
        }
      }
      return sum;
    } catch (AbstractCellFactory.ValidationFailure e) {
      e.printStackTrace();
      System.exit(1);
    }
    return 0;
  }
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
    // ?????? Dust?????? ???????????? sum??? ?????????.
    dusts.forEach(e -> e.spread(cells, sum));
    // sum??? ??? ?????? ????????????.
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
 * ????????? ????????? ???????????? ????????? ??????... ????????? ????????? ???????
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

    boolean isCCW = true;
    for (int i = 0; i < maxRow; ++i) {
      for (int j = 0; j < maxCol; ++j) {
        Cell cell = null;
        if (arr[i][j] < 0) {
          Purifier p = new Purifier(i, j, maxRow, maxCol, isCCW);
          purifiers.add(p);
          cell = p;
          isCCW = !isCCW;
        } else {
          Dust d = new Dust(i, j, maxRow, maxCol, arr[i][j]);
          dusts.add(d);
          cell = d;
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
   * purifiers??? ?????? ???????????? ??????.
   * ??? ?????? purifiers??? ?????? CCW, CW ????????? ????????? ????????? ??????.
   * ?????? purifiers??? col??? 0????????? ??????.
   * purifiers??? ??? ????????? ??????????????? ??????.
   */
  @Override
  public boolean validatePurifiers() {
    if (purifiers.size() == 2 &&
        purifiers.get(0).isCCW == true &&
        purifiers.get(1).isCCW == false &&
        purifiers.stream().anyMatch(e -> e.col() == 0) &&
        purifiers.get(1).row() - purifiers.get(0).row() == 1) {
      return true;
    }
    return false;
  }

  /**
   * dusts??? maxRow * maxCol - 2 ?????? ??????.
   * ?????? dusts?????? amount??? ?????? ?????? ???????????? ??????.
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

  public boolean isSpreadable();
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
    this.index = Idx2D.of(row, col);
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
   * ?????? Purifier??? List<Idx2D>?????? ?????? ??????. ????????? ?????? Cell?????? ??????????????? ????????? ????????? ??????.
   * 
   * @param arr real objects of arr
   */
  public void purify(Cell[][] arr) {
    // ??? ????????? ????????? ???.
    for (int i = 0; i < indices.size() - 1; ++i) {
      var a = indices.get(i);
      var b = indices.get(i + 1);
      arr[a.row()][a.col()].setAmount(
          arr[b.row()][b.col()].getAmount());
    }
    // ????????? ?????? ????????????????????? ????????? ????????? ????????? 0???.
    Idx2D last = indices.get(indices.size() - 1);
    arr[last.row()][last.col()].setAmount(0);
  }

  protected static List<Idx2D> initIndices(int row, int col, int maxRow, int maxCol, boolean isCCW) {
    // initialize indices
    List<Idx2D> ret = new ArrayList<>();
    Idx2D current = Idx2D.of(row, col);
    if (isCCW) {
      // up
      var next = Idx2D.of(0, 0);
      var delta = Idx2D.of(-1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // right
      next = Idx2D.of(0, maxCol - 1);
      delta = Idx2D.of(0, 1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // down
      next = Idx2D.of(row, maxCol - 1);
      delta = Idx2D.of(1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // left
      next = Idx2D.of(row, col + 1);
      delta = Idx2D.of(0, -1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
    } else {
      // down
      var next = Idx2D.of(maxRow - 1, 0);
      var delta = Idx2D.of(1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // right
      next = Idx2D.of(maxRow - 1, maxCol - 1);
      delta = Idx2D.of(0, 1);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // up
      next = Idx2D.of(row, maxCol - 1);
      delta = Idx2D.of(-1, 0);
      while (current.compareTo(next) != 0) {
        current = current.add(delta);
        ret.add(current);
      }
      // left
      next = Idx2D.of(row, col + 1);
      delta = Idx2D.of(0, -1);
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

  @Override
  public boolean isSpreadable() {
    return false;
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
   * ??? ?????? ????????? sum??? ????????????. ?????? ????????? ?????? ?????? ????????? ?????? sum??? ?????? ??????????????? ??????.
   * ????????? ??????????????? ?????? sum??? ????????????.
   * <h1>??????????????? ????????? ????????? ?????????.</h1>
   */
  public void spread(Cell[][] arr, int[][] sum) {
    if (getAmount() <= 0) {
      return;
    }
    int count = 0;
    for (Idx2D index : indices) {
      int r = index.row();
      int c = index.col();
      if (arr[r][c].isSpreadable()) {
        count++;
        sum[r][c] += getSpreadAmount();
      }
    }
    sum[this.row()][this.col()] -= getTakedAmount(count);
  }

  public int getSpreadAmount() {
    return getAmount() / 5;
  }

  public int getRemainAmount(int count) {
    return getAmount() - (getAmount() / 5) * count;
  }

  public int getTakedAmount(int count) {
    return (getAmount() / 5) * count;
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

  @Override
  public boolean isSpreadable() {
    return true;
  }

}

// class Pair<A extends Comparable<A>, B extends Comparable<B>> implements
// Comparable<Pair<A, B>> {
// public final A first;
// public final B second;

// public Pair(A a, B b) {
// first = a;
// second = b;
// }

// @Override
// public int compareTo(Pair<A, B> o) {
// int ret = this.first.compareTo(o.first);
// if (ret == 0) {
// ret = this.second.compareTo(o.second);
// }
// return ret;
// }
// }
