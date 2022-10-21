package programmers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12939
 * BigInteger 사용방법 개꿀
 */

public class Ex12939 {
  public String solution(String s) {
    String[] tokens = s.split(" ");
    BigInteger[] integers = Stream.of(tokens)
        .map(e -> new BigInteger(e))
        .toArray(BigInteger[]::new);
    Map.Entry<BigInteger, BigInteger> minMax = getMinMax(integers);

    BigInteger min = minMax.getKey();
    BigInteger max = minMax.getValue();

    return min.toString() + " " + max.toString();
  }

  public Map.Entry<BigInteger, BigInteger> getMinMax(BigInteger[] integers) {
    BigInteger min = null;
    BigInteger max = null;

    for (BigInteger each : integers) {
      if (min == null || each.compareTo(min) < 0) {
        min = each;
      }
      if (max == null || each.compareTo(max) > 0) {
        max = each;
      }
    }
    return new AbstractMap.SimpleImmutableEntry<>(min, max);
  }

  @Test
  public void looooongIntegerTest() {
    String loooooongIntegers = "124353749857234729384729385 8999999999999999999999999999999999999999999999 0 1 2 3 4 5 -99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";
    assertEquals(
        "-99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 8999999999999999999999999999999999999999999999",
        solution(loooooongIntegers));
  }
}
