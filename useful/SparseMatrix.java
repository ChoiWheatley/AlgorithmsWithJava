package useful;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SparseMatrix<T> {

  private Map<Idx2D, T> table;

  public final int ROW;
  public final int COL;

  public SparseMatrix(int row, int col) {
    this.ROW = row;
    this.COL = col;
    this.table = new HashMap<>();
  }

  public void set(int row, int col, T val) {
    if (row >= ROW || col >= COL ||
        row < 0 || col < 0) {
      throw new IndexOutOfBoundsException();
    }
    Idx2D idx = Idx2D.of(row, col);
    table.put(idx, val);
  }

  public Optional<T> get(int row, int col) {
    return Optional.ofNullable(table.get(Idx2D.of(row, col)));
  }

  public Set<Map.Entry<Idx2D, T>> getR(int row) {
    return table.entrySet().stream().filter(e -> e.getKey().row() == row).collect(Collectors.toSet());
  }

  public Set<Map.Entry<Idx2D, T>> getC(int col) {
    return table.entrySet().stream().filter(e -> e.getKey().col() == col).collect(Collectors.toSet());
  }
}
