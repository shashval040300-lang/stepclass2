import java.util.Arrays;

public class Week5Practice {
    static void curveScores(int[] scores, int bonus) {
        for (int i = 0; i < scores.length; i++) scores[i] += bonus;
    }

    static String findDuplicateTeam(String[] teamNames) {
        for (int i = 0; i < teamNames.length; i++)
            for (int j = i + 1; j < teamNames.length; j++)
                if (teamNames[i].equals(teamNames[j])) return "Duplicate Found: " + teamNames[i];
        return "No Duplicates Found";
    }

    static int[] findTopThreeScores(int[] scores) {
        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE, third = Integer.MIN_VALUE;
        for (int score : scores) {
            if (score >= first) { third = second; second = first; first = score; }
            else if (score >= second) { third = second; second = score; }
            else if (score > third) third = score;
        }
        if (scores.length < 3) throw new IllegalArgumentException("At least three scores are required.");
        return new int[] {first, second, third};
    }

    private static double rowAverage(int[] row) {
        int total = 0;
        for (int score : row) total += score;
        return (double) total / row.length;
    }

    static String classifyRows(int[][] seatingScores, int threshold) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < seatingScores.length; i++) {
            if (i > 0) result.append(" | ");
            result.append("Row ").append(i).append(": ")
                  .append(rowAverage(seatingScores[i]) < threshold ? "Quiet Zone" : "Buzzing Zone");
        }
        return result.toString();
    }

    static class Candidate implements Comparable<Candidate> {
        private final String name;
        private final double cgpa;
        private final int codingScore;
        Candidate(String name, double cgpa, int codingScore) {
            this.name = name; this.cgpa = cgpa; this.codingScore = codingScore;
        }
        static boolean isEligible(double cgpa) { return cgpa >= 7.5; }
        static boolean isEligible(double cgpa, int codingScore) { return cgpa >= 6.5 && codingScore >= 60; }
        private double compositeScore() { return cgpa * 10 + codingScore * 0.5; }
        @Override public int compareTo(Candidate other) { return Double.compare(other.compositeScore(), compositeScore()); }
    }

    static String shortlistAndRank(Candidate[] candidates) {
        Candidate[] selected = new Candidate[candidates.length];
        int count = 0;
        for (Candidate c : candidates)
            if (Candidate.isEligible(c.cgpa) || Candidate.isEligible(c.cgpa, c.codingScore)) selected[count++] = c;
        selected = Arrays.copyOf(selected, count);
        Arrays.sort(selected);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < selected.length; i++) {
            if (i > 0) result.append(" | ");
            result.append(i + 1).append(". ").append(selected[i].name)
                  .append(" (").append(String.format("%.1f", selected[i].compositeScore())).append(")");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        int[] scores = {70, 85, 60}; curveScores(scores, 10);
        System.out.println("Curved scores: " + Arrays.toString(scores));
        System.out.println(findDuplicateTeam(new String[]{"ByteForce", "CodeCrafters", "ByteForce"}));
        System.out.println("Top three: " + Arrays.toString(findTopThreeScores(new int[]{45, 82, 79, 90, 33, 90, 61})));
        System.out.println(classifyRows(new int[][]{{40, 50, 45}, {85, 90, 95}, {30, 20, 25}}, 60));
        Candidate[] candidates = {new Candidate("Aisha", 8.2, 40), new Candidate("Rohit", 6.8, 65),
                new Candidate("Meena", 6.0, 90), new Candidate("Karan", 7.5, 20)};
        System.out.println(shortlistAndRank(candidates));
    }
}
