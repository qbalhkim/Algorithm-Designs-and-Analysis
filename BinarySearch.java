import java.io.*;
import java.util.*;

public class BinarySearch {

    static class Entry {
        long number;
        String text;

        Entry(long number, String text) {
            this.number = number;
            this.text = text;
        }
    }

    public static List<Entry> loadDataset(String filename) throws IOException {
        List<Entry> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                long num = Long.parseLong(parts[0]);
                String text = parts[1];
                data.add(new Entry(num, text));
            }
        }
        reader.close();
        return data;
    }

    public static int binarySearch(List<Entry> data, long target) {
        int left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (data.get(mid).number == target)
                return mid;
            else if (data.get(mid).number < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    public static List<String> testBinarySearch(List<Entry> data) {
        int n = data.size();
        List<String> results = new ArrayList<>();

        long bestCase = data.get(n / 2).number;
        long avgCase = data.get(n / 4).number;
        long worstCase = 10_000_000_000L;

        results.add(measureCase("Best case", bestCase, data, n));
        results.add(measureCase("Average case", avgCase, data, n));
        results.add(measureCase("Worst case", worstCase, data, n));

        return results;
    }

    public static String measureCase(String caseName, long value, List<Entry> data, int repetitions) {
        long start = System.nanoTime();
        for (int i = 0; i < repetitions; i++) {
            binarySearch(data, value);
        }
        long end = System.nanoTime();
        double elapsedMs = (end - start) / 1_000_000.0;
        return caseName + " time: " + String.format("%.2f ms", elapsedMs);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter sorted dataset filename: ");
            String filename = scanner.nextLine().trim();

            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Error: File not found.");
                return;
            }

            List<Entry> dataset = loadDataset(filename);
            List<String> results = testBinarySearch(dataset);

            
            String baseName = file.getName();
            String[] nameParts = baseName.split("_");
            String sizePart = nameParts.length > 1 ? nameParts[nameParts.length - 1].replace(".csv", "") : "result";
            String outputFile = "binary_search_" + sizePart + ".txt";

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String line : results) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();

            System.out.println("\nResults written to " + outputFile);
            for (String line : results) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error handling files.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        }
    }
}
