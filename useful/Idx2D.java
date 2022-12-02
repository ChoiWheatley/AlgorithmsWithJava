package useful;

import java.util.Map;
import java.util.TreeMap;

public class Idx2D extends Pair<Integer, Integer> {
  public static enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    private final Idx2D delta;

    Direction(Integer row, Integer col) {
      this.delta = Idx2D.of(row, col);
    }

    public Idx2D getDelta() {
      return this.delta;
    }
  }

  private static Map<Integer, Map<Integer, Idx2D>> cached = new TreeMap<>();

  /** singleton..? */
  public static Idx2D of(Integer a, Integer b) {
    if (cached.get(a) == null) {
      cached.put(a, new TreeMap<>());
    }
    if (cached.get(a).get(b) == null) {
      cached.get(a).put(b, new Idx2D(a, b));
    }
    return cached.get(a).get(b);
  }

  public Idx2D(Integer row, Integer col) {
    super(row, col);
  }

  public int row() {
    return first;
  }

  public int col() {
    return second;
  }

  public Idx2D add(int row, int col) {
    return Idx2D.of(this.row() + row, this.col() + col);
  }

  public Idx2D add(Idx2D o) {
    return Idx2D.of(this.row() + o.row(), this.col() + o.col());
  }

  public Idx2D add(Direction d) {
    return add(d.delta);
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
