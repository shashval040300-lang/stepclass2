import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/** Solutions for the five Week 1 live-coding problems. */
public class Week1Practice {
    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};

    public static String playRound(String playerMove, String computerMove) {
        String player = normalizeMove(playerMove);
        String computer = normalizeMove(computerMove);
        if (player == null || computer == null) {
            return "Invalid Move";
        }
        if (player.equals(computer)) {
            return "Draw";
        }
        boolean playerWins = (player.equals("Rock") && computer.equals("Scissors"))
                || (player.equals("Paper") && computer.equals("Rock"))
                || (player.equals("Scissors") && computer.equals("Paper"));
        return playerWins ? "Player Wins" : "Computer Wins";
    }

    private static String normalizeMove(String move) {
        if (move == null) {
            return null;
        }
        for (String validMove : MOVES) {
            if (validMove.equalsIgnoreCase(move.trim())) {
                return validMove;
            }
        }
        return null;
    }

    private static String randomMove(Random random) {
        return MOVES[random.nextInt(MOVES.length)];
    }

    public static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        return isPalindromeRecursive(text, 0, text.length() - 1);
    }

    private static boolean isPalindromeRecursive(String text, int left, int right) {
        if (left >= right) {
            return true;
        }
        return text.charAt(left) == text.charAt(right)
                && isPalindromeRecursive(text, left + 1, right - 1);
    }

    public static boolean isPalindromeArrayReversal(String text) {
        char[] reversed = text.toCharArray();
        for (int left = 0, right = reversed.length - 1; left < right; left++, right--) {
            char temp = reversed[left];
            reversed[left] = reversed[right];
            reversed[right] = temp;
        }
        return text.equals(new String(reversed));
    }

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Normal";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obese";
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        if (heights.length != weights.length) {
            throw new IllegalArgumentException("Heights and weights must have the same length.");
        }
        System.out.printf(Locale.US, "%-8s %-12s %-12s %-10s %s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        for (int i = 0; i < heights.length; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            System.out.printf(Locale.US, "%-8d %-12.2f %-12.2f %-10.2f %s%n",
                    i + 1, heights[i], weights[i], bmi, getBmiStatus(bmi));
        }
    }

    public static Character findFirstNonRepeatingChar(String text) {
        int[] frequencies = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < text.length(); i++) {
            frequencies[text.charAt(i)]++;
        }
        for (int i = 0; i < text.length(); i++) {
            if (frequencies[text.charAt(i)] == 1) {
                return text.charAt(i);
            }
        }
        return null;
    }

    public static String reverseCustomerName(String customerName) {
        char[] characters = customerName.toCharArray();
        for (int left = 0, right = characters.length - 1; left < right; left++, right--) {
            char temp = characters[left];
            characters[left] = characters[right];
            characters[right] = temp;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        System.out.println("1. Rock-Paper-Scissors Game (5-round seeded demo)");
        String[] playerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        Random random = new Random(42L);
        int wins = 0;
        int losses = 0;
        int draws = 0;
        System.out.println("Round | Player Move | Computer Move | Result");
        for (int i = 0; i < playerMoves.length; i++) {
            String computerMove = randomMove(random);
            String result = playRound(playerMoves[i], computerMove);
            System.out.printf("%5d | %-11s | %-13s | %s%n",
                    i + 1, playerMoves[i], computerMove, result);
            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }
        }
        double winPercentage = playerMoves.length == 0 ? 0.0
                : wins * 100.0 / playerMoves.length;
        System.out.printf(Locale.US, "Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPercentage);

        System.out.println("\n2. Palindrome Checker (3 Approaches)");
        for (String text : new String[] {"madam", "hello"}) {
            System.out.printf("%s -> Iterative: %s | Recursive: %s | Array Reversal: %s%n",
                    text, palindromeResult(isPalindromeIterative(text)),
                    palindromeResult(isPalindromeRecursive(text)),
                    palindromeResult(isPalindromeArrayReversal(text)));
        }

        System.out.println("\n3. BMI Calculator for a Team");
        printWellnessReport(new double[] {1.75, 1.60}, new double[] {70, 90});

        System.out.println("\n4. First Non-Repeating Character");
        printFirstUnique("swiss");
        printFirstUnique("aabbcc");

        System.out.println("\n5. Reverse Customer Name");
        String name = "Sunil";
        System.out.println("Original Name: " + name);
        System.out.println("Reversed Name: " + reverseCustomerName(name));

        boolean checksPass = playRound("Rock", "Scissors").equals("Player Wins")
                && playRound("Paper", "Paper").equals("Draw")
                && isPalindromeIterative("") && isPalindromeRecursive("a")
                && isPalindromeArrayReversal("abba")
                && Math.abs(70.0 / (1.75 * 1.75) - 22.857142857) < 0.001
                && getBmiStatus(18.5).equals("Normal")
                && findFirstNonRepeatingChar("aabbcc") == null
                && reverseCustomerName("").isEmpty();
        if (!checksPass) {
            throw new AssertionError("A Week 1 practice edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }

    private static String palindromeResult(boolean isPalindrome) {
        return isPalindrome ? "Palindrome" : "Not Palindrome";
    }

    private static void printFirstUnique(String text) {
        Character result = findFirstNonRepeatingChar(text);
        if (result == null) {
            System.out.println("\"" + text + "\" -> No Non-Repeating Character Found");
        } else {
            System.out.println("\"" + text + "\" -> First Non-Repeating Character: '" + result + "'");
        }
    }
}
