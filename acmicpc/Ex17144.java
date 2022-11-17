package acmicpc;

import java.util.ArrayList;
import java.util.List;

public class Ex17144 {
  public static void main(String[] args) {

  }
}

class Solver17144 {

}

interface Cell {
  public int row();

  public int col();

  public int maxRow();

  public int maxCol();

  public int getAmount();
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

  public void purify(Cell[][] arr) {
    // TODO
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
}

// class Dust implements Cell {

// @Override
// public int row() {
// // TODO Auto-generated method stub
// return 0;
// }

// @Override
// public int col() {
// // TODO Auto-generated method stub
// return 0;
// }

// @Override
// public int getAmount() {
// // TODO Auto-generated method stub
// return 0;
// }

// }

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
