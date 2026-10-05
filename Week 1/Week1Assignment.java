import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Solutions for the five Week 1 assignment problems. */
public class Week1Assignment {
    public static String checkDuplicateSeats(int[] seatNumbers) {
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < seatNumbers.length; i++) {
            boolean appearedEarlier = false;
            for (int previous = 0; previous < i; previous++) {
                if (seatNumbers[previous] == seatNumbers[i]) {
                    appearedEarlier = true;
                    break;
                }
            }
            if (!appearedEarlier) {
                for (int next = i + 1; next < seatNumbers.length; next++) {
                    if (seatNumbers[i] == seatNumbers[next]) {
                        duplicates.add(seatNumbers[i]);
                        break;
                    }
                }
            }
        }
        if (duplicates.isEmpty()) {
            return "No Duplicate Seats Found";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < duplicates.size(); i++) {
            if (i > 0) {
                result.append(" | ");
            }
            result.append("Duplicate Seat Number Found: ").append(duplicates.get(i));
        }
        return result.toString();
    }

    public static String checkTypingAccuracy(String original, String typed) {
        int totalPositions = Math.max(original.length(), typed.length());
        if (totalPositions == 0) {
            return "Matched: 0/0 | Accuracy: 100.00% | No Mismatches";
        }
        int matched = 0;
        int firstMismatch = -1;
        char originalAtMismatch = '\0';
        char typedAtMismatch = '\0';
        for (int i = 0; i < totalPositions; i++) {
            boolean bothHaveCharacter = i < original.length() && i < typed.length();
            if (bothHaveCharacter && original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatch < 0) {
                firstMismatch = i;
                if (i < original.length()) {
                    originalAtMismatch = original.charAt(i);
                }
                if (i < typed.length()) {
                    typedAtMismatch = typed.charAt(i);
                }
            }
        }
        double accuracy = matched * 100.0 / totalPositions;
        if (firstMismatch < 0) {
            return String.format("Matched: %d/%d | Accuracy: %.2f%% | No Mismatches",
                    matched, totalPositions, accuracy);
        }
        String originalDisplay = firstMismatch < original.length()
                ? "'" + originalAtMismatch + "'" : "<end>";
        String typedDisplay = firstMismatch < typed.length()
                ? "'" + typedAtMismatch + "'" : "<end>";
        return String.format("Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d (%s vs %s)",
                matched, totalPositions, accuracy, firstMismatch + 1,
                originalDisplay, typedDisplay);
    }

    public static String findLongestStreak(String signalLog) {
        if (signalLog.isEmpty()) {
            return "No signal readings";
        }
        char longestColor = signalLog.charAt(0);
        int longestLength = 1;
        char currentColor = signalLog.charAt(0);
        int currentLength = 1;
        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentColor) {
                currentLength++;
            } else {
                currentColor = signalLog.charAt(i);
                currentLength = 1;
            }
            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestColor = currentColor;
            }
        }
        return "Longest Streak: '" + longestColor + "' repeated " + longestLength + " times";
    }

    public static String analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA.length != sectionB.length || sectionA.length == 0) {
            throw new IllegalArgumentException("Sections must have equal, non-empty arrays.");
        }
        long totalA = 0;
        long totalB = 0;
        int highest = Integer.MIN_VALUE;
        String highestSection = "Section A";
        int highestIndex = 0;
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            totalB += sectionB[i];
            if (sectionA[i] > highest) {
                highest = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }
            if (sectionB[i] > highest) {
                highest = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }
        String status = totalA == totalB ? "Balanced" : "Not Balanced";
        return "Section A Total: " + totalA + " | Section B Total: " + totalB
                + " | Status: " + status + " | Highest Quantity: " + highest
                + " (" + highestSection + ", Item " + (highestIndex + 1) + ")";
    }

    public static String classifyWordLengths(String review) {
        int shortWords = 0;
        int mediumWords = 0;
        int longWords = 0;
        String trimmed = review.trim();
        if (!trimmed.isEmpty()) {
            for (String word : trimmed.split("\\s+")) {
                int length = word.length();
                if (length <= 4) {
                    shortWords++;
                } else if (length <= 8) {
                    mediumWords++;
                } else {
                    longWords++;
                }
            }
        }
        return "Short: " + shortWords + " | Medium: " + mediumWords + " | Long: " + longWords;
    }

    public static void main(String[] args) {
        System.out.println("1. Exam Hall Seat Duplication Checker");
        System.out.println(checkDuplicateSeats(new int[] {101, 102, 103, 102, 105}));
        System.out.println(checkDuplicateSeats(new int[] {101, 102, 103, 104, 105}));
        System.out.println(checkDuplicateSeats(new int[] {1, 1, 2, 2, 2}));

        System.out.println("\n2. Typing Speed Test Accuracy Checker");
        System.out.println(checkTypingAccuracy("hello world", "hello worlt"));
        System.out.println(checkTypingAccuracy("coding", "coding"));

        System.out.println("\n3. Traffic Signal Streak Analyzer");
        System.out.println(findLongestStreak("RRGGGYRR"));
        System.out.println(findLongestStreak("RRRRYYGG"));

        System.out.println("\n4. Warehouse Inventory Balancer");
        System.out.println(analyzeInventory(new int[] {20, 15, 30}, new int[] {25, 10, 30}));

        System.out.println("\n5. Movie Review Word Length Profiler");
        System.out.println(classifyWordLengths("This movie was absolutely fantastic and thrilling"));

        boolean checksPass = checkDuplicateSeats(new int[] {7, 7, 7, 8, 8}).equals(
                        "Duplicate Seat Number Found: 7 | Duplicate Seat Number Found: 8")
                && checkTypingAccuracy("", "").contains("100.00%")
                && findLongestStreak("").equals("No signal readings")
                && analyzeInventory(new int[] {-3}, new int[] {-4}).contains("Not Balanced")
                && classifyWordLengths("").equals("Short: 0 | Medium: 0 | Long: 0");
        if (!checksPass) {
            throw new AssertionError("A Week 1 assignment edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }
}
