import java.io.*;
import java.util.*;

public class merge_sort {

    
    static class Row {
        long number;
        String text;

        Row(long number, String text) {
            this.number = number;
            this.text = text;
        }
    }

    // Merge Sort implementation
    public static void mergeSort(List<Row> data) {
        if (data.size() <= 1) return;

        int mid = data.size() / 2;
        List<Row> left = new ArrayList<>(data.subList(0, mid));
        List<Row> right = new ArrayList<>(data.subList(mid, data.size()));

        mergeSort(left);
        mergeSort(right);

        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).number < right.get(j).number) {
                data.set(k++, left.get(i++));
            } else {
                data.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) data.set(k++, left.get(i++));
        while (j < right.size()) data.set(k++, right.get(j++));
    }

    public static void main(String[] args) {
        // Check for input file
        if (args.length < 1) {
            System.out.println("Usage: java merge_sort <input_filename.csv>");
            return;
        }

        String inputFileName = args[0];
        String numberPart = inputFileName.replaceAll("[^0-9]", "");
        String outputFileName = "merge_sort_" + numberPart + ".csv";

        List<Row> data = new ArrayList<>();

        // Read input CSV
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;
                long number = Long.parseLong(parts[0].trim());
                String text = parts[1].trim();
                data.add(new Row(number, text));
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        
        long startTime = System.nanoTime();

        
        mergeSort(data);

        
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

      
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (Row row : data) {
                bw.write(row.number + "," + row.text);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
            return;
        }

        System.out.printf("Merge Sort completed.\nInput: %s\nOutput: %s\nRunning time: %.3f s\n",
                inputFileName, outputFileName, durationInSeconds);
    }
}
