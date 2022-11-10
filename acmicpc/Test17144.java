package acmicpc;

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