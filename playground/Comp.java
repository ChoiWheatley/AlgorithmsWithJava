package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import useful.Pair;

import org.junit.Test;

/**
 * Comp
 */
public class Comp {
    static final int MAX = 10_000_000;

    @Test
    public void comparing1() {
        Random r = new Random();
        int[][] arr = new int[MAX][2];
        for (int i = 0; i < MAX; ++i) {
            arr[i][0] = r.nextInt();
            arr[i][1] = r.nextInt();
        }
        Arrays.sort(arr, Comparator.comparing(i -> i[0]));
    }

    @Test
    public void comparing2() {
        Random r = new Random();
        int[][] arr = new int[MAX][2];
        for (int i = 0; i < MAX; ++i) {
            arr[i][0] = r.nextInt();
            arr[i][1] = r.nextInt();
        }
        Arrays.sort(arr, new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }

        });
    }

    static class Node {
        final int a, b;

        public Node(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    @Test
    public void comparing3() {
        Random r = new Random();
        List<Node> lines = new ArrayList<>(Comp.MAX);
        r.ints().limit((long) MAX * 2).reduce((a, b) -> {
            lines.add(new Node(a, b));
            return b;
        });

        Collections.sort(lines, Comparator.comparing(o1 -> o1.a));
    }

    @Test
    public void comparing4() {
        Random r = new Random();
        List<Node> lines = new ArrayList<>(Comp.MAX);
        r.ints().limit((long) MAX * 2).reduce((a, b) -> {
            lines.add(new Node(a, b));
            return b;
        });

        Collections.sort(lines, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.a, o2.a);
            }
        });
    }

    static class NodeWithComparable extends Node implements Comparable<NodeWithComparable> {

        public NodeWithComparable(int a, int b) {
            super(a, b);
        }

        @Override
        public int compareTo(NodeWithComparable o) {
            return Integer.compare(this.a, o.a);
        }

    }

    @Test
    public void comparing5() {

    }

}