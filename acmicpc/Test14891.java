package acmicpc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static acmicpc.Solver14891.*;

import org.junit.Test;

public class Test14891 {
  @Test
  public void getBitTest() {
    int sample = 0b100_0011;
    assertEquals(1, getBit(sample, 0));
    assertEquals(1, getBit(sample, 1));
    assertEquals(0, getBit(sample, 2));
    assertEquals(0, getBit(sample, 3));
    assertEquals(0, getBit(sample, 4));
    assertEquals(0, getBit(sample, 5));
    assertEquals(1, getBit(sample, 6));
  }

  @Test
  public void setBitTest() {
    int sample = 0b1000_0000_0000_0000;
    assertEquals(0b1000_0000_0000_0001, setBit(sample, 0, 1));
    assertEquals(0b1000_0000_0000_0010, setBit(sample, 1, 1));
    assertEquals(0b1000_0000_0000_0100, setBit(sample, 2, 1));
    assertEquals(0b1000_0000_0000_1000, setBit(sample, 3, 1));
    assertEquals(0b1000_0000_0001_0000, setBit(sample, 4, 1));
    assertEquals(0b1000_0000_0010_0000, setBit(sample, 5, 1));
    assertEquals(0b1000_0000_0100_0000, setBit(sample, 6, 1));
    assertEquals(0b1000_0000_1000_0000, setBit(sample, 7, 1));
    assertEquals(0b1000_0001_0000_0000, setBit(sample, 8, 1));
    assertEquals(0b1000_0010_0000_0000, setBit(sample, 9, 1));
    assertEquals(0b1000_0100_0000_0000, setBit(sample, 10, 1));
    assertEquals(0b1000_1000_0000_0000, setBit(sample, 11, 1));
    assertEquals(0b1001_0000_0000_0000, setBit(sample, 12, 1));
    assertEquals(0b1010_0000_0000_0000, setBit(sample, 13, 1));
    assertEquals(0b1100_0000_0000_0000, setBit(sample, 14, 1));
  }

  @Test
  public void doRotateTest() {
    int sample = 0b010111;
    int output = 0b101110;
    assertEquals(output, doRotate(sample, 6, true));
    output = 0b101011;
    assertEquals(output, doRotate(sample, 6, false));
  }

  @Test
  public void rotateTest() {
    var solver = new Solver14891(new int[] {
        0b10101111,
        0b01111101,
        0b11001110,
        0b00000010
    });
    // 3번 톱니바퀴를 반시계 방향으로 회전했을 때
    var answer = new int[] {
        0b10101111,
        0b01111101,
        0b10011101,
        0b00000001
    };
    solver.rotate(2, true);
    assertArrayEquals(answer, solver.get());

    // 1번 톱니바퀴를 시계 방향으로 회전했을 때
    answer = new int[] {
        0b11010111,
        0b11111010,
        0b11001110,
        0b00000001
    };
    solver.rotate(0, false);
    assertArrayEquals(answer, solver.get());
  }

  @Test
  public void solveTest1() {
    var sample = new int[] {
        0b10101111,
        0b01111101,
        0b11001110,
        0b00000010
    };
    var indices = new int[] { 3, 1 };
    var isCCWs = new boolean[] { true, false };

    assertEquals(7, solve(sample, indices, isCCWs));
  }

  @Test
  public void solveTest2() {
    var sample = new int[] {
        0b11111111,
        0b11111111,
        0b11111111,
        0b11111111
    };
    var indices = new int[] { 1, 2, 3 };
    var isCCWs = new boolean[] { false, false, false };
    assertEquals(15, solve(sample, indices, isCCWs));
  }
}
