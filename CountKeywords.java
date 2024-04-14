import java.io.*;
import java.util.*;

public class CountKeywords {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords <filename>");
            return;
        }
        
        String filename = args[0];
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File " + filename + " does not exist");
            return;
        }

        try {
            System.out.println("The number of keywords in " + filename
                + " is " + countKeywords(file));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static int countKeywords(File file) throws IOException {
        // Array of all Java keywords + true, false, and null
        String[] keywordString = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "for",
            "final", "finally", "float", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public", "return",
            "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while", "true", "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean inComment = false;

            while ((line = reader.readLine()) != null) {
                // Check for comments
                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//"));
                }

                if (line.contains("/*")) {
                    inComment = true;
                    line = line.substring(0, line.indexOf("/*"));
                }

                if (line.contains("*/")) {
                    inComment = false;
                    line = line.substring(line.indexOf("*/") + 2);
                }

                if (inComment) continue;

                // Check for strings
                line = line.replaceAll("\".*?\"", "");

                // Split the line into words and check for keywords
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (keywordSet.contains(word)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
