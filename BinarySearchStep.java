import java.io.*;
import java.util.*;

public class BinarySearchStep {

    static class Entry {
        long number;
        String text;

        Entry(long number, String text) {
            this.number = number;
            this.text = text;
        }

        @Override
        public String toString() {
            return number + "/" + text;
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

    public static List<String> binarySearchSteps(List<Entry> data, long target) {
        List<String> steps = new ArrayList<>();
        int left = 0, right = data.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            Entry entry = data.get(mid);
            steps.add(mid + ": " + entry);

            if (entry.number == target) {
                return steps;  // Found
            } else if (entry.number < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        steps.add("-1");  // Not found
        return steps;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter sorted dataset filename: ");
            String filename = scanner.nextLine().trim();

            System.out.print("Enter integer target to search: ");
            long target = Long.parseLong(scanner.nextLine().trim());

            List<Entry> dataset = loadDataset(filename);
            List<String> steps = binarySearchSteps(dataset, target);

            String outputFile = "binary_search_step_" + target + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
            writer.close();

            System.out.println("\nSearch steps written to " + outputFile);
            for (String step : steps) {
                System.out.println(step);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (IOException e) {
            System.out.println("Error reading or writing files.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Target must be an integer.");
        }

        scanner.close();
    }
}
