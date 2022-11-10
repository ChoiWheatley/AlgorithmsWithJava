package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class Test17144 {
  @Test
  public void purifyTest() {
    List<Dust> beforePurify = Arrays.asList(
        new StubDust(0),
        new StubDust(1),
        new StubDust(2),
        new StubDust(3),
        new StubDust(4),
        new StubDust(5),
        new StubDust(6));
    List<Dust> afterPurify = Arrays.asList(
        new StubDust(1),
        new StubDust(2),
        new StubDust(3),
        new StubDust(4),
        new StubDust(5),
        new StubDust(6),
        new StubDust(0));

    StubAirPurifier ap = new StubAirPurifier(new Position(3, 0, 30, 30));
    beforePurify.forEach(e -> ap.addDust(e));
    ap.purify();

    assertEquals(ap.getDusts(), afterPurify);
  }

  @Test
  public void spreadTest() {
    int centerVal = 10;
    List<Integer> values = Arrays.asList(0, 1, 10, 11);
    List<Integer> afters = values.stream().map(e -> e + centerVal / 5).toList();
    int afterVal = 10 - (10 / 5) * values.size();

    StubDust center = new StubDust(centerVal);
    for (var v : values) {
      center.addDust(Optional.of(StubDust.of(v)));
    }
    assertEquals(centerVal, center.getAmount());

    center.spread();
    assertEquals(afterVal, center.getAmount());

    List<Integer> afterSpread = center.getLinkedDusts().stream().map((Dust e) -> e.getAmount()).toList();
    assertEquals(afters, afterSpread);
  }

  @Test
  public void roomTest() throws AirPurifierInAWronLocation, AirPurifiersCountsOff {
    /**
     * before
     * 0 0 0 0 0 0 0 9
     * 0 0 0 0 3 0 0 8
     * -1 0 5 0 0 0 22 0
     * -1 8 0 0 0 0 0 0
     * 0 0 0 0 0 10 43 0
     * 0 0 5 0 15 0 0 0
     * 0 0 40 0 0 0 20 0
     * 
     * after spread
     * 0 0 0 0 0 0 1 8
     * 0 0 1 0 3 0 5 6
     * -1 2 1 1 0 4 6 5
     * -1 5 2 0 0 2 12 0
     * 0 1 1 0 5 10 13 8
     * 0 1 9 4 3 5 12 0
     * 0 8 17 8 3 4 8 4
     * 
     * after purify
     * 0 0 0 0 0 1 8 6
     * 0 0 1 0 3 0 5 5
     * -1 0 2 1 1 0 4 6
     * -1 0 5 2 0 0 2 12
     * 0 1 1 0 5 10 13 0
     * 0 1 9 4 3 5 12 8
     * 8 17 8 3 4 8 4 0
     */
    int[][] initial = new int[][] {
        new int[] { 0, 0, 0, 0, 0, 0, 0, 9 },
        new int[] { 0, 0, 0, 0, 3, 0, 0, 8, },
        new int[] { -1, 0, 5, 0, 0, 0, 22, 0, },
        new int[] { -1, 8, 0, 0, 0, 0, 0, 0 },
        new int[] { 0, 0, 0, 0, 0, 10, 43, 0 },
        new int[] { 0, 0, 5, 0, 15, 0, 0, 0 },
        new int[] { 0, 0, 40, 0, 0, 0, 20, 0 }
    };

    int[][] afterSpread = new int[][] {
        new int[] { 0, 0, 0, 0, 0, 0, 1, 8, },
        new int[] { 0, 0, 1, 0, 3, 0, 5, 6, },
        new int[] { 0, 2, 1, 1, 0, 4, 6, 5, },
        new int[] { 0, 5, 2, 0, 0, 2, 12, 0 },
        new int[] { 0, 1, 1, 0, 5, 10, 13, 8 },
        new int[] { 0, 1, 9, 4, 3, 5, 12, 0, },
        new int[] { 0, 8, 17, 8, 3, 4, 8, 4, },
    };

    int[][] afterPurify = new int[][] {
        new int[] { 0, 0, 0, 0, 0, 1, 8, 6, },
        new int[] { 0, 0, 1, 0, 3, 0, 5, 5, },
        new int[] { 0, 0, 2, 1, 1, 0, 4, 6, },
        new int[] { 0, 0, 5, 2, 0, 0, 2, 12 },
        new int[] { 0, 1, 1, 0, 5, 10, 13, 0 },
        new int[] { 0, 1, 9, 4, 3, 5, 12, 8, },
        new int[] { 8, 17, 8, 3, 4, 8, 4, 0, },
    };

    try {
      Room room = RoomGenerator.create(initial);
      room.doSpread();
      assertArrayEquals(afterSpread, room.getDustStat());
      room.doPurify();
      assertArrayEquals(afterPurify, room.getDustStat());
    } catch (AirPurifierInAWronLocation e) {
      e.printStackTrace();
      throw e;
    } catch (AirPurifiersCountsOff e) {
      e.printStackTrace();
      throw e;
    }
  }
}

class StubAirPurifier extends AirPurifier {
  public StubAirPurifier(Position pos) {
    super(pos);
  }

  public List<Dust> getDusts() {
    return dusts;
  }
}

class StubDust extends Dust {
  public StubDust(int amount) {
    super(0, 0, 30, 30, amount);
  }

  public static Dust of(int amount) {
    return new StubDust(amount);
  }

  public static List<Dust> asList(int... amounts) {
    List<Dust> ret = new ArrayList<>();
    for (var i : amounts) {
      ret.add(StubDust.of(i));
    }
    return ret;
  }

  @Override
  public List<Dust> getLinkedDusts() {
    return super.getLinkedDusts();
  }
}