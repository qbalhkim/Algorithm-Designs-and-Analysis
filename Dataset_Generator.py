import random
import string
import csv

def generate_dataset(n, filename):
    
    numbers = random.sample(range(1, 1_000_000_0001), n)

    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        for num in numbers:
            rand_str = ''.join(random.choices(string.ascii_lowercase, k=6))
            writer.writerow([num, rand_str])

    print(f"Done generating dataset: {filename} with {n} rows.")


generate_dataset(10000, 'dataset_10.csv')
