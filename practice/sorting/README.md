# Bubble Sort and Selection Sort

Both functions sort in place and use O(1) auxiliary space. Bubble sort moves the largest remaining item to the right by swapping adjacent inversions. It takes O(n) time on sorted input with the early exit and O(n²) in the worst case. It is stable because equal items are never swapped.

Selection sort finds the minimum in the unsorted suffix and swaps it into the next position. It uses Θ(n²) comparisons on any input and at most n − 1 swaps. The swaps can change the order of equal items.

For [4, 1, 3, 2], bubble sort's first pass gives [1, 3, 2, 4], while selection sort's first pass gives [1, 4, 3, 2].
