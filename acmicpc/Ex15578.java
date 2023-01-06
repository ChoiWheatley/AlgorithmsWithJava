package acmicpc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ex15578 {
    public static void main(String[] args) throws IOException {
        var reader = new Scanner(System.in);
        var writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = reader.nextInt();
        int K = reader.nextInt();
        int M = reader.nextInt();

        List<Integer> submit = Solution.solution(N, K, M);
        for (int i = 0; i < N; ++i) {
            writer.write(submit.get(i) + " ");
        }
        writer.write("\n");

        reader.close();
        writer.flush();
        writer.close();
    }

    public static class Solution {
        /**
         * @param K 한 번에 팀에 넣을 학생수
         */
        public static List<Integer> solution(int teamLength, int K, int studentLength) {
            assert 2 <= teamLength && teamLength <= 200_000;
            assert 1 <= K && K <= studentLength && studentLength <= 2_000_000_000;
            List<Integer> ret = new ArrayList<>(teamLength);
            for (int i = 0; i < teamLength; ++i) {
                ret.add(0);
            }

            int divisor = studentLength / K;
            int cycle = teamLength * 2 - 2;
            int numberOfCycle = divisor / cycle;

            ret.set(0, K * numberOfCycle);
            ret.set(ret.size() - 1, K * numberOfCycle);
            for (int idx = 1; idx < ret.size() - 1; ++idx) {
                ret.set(idx, 2 * K * numberOfCycle);
            }

            // manually iterate
            int remainStudents = studentLength - (K * cycle * numberOfCycle);
            int idx = 0;
            int delta = 1;
            for (; remainStudents >= K; remainStudents -= K) {
                ret.set(idx, ret.get(idx) + K);
                idx += delta;
                if (idx <= 0 || idx >= ret.size() - 1) {
                    delta *= -1;
                }
            }
            ret.set(idx, ret.get(idx) + remainStudents);

            return ret;
        }
    }
}
