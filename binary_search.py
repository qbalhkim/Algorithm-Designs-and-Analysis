import csv
import time
import os

def binary_search(arr, target):
    left, right = 0, len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid][0] == target:
            return mid
        elif arr[mid][0] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1

def load_dataset(filename):
    with open(filename, 'r') as csvfile:
        reader = csv.reader(csvfile)
        data = [(int(row[0]), row[1]) for row in reader]
    return data

def test_binary_search(data):
    n = len(data)
    results = []

    # Choose values to test
    best_case = data[n // 2][0]
    average_case = data[n // 4][0]
    worst_case = 10**10  

    def measure_case(case_name, value):
        start = time.time()
        for _ in range(n):
            binary_search(data, value)
        end = time.time()
        elapsed = (end - start) * 1000  
        results.append(f"{case_name} time: {elapsed:.2f} ms")

    measure_case("Best case", best_case)
    measure_case("Average case", average_case)
    measure_case("Worst case", worst_case)

    return results


if __name__ == "__main__":
    filename = input("Enter sorted dataset filename : ").strip()

    if not os.path.exists(filename):
        print("Error: File not found.")
        exit(1)

    data = load_dataset(filename)
    results = test_binary_search(data)

    
    base_name = os.path.basename(filename)
    size_part = base_name.split("_")[-1].split(".")[0]
    output_file = f"binary_search_{size_part}.txt"

    with open(output_file, "w") as f:
        for line in results:
            f.write(line + "\n")

    print(f"\nResults written to {output_file}")
    print("\n".join(results))
