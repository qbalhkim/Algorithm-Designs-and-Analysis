import csv
import time


def merge_sort(data):
    if len(data) > 1:
        mid = len(data) // 2
        left = data[:mid]
        right = data[mid:]

        merge_sort(left)
        merge_sort(right)

        i = j = k = 0
        while i < len(left) and j < len(right):
            if left[i][0] < right[j][0]:
                data[k] = left[i]
                i += 1
            else:
                data[k] = right[j]
                j += 1
            k += 1

        while i < len(left):
            data[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            data[k] = right[j]
            j += 1
            k += 1


def merge_sort_main(input_file):
    with open(input_file, 'r') as csvfile:
        reader = csv.reader(csvfile)
        data = [(int(row[0]), row[1]) for row in reader]

    start_time = time.time()
    merge_sort(data)
    end_time = time.time()

    n = len(data)
    output_file = f"merge_sort_{n}.csv"
    with open(output_file, 'w', newline='') as f:
        writer = csv.writer(f)
        for row in data:
            writer.writerow(row)

    print(f"-Sorted {n} records.")
    print(f"-Time taken (excluding I/O): {end_time - start_time:.4f} seconds.")
    print(f"-Output file name: {output_file}")


if __name__ == "__main__":
    file_name = input("Enter CSV filename : ")
    merge_sort_main(file_name)
