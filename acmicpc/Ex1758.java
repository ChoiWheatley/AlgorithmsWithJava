package acmicpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ex1758 {
    public static void main(String[] args) {
        try (var br = new BufferedReader(new InputStreamReader(System.in));
                var bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String line = br.readLine();
            int t = Integer.parseInt(line);
            List<Long> tips = new ArrayList<>(t);

            while (t-- > 0) {
                tips.add(Long.parseLong(br.readLine()));
            }

            long submit = Solution.solution(tips);
            bw.write(submit + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Solution {

        public static long solution(List<Long> tips) {

            Collections.sort(tips, Comparator.reverseOrder());
            return sum(tips);
        }

        public static long sum(List<Long> tips) {

            long sum = 0;
            for (int idx = 0; idx < tips.size(); ++idx) {
                if (tips.get(idx) > idx) {
                    sum += tips.get(idx) - idx;
                }
            }
            return sum;
        }
    }
}
