package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import useful.Pair;

public class Ex2170 {
    public static void main(String[] args) {

        Solution solution = new Solution();

        try (var br = new BufferedReader(new InputStreamReader(System.in));
                var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String line = br.readLine();
            int t = Integer.parseInt(line);

            while (t-- > 0) {
                line = br.readLine();
                String[] splitted = line.split(" ");
                int start = Integer.parseInt(splitted[0]);
                int end = Integer.parseInt(splitted[1]);

                solution.add(Line.of(start, end));
            }

            Collection<Line> lines = solution.getLines();
            int sum = lines.stream().mapToInt(Line::length).sum();
            bw.write(sum + "\n");

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
