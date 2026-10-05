import java.util.Locale;

/** Solutions for the five Week 2 live-coding problems. */
public class Week2Practice {
    public static String countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;
        for (int i = 0; i < text.length(); i++) {
            char character = Character.toLowerCase(text.charAt(i));
            if (character >= 'a' && character <= 'z') {
                if ("aeiou".indexOf(character) >= 0) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        return "Vowels: " + vowels + " | Consonants: " + consonants;
    }

    public static String parseStudentRecord(String csvLine) {
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            return "Invalid Record";
        }
        return "Name: " + fields[0].trim() + " | Roll No: " + fields[1].trim()
                + " | Dept: " + fields[2].trim();
    }

    public static String validateFileExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) {
            return "Rejected - invalid file type";
        }
        String extension = filename.substring(dot + 1);
        if (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("docx")
                || extension.equalsIgnoreCase("zip")) {
            return "Accepted";
        }
        return "Rejected - invalid file type";
    }

    public static String maskPhoneNumber(String phone) {
        if (phone.length() != 10) {
            return "Invalid phone number";
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }
        StringBuilder masked = new StringBuilder("XXXXXX").append(phone.substring(6));
        masked.insert(6, '-');
        return masked.toString();
    }

    public static String normalizeReference(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase(Locale.ROOT);
        }
        return trimmed.substring(0, 3).toUpperCase(Locale.ROOT) + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: reference must be exactly 14 characters";
        }
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }
        for (int i = 3; i < reference.length(); i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: reference body must contain only digits";
            }
        }
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9);
        StringBuilder formatted = new StringBuilder("[")
                .append(reference, 0, 3).append("] DATE: ")
                .append(date, 0, 2).append('/')
                .append(date, 2, 4).append('/')
                .append(date, 4, 6).append(" | SEQ: ")
                .append(sequence);
        return formatted.toString();
    }

    public static void main(String[] args) {
        System.out.println("1. Vowel & Consonant Counter");
        System.out.println(countVowelsAndConsonants("Java Programming"));

        System.out.println("\n2. CSV Student Record Parser");
        System.out.println(parseStudentRecord("Ananya Verma,RA2211003010123,CSE"));
        System.out.println(parseStudentRecord("Ananya Verma,CSE"));

        System.out.println("\n3. File Extension Validator");
        System.out.println("Assignment1.PDF -> " + validateFileExtension("Assignment1.PDF"));
        System.out.println("notes.txt -> " + validateFileExtension("notes.txt"));

        System.out.println("\n4. Masked Phone Number Formatter");
        System.out.println(maskPhoneNumber("9876543210"));
        System.out.println(maskPhoneNumber("98765"));

        System.out.println("\n5. Bank Transaction Reference Generator & Validator");
        String normalized = normalizeReference("  hdf03022600042  ");
        System.out.println(validateAndFormat(normalized));
        System.out.println(validateAndFormat(normalizeReference("12F03022600042")));

        boolean checksPass = countVowelsAndConsonants("AEIOU xyz").equals("Vowels: 5 | Consonants: 3")
                && parseStudentRecord("a,b,").endsWith("Dept: ")
                && validateFileExtension("archive.DoCx").equals("Accepted")
                && maskPhoneNumber("123456789a").equals("Invalid phone number")
                && validateAndFormat("ABC03022600042").equals("[ABC] DATE: 03/02/26 | SEQ: 00042")
                && validateAndFormat("ABC03X22600042").contains("body must contain only digits");
        if (!checksPass) {
            throw new AssertionError("A Week 2 practice edge-case check failed.");
        }
        System.out.println("Additional edge-case checks passed.");
    }
}
