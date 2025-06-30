import csv

def read_csv(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        return [(int(row[0]), row[1]) for row in reader]

def format_data(arr):
    return "[" + ", ".join(f"{num}/{text}" for num, text in arr) + "]"

def quick_sort_step(arr, low, high, steps):
    if low < high:
        pi = partition(arr, low, high, steps)
        quick_sort_step(arr, low, pi - 1, steps)
        quick_sort_step(arr, pi + 1, high, steps)

def partition(arr, low, high, steps):
    pivot = arr[high][0]
    i = low - 1
    for j in range(low, high):
        if arr[j][0] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    
    steps.append(f"pi={i+1} {format_data(arr)}")
    return i + 1

def main():
    input_file = input("Enter dataset filename (e.g., dataset_sample_1000.csv): ").strip()
    data = read_csv(input_file)
    total_rows = len(data)

    print(f"\nDataset loaded. Total rows: {total_rows} (index from 0 to {total_rows - 1})")

    try:
        start_row = int(input(f"Enter start row (0 to {total_rows - 1}): ").strip())
        end_row = int(input(f"Enter end row (0 to {total_rows - 1}): ").strip())
    except ValueError:
        print("Please enter valid integers.")
        return

    if start_row < 0 or end_row >= total_rows or start_row > end_row:
        print(" Invalid row range. Make sure 0 <= start <= end < total rows.")
        return

    selected = data[start_row:end_row + 1]
    print(f"📘 Selected rows: {start_row} to {end_row} ({len(selected)} entries)")

    steps = [format_data(selected)]
    quick_sort_step(selected, 0, len(selected) - 1, steps)

    output_filename = f"quick_sort_step_{start_row}_{end_row}.txt"
    with open(output_filename, 'w') as f:
        for step in steps:
            f.write(step + "\n")

    print(f"Output written to: {output_filename}")

if __name__ == "__main__":
    main()
