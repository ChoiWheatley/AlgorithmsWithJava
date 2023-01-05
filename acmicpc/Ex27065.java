package acmicpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Ex27065 {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.valueOf(reader.readLine());

        while (t-- > 0) {
            int n = Integer.valueOf(reader.readLine());
            System.out.println(Solution.solution(n) ? "Good Bye" : "BOJ 2022");
        }

        reader.close();
    }

    public static class Solution {
        /** 아름다운수: n은 과잉수면서 n의 약수들은 모두 과잉수가 아니다. */
        public static boolean solution(int n) {
            Set<Integer> divisors = getDivisors(n);
            if (isExcessive(n) && divisors.stream().filter(e -> e != n).allMatch(e -> !isExcessive(e))) {
                return true;
            }
            return false;
        }

        /**
         * 과잉수: 자기를 제외한 약수의 합이 자기보다 큰 수
         */
        public static boolean isExcessive(int n) {
            return getDivisors(n).stream().mapToInt(Integer::valueOf).sum() - n > n;
        }

        public static Set<Integer> getDivisors(int n) {
            if (n < 1) {
                return Set.of();
            }
            Set<Integer> ret = new HashSet<>((int) Math.sqrt(n));
            ret.add(1);
            for (int i = 2; i < (int) (Math.sqrt(n)) + 1; ++i) {
                if (n % i == 0) {
                    ret.add(i);
                    ret.add(n / i); //
                }
            }
            ret.add(n);
            return ret;
        }
    }
}
