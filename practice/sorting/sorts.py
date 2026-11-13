def bubble_sort(values):
    for end in range(len(values) - 1, 0, -1):
        swapped = False
        for i in range(end):
            if values[i] > values[i + 1]:
                values[i], values[i + 1] = values[i + 1], values[i]
                swapped = True
        if not swapped:
            break
    return values


def selection_sort(values):
    for i in range(len(values) - 1):
        smallest = i
        for j in range(i + 1, len(values)):
            if values[j] < values[smallest]:
                smallest = j
        if smallest != i:
            values[i], values[smallest] = values[smallest], values[i]
    return values
