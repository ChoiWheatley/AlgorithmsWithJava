package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

public class Test17144 {
  @Test
  public void purifierIndicesTest() {
    int maxRow = 7;
    int maxCol = 8;
    // first purifier must be CCW
    Idx2D purifierUpIdx = new Idx2D(2, 0);
    var purifier = new PurifierTest(purifierUpIdx, maxRow, maxCol, true);
    List<Idx2D> answer = List.of(
        // up
        new Idx2D(1, 0),
        new Idx2D(0, 0),
        // right
        new Idx2D(0, 1),
        new Idx2D(0, 2),
        new Idx2D(0, 3),
        new Idx2D(0, 4),
        new Idx2D(0, 5),
        new Idx2D(0, 6),
        new Idx2D(0, 7),
        // down
        new Idx2D(1, 7),
        new Idx2D(2, 7),
        // left
        new Idx2D(2, 6),
        new Idx2D(2, 5),
        new Idx2D(2, 4),
        new Idx2D(2, 3),
        new Idx2D(2, 2),
        new Idx2D(2, 1));
    assertArrayEquals(answer.toArray(Idx2D[]::new), purifier.getIndices().toArray(Idx2D[]::new));

    // second purifier must be CW
    Idx2D purifierDownIdx = new Idx2D(3, 0);
    purifier = new PurifierTest(purifierDownIdx, maxRow, maxCol, false);
    answer = List.of(
        // down
        new Idx2D(4, 0),
        new Idx2D(5, 0),
        new Idx2D(6, 0),
        // right
        new Idx2D(6, 1),
        new Idx2D(6, 2),
        new Idx2D(6, 3),
        new Idx2D(6, 4),
        new Idx2D(6, 5),
        new Idx2D(6, 6),
        new Idx2D(6, 7),
        // up
        new Idx2D(5, 7),
        new Idx2D(4, 7),
        new Idx2D(3, 7),
        // left
        new Idx2D(3, 6),
        new Idx2D(3, 5),
        new Idx2D(3, 4),
        new Idx2D(3, 3),
        new Idx2D(3, 2),
        new Idx2D(3, 1));

    assertArrayEquals(answer.toArray(Idx2D[]::new), purifier.getIndices().toArray(Idx2D[]::new));
  }

  @Test
  public void dustIndicesTest() {
    int maxRow = 3;
    int maxCol = 3;

    Idx2D idx = Idx2D.of(0, 0);
    Idx2D[] answer = new Idx2D[] { Idx2D.of(0, 1), Idx2D.of(1, 0) };
    var dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(0, 1);
    answer = new Idx2D[] { Idx2D.of(0, 0), Idx2D.of(0, 2), Idx2D.of(1, 1) };
    dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(0, 2);
    answer = new Idx2D[] { Idx2D.of(0, 1), Idx2D.of(1, 2) };
    dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(1, 0);
    answer = new Idx2D[] { Idx2D.of(0, 0), Idx2D.of(1, 1), Idx2D.of(2, 0) };
    dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(1, 1);
    answer = new Idx2D[] { Idx2D.of(0, 1), Idx2D.of(1, 0), Idx2D.of(1, 2), Idx2D.of(2, 1) };
    dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(1, 2);
    answer = new Idx2D[] { Idx2D.of(0, 2), Idx2D.of(1, 1), Idx2D.of(2, 2), };
    dust = new DustTest(idx, maxRow, maxCol, 0);
    assertArrayEquals(answer, dust.getIndices().toArray(Idx2D[]::new));

    idx = Idx2D.of(2, 0);
    answer = new Idx2D[] { Idx2D.of(1, 0), Idx2D.of(2, 1), };

    idx = Idx2D.of(2, 1);
    answer = new Idx2D[] { Idx2D.of(1, 1), Idx2D.of(2, 0), Idx2D.of(2, 2) };

    idx = Idx2D.of(2, 2);
    answer = new Idx2D[] { Idx2D.of(1, 2), Idx2D.of(2, 1), };
  }

  @Test
  public void dustSpread() {
    int maxRow = 3;
    int maxCol = 3;
    Cell[][] matrix = new Dust[maxRow][maxCol];
    for (int i = 0; i < maxRow; ++i) {
      for (int j = 0; j < maxCol; ++j) {
        matrix[i][j] = new Dust(i, j, maxRow, maxCol, 0);
      }
    }
    matrix[1][1].setAmount(10);
    /**
     * 0 0 0
     * 0 10 0
     * 0 0 0
     */
  }

  @Test
  public void purifyTest() {
    /**
     * ccw 기준
     * before:
     * 2 3 4 5
     * 1 9 9 6
     * p 9 8 7
     * after:
     * 3 4 5 6
     * 2 9 9 7
     * p 0 9 8
     */
    // TODO: purify() 함수 테스트하기
  }
}

class PurifierTest extends Purifier {

  public PurifierTest(int row, int col, int maxRow, int maxCol, boolean isCCW) {
    super(row, col, maxRow, maxCol, isCCW);
  }

  public PurifierTest(Idx2D index, int maxRow, int maxCol, boolean isCCW) {
    super(index, maxRow, maxCol, isCCW);
  }

  public List<Idx2D> getIndices() {
    return super.indices;
  }

}

class DustTest extends Dust {

  public DustTest(Idx2D index, int maxRow, int maxCol, int amount) {
    super(index, maxRow, maxCol, amount);
  }

  public List<Idx2D> getIndices() {
    return super.indices;
  }
}