package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import useful.Pair;

public class Ex2170 {
    public static void main(String[] args) {

        try (var br = new BufferedReader(new InputStreamReader(System.in));
                var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String line = br.readLine();
            int t = Integer.parseInt(line);

            /** Solution1 방식 */
            // Solution solution = new Solution();
            // while (t-- > 0) {
            // line = br.readLine();
            // String[] splitted = line.split(" ");
            // int start = Integer.parseInt(splitted[0]);
            // int end = Integer.parseInt(splitted[1]);

            // solution.add(Line.of(start, end));
            // }

            // Collection<Line> lines = solution.getLines();
            // int sum = lines.stream().mapToInt(Line::length).sum();
            // bw.write(sum + "\n");

            /** Solution2 방식 */
            List<Line> lines = new ArrayList<>(t);
            while (t-- > 0) {

                line = br.readLine();
                String[] splitted = line.split(" ");
                int start = Integer.parseInt(splitted[0]);
                int end = Integer.parseInt(splitted[1]);

                lines.add(Line.of(start, end));
            }
            int submit = Solution2.solution(lines);
            bw.write(submit + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Solution {
        private TreeMap<Integer, Line> starters;
        private TreeMap<Integer, Line> enders;

        public Solution() {
            starters = new TreeMap<>();
            enders = new TreeMap<>();
        }

        /** bulk loading */
        public Solution(Collection<Line> lines) {
            starters = new TreeMap<>();
            enders = new TreeMap<>();
            lines.forEach(l -> Solution.add(starters, enders, l));
        }

        public void add(Line line) {
            Solution.add(starters, enders, line);
        }

        /**
         * Select line From lines where (e.end > other.start) And (e.start < other.end)
         */
        public Collection<Line> findRelevantLines(Line other) {
            return Solution.findRelevantLines(starters, enders, other);
        }

        public Collection<Line> getLines() {
            return starters.values();
        }

        private static void add(TreeMap<Integer, Line> startersOut, TreeMap<Integer, Line> endersOut, Line line) {

            if (startersOut.size() != endersOut.size()) {
                throw new RuntimeException("two identical map (starters, enders) are different");
            }

            Collection<Line> originals = findRelevantLines(startersOut, endersOut, line);
            if (originals.isEmpty()) {

                startersOut.put(line.start(), line);
                endersOut.put(line.end(), line);
            } else {

                // find min, max and squash into one long line
                int min = originals.stream().min((a, b) -> a.start() - b.start()).orElseThrow().start();
                int max = originals.stream().max((a, b) -> a.end() - b.end()).orElseThrow().end();
                min = Integer.min(min, line.start());
                max = Integer.max(max, line.end());
                Line newLongLine = Line.of(min, max);

                // remove and replace
                originals.forEach(e -> {
                    startersOut.remove(e.start());
                    endersOut.remove(e.end());
                });
                startersOut.put(newLongLine.start(), newLongLine);
                endersOut.put(newLongLine.end(), newLongLine);
            }
        }

        private static Collection<Line> findRelevantLines(TreeMap<Integer, Line> startersOut,
                TreeMap<Integer, Line> endersOut, Line other) {

            Collection<Line> s = startersOut.headMap(other.end()).values();
            Collection<Line> e = endersOut.tailMap(other.start()).values();
            Set<Line> result = new TreeSet<>();
            result.addAll(s);
            result.retainAll(e);
            return result;
        }
    }

    /**
     * <h1>sweeping algorithm 사용한 풀이법</h1>
     * 
     * https://coder-in-war.tistory.com/entry/%EA%B0%9C%EB%85%90-47-%EC%8A%A4%EC%9C%84%ED%95%91-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98Sweeping-Algorithm
     */
    public static class Solution2 {
        public static int solution(List<Line> lines) {

            Collections.sort(lines); // 수평선을 한 방향으로만 훑을 수 있게
            int result = 0;
            int l = Integer.MIN_VALUE; // l, r : range of interest
            int r = Integer.MIN_VALUE;
            for (Line e : lines) {

                if (r < e.start()) { // 병합 불가 조건

                    result += r - l; // 우리는 길이만을 원한다.
                    l = e.start(); // 현재 구간을 초기화, 오로지 우리는 오른쪽으로만 간다.
                    r = e.end();
                } else { // 병합 가능 조건
                    r = Integer.max(r, e.end());
                }
            }
            result += r - l; // last case

            return result;
        }
    }

    /** 값 객체 */
    public static class Line extends Pair<Integer, Integer> {

        private Line(Integer a, Integer b) {
            super(a, b);
        }

        public static Line of(int start, int end) {
            if (start > end) {
                return new Line(end, start);
            }
            return new Line(start, end);
        }

        public int start() {
            return first;
        }

        public int end() {
            return second;
        }

        public int length() {
            return end() - start();
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }
    }
}
