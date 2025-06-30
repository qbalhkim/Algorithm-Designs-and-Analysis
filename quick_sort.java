import java.io.*;
import java.util.*;

public class quick_sort {

    static class Row {
        long number;
        String text;

        Row(long number, String text) {
            this.number = number;
            this.text = text;
        }
    }

    public static void quickSort(List<Row> data) {
        quickSortHelper(data, 0, data.size() - 1);
    }

    private static void quickSortHelper(List<Row> data, int low, int high) {
        while (low < high) {
            if (high - low + 1 < 16) {
                insertionSort(data, low, high);
                break;
            }

            int pi = partition(data, low, high);

            if (pi - low < high - pi) {
                quickSortHelper(data, low, pi - 1);
                low = pi + 1;
            } else {
                quickSortHelper(data, pi + 1, high);
                high = pi - 1;
            }
        }
    }

    private static int partition(List<Row> data, int low, int high) {
        Row pivot = data.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (data.get(j).number <= pivot.number) {
                i++;
                Collections.swap(data, i, j);
            }
        }

        Collections.swap(data, i + 1, high);
        return i + 1;
    }

    private static void insertionSort(List<Row> data, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Row key = data.get(i);
            int j = i - 1;

            while (j >= low && data.get(j).number > key.number) {
                data.set(j + 1, data.get(j));
                j--;
            }

            data.set(j + 1, key);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java quick_sort <input_filename.csv>");
            return;
        }

        String inputFile = args[0];
        String outputFile = "quick_sort_" + inputFile.replaceAll("[^0-9]", "") + ".csv";
        List<Row> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2)
                    rows.add(new Row(Long.parseLong(parts[0]), parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
            return;
        }

        long start = System.nanoTime();
        quickSort(rows);
        long end = System.nanoTime();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (Row row : rows) {
                bw.write(row.number + "," + row.text);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }

        double timeInSeconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Quick Sort completed.\nInput: %s\nOutput: %s\nRuntime: %.3f s\n",
                inputFile, outputFile, timeInSeconds);
    }
}
