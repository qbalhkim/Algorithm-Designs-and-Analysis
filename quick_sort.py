import csv
import time


def quick_sort(arr, low, high):
    while low < high:
        pi = partition(arr, low, high)

        
        if pi - low < high - pi:
            quick_sort(arr, low, pi - 1)
            low = pi + 1  
        else:
            quick_sort(arr, pi + 1, high)
            high = pi - 1  

def partition(arr, low, high):
    pivot = arr[high][0]
    i = low - 1
    for j in range(low, high):
        if arr[j][0] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

def read_csv(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        return [(int(row[0]), row[1]) for row in reader]

def write_csv(filename, data):
    with open(filename, 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(data)

def main():
    input_file = input("Enter dataset filename (e.g., dataset_1000.csv): ").strip()
    try:
        n = input_file.split('_')[-1].split('.')[0]
    except:
        n = "output"

    data = read_csv(input_file)

    print(f"Sorting {len(data)} records using Quick Sort...")

    start_time = time.time()
    quick_sort(data, 0, len(data) - 1)
    end_time = time.time()

    output_file = f"quick_sort_{n}.csv"
    write_csv(output_file, data)

    print(f"Sorted {len(data)} records.")
    print(f"Runtime: {end_time - start_time:.4f} seconds")
    print(f"Output file: {output_file}")

if __name__ == "__main__":
    main()
