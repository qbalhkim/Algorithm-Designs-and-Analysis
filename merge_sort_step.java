import java.io.*;
import java.util.*;

public class merge_sort_step {

    static class Row {
        long number;
        String text;

        Row(long number, String text) {
            this.number = number;
            this.text = text;
        }

        @Override
        public String toString() {
            return number + "/" + text;
        }
    }

    public static void mergeSortWithSteps(List<Row> data, List<String> log) {
        mergeSortInner(data, 0, data.size() - 1, log);
    }

    private static void mergeSortInner(List<Row> arr, int l, int r, List<String> log) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortInner(arr, l, m, log);
            mergeSortInner(arr, m + 1, r, log);
            merge(arr, l, m, r, log);
        }
    }

    private static void merge(List<Row> arr, int l, int m, int r, List<String> log) {
        List<Row> left = new ArrayList<>(arr.subList(l, m + 1));
        List<Row> right = new ArrayList<>(arr.subList(m + 1, r + 1));

        int i = 0, j = 0, k = l;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).number < right.get(j).number) {
                arr.set(k++, left.get(i++));
            } else {
                arr.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) arr.set(k++, left.get(i++));
        while (j < right.size()) arr.set(k++, right.get(j++));

        log.add(formatStep(arr));
    }

    private static String formatStep(List<Row> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < data.size(); i++) {
            sb.append(data.get(i));
            if (i < data.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter CSV filename: ");
        String fileName = sc.nextLine();
        System.out.print("Enter start row: ");
        int startRow = sc.nextInt();
        System.out.print("Enter end row: ");
        int endRow = sc.nextInt();
        sc.close();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<Row> selectedRows = new ArrayList<>();
        String line;
        int currentRow = 0;

        while ((line = br.readLine()) != null) {
            currentRow++;
            if (currentRow >= startRow && currentRow <= endRow) {
                String[] parts = line.split(",");
                selectedRows.add(new Row(Long.parseLong(parts[0]), parts[1]));
            }
        }
        br.close();

        List<String> log = new ArrayList<>();
        
        log.add(formatStep(selectedRows));
        
        mergeSortWithSteps(selectedRows, log);

        String outputFile = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (String step : log) {
            writer.write(step);
            writer.newLine();
        }
        writer.close();
        System.out.println("Merge steps written to: " + outputFile);
    }
}
