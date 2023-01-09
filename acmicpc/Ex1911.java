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
        List<Hole> holes = new ArrayList<>(n);
        while (n-- > 0) {
            line = reader.readLine().split(" ");
            int start = Integer.valueOf(line[0]);
            int end = Integer.valueOf(line[1]);
            holes.add(new Hole(start, end));
        }

        int submit = Solution.solution(holes, l);
        System.out.println(submit);

        reader.close();
    }

    public static class Solution {
        public static int solution(List<Hole> ls, int plankLen) {
            Collections.sort(ls);
            // 첫번째 구멍의 위치를 0번으로 전부 바꾼다.
            int offset = ls.get(0).start;
            ls = ls.stream().map((before) -> {
                return new Hole(before.start - offset, before.end - offset);
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
            Hole lastHole = ls.get(ls.size() - 1);
            if (start <= lastHole.start && lastHole.end <= end) {
                result++;
            }
            return result;
        }
    }

    public static class Hole implements Comparable<Hole> {

        public final int start;
        public final int end;

        public Hole(int startInclusive, int endExclusive) {
            this.start = startInclusive;
            this.end = endExclusive;
        }

        @Override
        public int compareTo(Hole o) {
            return this.start - o.start;
        }
    }
}
