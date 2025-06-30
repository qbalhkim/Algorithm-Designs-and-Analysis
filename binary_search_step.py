import csv

def load_dataset(filename):
    with open(filename, 'r') as csvfile:
        reader = csv.reader(csvfile)
        data = [(int(row[0]), row[1]) for row in reader]
    return data

def binary_search_steps(data, target):
    steps = []
    left, right = 0, len(data) - 1

    while left <= right:
        mid = (left + right) // 2
        val, string = data[mid]
        steps.append(f"{mid}: {val}/{string}")

        if val == target:
            return steps  # Found target
        elif val < target:
            left = mid + 1
        else:
            right = mid - 1

    steps.append("-1")  # Target not found
    return steps


if __name__ == "__main__":
    filename = input("Enter sorted dataset filename : ").strip()

    try:
        target = int(input("Enter integer target to search: ").strip())
    except ValueError:
        print("Invalid input. Target must be an integer.")
        exit(1)

    try:
        data = load_dataset(filename)
    except FileNotFoundError:
        print("Error: File not found.")
        exit(1)

    steps = binary_search_steps(data, target)
    output_file = f"binary_search_step_{target}.txt"

    with open(output_file, "w") as f:
        for line in steps:
            f.write(line + "\n")

    print(f"\nSearch steps written to {output_file}")
    print("\n".join(steps))
