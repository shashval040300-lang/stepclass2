import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Solutions for the five Week 2 assignment problems. */
public class Week2Assignment {
    public static String checkPinLength(String pin) {
        return pin.length() == 4 ? "PIN length OK." : "Invalid PIN - must be exactly 4 digits.";
    }

    public static String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ", -1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(' ');
            }
            result.append(new StringBuilder(words[i]).reverse());
        }
        return result.toString();
    }

    public static String parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            return "Invalid Record";
        }
        return "Product: " + fields[0].trim() + " | SKU: " + fields[1].trim()
                + " | Qty: " + fields[2].trim();
    }

    public static String normalizeCode(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: code must be exactly 13 characters";
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }
        for (int i = 3; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: code body must contain only digits";
            }
        }
        StringBuilder formatted = new StringBuilder("[")
                .append(code, 0, 3).append("] YEAR: ")
                .append(code, 3, 7).append(" | CATALOG: ")
                .append(code, 7, 13);
        return formatted.toString();
    }

    public static String printFilteredWordFrequency(String feedback) {
        Set<String> stopWords = new HashSet<>(Arrays.asList("the", "was", "and", "a", "is", "of", "in"));
        String cleaned = feedback.toLowerCase().replace(".", " ").replace(",", " ").trim();
        Map<String, Integer> frequencies = new HashMap<>();
        if (!cleaned.isEmpty()) {
            for (String word : cleaned.split("\\s+")) {
                if (!word.isEmpty() && !stopWords.contains(word)) {
                    frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
                }
            }
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencies.entrySet());
        entries.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed().thenComparing(Map.Entry::getKey));
        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, Integer> entry : entries) {
            if (report.length() > 0) {
                report.append(" | ");
            }
            report.append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return report.toString();
    }

    public static void main(String[] args) {
        System.out.println("1. ATM PIN Length Validator");
        System.out.println("482 -> " + checkPinLength("482"));
        System.out.println("4820 -> " + checkPinLength("4820"));

        System.out.println("\n2. Word Reversal Encoder");
        System.out.println(reverseEachWord("hello club"));

        System.out.println("\n3. Product Inventory CSV Parser");
        System.out.println(parseInventoryRecord("Wireless Mouse,WM-2201,150"));
        System.out.println(parseInventoryRecord("Wireless Mouse,150"));

        System.out.println("\n4. Library ISBN Normalizer & Validator");
        System.out.println(validateAndFormat(normalizeCode("  pen2026004251  ")));
        System.out.println(validateAndFormat(normalizeCode("12N2026004251")));

        System.out.println("\n5. Stop-Word-Filtered Word Frequency Report");
        System.out.println(printFilteredWordFrequency(
                "The mentor was great, the session was great and clear."));

        boolean checksPass = checkPinLength("a123").equals("PIN length OK.")
                && reverseEachWord("one  two").equals("eno  owt")
                && parseInventoryRecord("a,b,").endsWith("Qty: ")
                && validateAndFormat("PEN2026004251").equals("[PEN] YEAR: 2026 | CATALOG: 004251")
                && validateAndFormat("PEN2026X04251").contains("body must contain only digits")
                && printFilteredWordFrequency("was the and").isEmpty();
        if (!checksPass) {
            throw new AssertionError("A Week 2 assignment edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }
}
