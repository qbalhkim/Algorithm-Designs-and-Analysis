import java.io.*;
import java.util.*;

public class quick_sort_step {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter CSV filename: ");
        String filename = scanner.nextLine();

        System.out.print("Enter start row: ");
        int startRow = scanner.nextInt();

        System.out.print("Enter end row: ");
        int endRow = scanner.nextInt();
        scanner.close();

        List<DataRow> data = readSubset(filename, startRow, endRow);
        List<String> steps = new ArrayList<>();

        steps.add(data.toString());
        quickSort(data, 0, data.size() - 1, steps);

        String outputFile = "quick_sort_step_" + startRow + "_" + endRow + ".txt";
        writeSteps(steps, outputFile);
        System.out.println("Quick sort steps written to " + outputFile);
    }

    public static List<DataRow> readSubset(String filename, int start, int end) throws Exception {
        List<DataRow> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int row = 0;
        while ((line = br.readLine()) != null) {
            if (row >= start && row <= end) {
                String[] parts = line.split(",");
                list.add(new DataRow(Long.parseLong(parts[0]), parts[1]));
            }
            row++;
        }
        br.close();
        return list;
    }

    public static void quickSort(List<DataRow> list, int low, int high, List<String> steps) {
        if (low < high) {
            int pi = partition(list, low, high);
            steps.add("pi=" + pi + " " + list.toString());
            quickSort(list, low, pi - 1, steps);
            quickSort(list, pi + 1, high, steps);
        }
    }

    public static int partition(List<DataRow> list, int low, int high) {
        DataRow pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    public static void writeSteps(List<String> steps, String filename) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String step : steps) {
            writer.write(step);
            writer.newLine();
        }
        writer.close();
    }
}

class DataRow implements Comparable<DataRow> {
    long number;
    String text;

    public DataRow(long number, String text) {
        this.number = number;
        this.text = text;
    }

    @Override
    public int compareTo(DataRow other) {
        return Long.compare(this.number, other.number);
    }

    @Override
    public String toString() {
        return number + "/" + text;
    }
}
