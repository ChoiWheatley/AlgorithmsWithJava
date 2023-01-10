package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Ex1911 {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        String[] line = reader.readLine().split(" ");
        int n = Integer.valueOf(line[0]);
        int l = Integer.valueOf(line[1]);
        List<Range> holes = new ArrayList<>(n);
        while (n-- > 0) {
            line = reader.readLine().split(" ");
            int start = Integer.valueOf(line[0]);
            int end = Integer.valueOf(line[1]);
            holes.add(new Range(start, end));
        }

        int submit = Solution2.solution(holes, l);
        System.out.println(submit);

        reader.close();
    }

    public static class Solution {
        public static int solution(List<Range> ls, int plankLen) {
            Collections.sort(ls);
            // 첫번째 구멍의 위치를 0번으로 전부 바꾼다.
            int offset = ls.get(0).start;
            ls = ls.stream().map((before) -> {
                return new Range(before.start - offset, before.end - offset);
            }).collect(Collectors.toList());

            int result = 0;
            int start = 0;
            int end = ls.get(0).end;
            {
                // first case
                int len = end - start;
                if (len >= plankLen) {
                    int countOfPlanks = len / plankLen;
                    result += countOfPlanks;
                    start += plankLen * countOfPlanks;
                }
            }
            for (int idx = 1; idx < ls.size(); ++idx) {
                if (start >= ls.get(idx - 1).end) {
                    start = ls.get(idx).start;
                } else {
                    end = ls.get(idx).start;
                    int len = end - start + 1;
                    if (len >= plankLen) {
                        // 중간값의 정리에 의해 plankLen을 지나친다.
                        result += 1;
                        start = Integer.max(
                                ls.get(idx).start,
                                start + plankLen);
                    }
                }

                end = ls.get(idx).end;
                int len = end - start;
                if (len >= plankLen) {
                    int countOfPlanks = len / plankLen;
                    result += countOfPlanks;
                    start += (plankLen * countOfPlanks);
                }
            }
            // Exceptional case: plankLen이 지나치게 커서 카운트를 세지 않고 넘어간 경우
            Range lastHole = ls.get(ls.size() - 1);
            if (start <= lastHole.start && lastHole.end <= end) {
                result++;
            }
            return result;
        }
    }

    public static class Solution2 {
        /**
         * 조건: 판자 pI..<pJ를 가지고 구멍 hI..<hJ를 막을 수 있나요?
         * 증가하는 경우: 위의 조건이 TRUE가 될 때까지
         */
        public static int solution(List<Range> holes, int plankLen) {
            Collections.sort(holes);
            final int SIZE = holes.size();
            final int BEGIN = holes.get(0).start;
            final int LAST = holes.get(SIZE - 1).end;

            int result = 0;

            int idx = 0;
            for (int pos = BEGIN; pos < LAST && idx < SIZE;) {
                Range hole = holes.get(idx);
                Range plank = Range.of(hole.start, hole.start);
                do {
                    if (plank.end < hole.end) {
                        // plank를 더 붙여야 하는 경우
                        int count = (int) Math.ceil((hole.end - plank.end) / (double) plankLen);
                        int len = count * plankLen;
                        result += count;
                        plank = Range.of(plank.start, plank.end + len);
                    }
                    if (idx < SIZE - 1) {
                        idx += 1;
                        Range nextHole = holes.get(idx);
                        if (nextHole.start <= plank.end) {
                            // hole의 크기를 늘려야 하는 경우
                            hole = Range.merge(hole, nextHole);
                        }
                    }
                } while (!canCover(hole, plank));
                // YES! plank can cover the hole
                pos = plank.end;
            }

            return result;
        }

        /**
         * 판자 pI..<pJ를 가지고 구멍 hI..<hJ를 막을 수 있나요?
         * 
         * @assertion subList는 정렬된 상태라 가정한다.
         */
        public static boolean canCover(Range hole, Range plank) {
            return plank.start <= hole.start && hole.end <= plank.end;
        }
    }

    public static class Solution3 {
        public static int solution(List<Range> holes, int plankLen) {
            Collections.sort(holes);

            int result = 0;
            int range = 0; // 판자의 범위
            for (int idx = 0; idx < holes.size(); ++idx) {
                Range hole = holes.get(idx);
                if (hole.start > range) {
                    range = hole.start;
                }
                if (hole.end >= range) {
                    // 판자를 더 붙여야 한다.
                    while (hole.end > range) {
                        range += plankLen;
                        result += 1;
                    }
                }
            }
            return result;
        }
    }

    public static class Range implements Comparable<Range> {

        public final int start;
        public final int end;

        public Range(int startInclusive, int endExclusive) {
            this.start = startInclusive;
            this.end = endExclusive;
        }

        public static Range of(int startInclusive, int endExclusive) {
            return new Range(startInclusive, endExclusive);
        }

        @Override
        public int compareTo(Range o) {
            if (this.start == o.start) {
                return this.end - o.end;
            }
            return this.start - o.start;
        }

        public int len() {
            return end - start;
        }

        public static Range merge(Range a, Range b) {
            return Range.of(Integer.min(a.start, b.start), Integer.max(a.end, b.end));
        }
    }

}
