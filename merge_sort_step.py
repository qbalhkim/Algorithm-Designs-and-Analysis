import csv


def merge_sort_with_full_steps(data, steps_log, original_size):
    def merge_sort_inner(arr, l, r):
        if l < r:
            m = (l + r) // 2
            merge_sort_inner(arr, l, m)
            merge_sort_inner(arr, m + 1, r)
            merge(arr, l, m, r)

    def merge(arr, l, m, r):
        left = arr[l:m+1]
        right = arr[m+1:r+1]

        i = j = 0
        k = l

        while i < len(left) and j < len(right):
            if left[i][0] < right[j][0]:
                arr[k] = left[i]
                i += 1
            else:
                arr[k] = right[j]
                j += 1
            k += 1

        while i < len(left):
            arr[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            data[k] = right[j]
            j += 1
            k += 1

        # Log full array after each merge
        step = ', '.join([f"{num}/{txt}" for num, txt in arr])
        steps_log.append(f"[{step}]")

    merge_sort_inner(data, 0, len(data) - 1)


def merge_sort_step(input_file, start_row, end_row):
    output_file = f"merge_sort_step_{start_row}_{end_row}.txt"

    with open(input_file, 'r') as csvfile:
        reader = csv.reader(csvfile)
        rows = [row for idx, row in enumerate(reader) if start_row <= idx + 1 <= end_row]

    data = [(int(row[0]), row[1]) for row in rows]
    steps_log = []

    original = ', '.join([f"{num}/{txt}" for num, txt in data])
    steps_log.append(f"[{original}]")

    merge_sort_with_full_steps(data, steps_log, len(data))

    with open(output_file, 'w') as f:
        for step in steps_log:
            f.write(step + '\n')

    print(f"Merge steps written to: {output_file}")


if __name__ == "__main__":
    file_name = input("Enter CSV filename: ")
    start = int(input("Enter start row: "))
    end = int(input("Enter end row: "))
    merge_sort_step(file_name, start, end)