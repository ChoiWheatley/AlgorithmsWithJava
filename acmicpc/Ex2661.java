package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;

public class Ex2661 {
  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.valueOf(reader.readLine());
    int[] answer = Solver2661.solve(n);
    for (int each : answer) {
      System.out.print(each);
    }
    System.out.println();
  }
}

class ArrayStack {
  int[] ls;
  int top;

  public ArrayStack(int initCapacity) {
    ls = new int[initCapacity];
    top = 0;
  }

  public int size() {
    return top;
  }

  public void push(int item) throws StackOverflowError {
    if (top >= ls.length) {
      throw new StackOverflowError();
    }
    ls[top] = item;
    top++;
  }

  public int pop() throws EmptyStackException {
    if (top <= 0) {
      throw new EmptyStackException();
    }
    top--;
    int ret = ls[top];
    ls[top] = 0;
    return ret;
  }

  public int[] getArray() {
    return ls;
  }
}

class Solver2661 {
  static final int MAX = 3;

  public static int[] solve(int n) {
    var stack = new ArrayStack(n);
    int k = 1;
    while (stack.size() < n) {
      stack.push(k);
      if (isGoodSequence(stack.getArray(), stack.size())) {
        k = 1;
      } else {
        stack.pop();
        k++;
      }
      if (k > MAX) {
        k = stack.pop();
        k++;
      }
    }
    return stack.getArray();
  }

  /**
   * 부분수열이 좋은수열인지 검증한다.
   * 
   * @param ls  길이 n짜리 수열
   * @param len 현재까지 쌓아올린 수열의 길이
   */
  public static boolean isGoodSequence(final int[] ls, int len) {
    if (len <= 1)
      return true;
    // 어차피 N/2번만 반복 수행하면 된다.
    for (int sampleLength = 2; sampleLength <= len; sampleLength += 2) {
      int bot = len - sampleLength;
      int mid = bot + (sampleLength / 2);
      boolean isSame = true;
      // check same sequence
      for (int i = 0; i < (sampleLength / 2); ++i) {
        if (ls[bot + i] != ls[mid + i]) {
          isSame = false;
          break;
        }
      }
      if (isSame) {
        return false;
      }
    }
    return isGoodSequence(ls, len - 1);
  }
}
