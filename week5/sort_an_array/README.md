# Sort an Array

Problem: [LeetCode 912 — Sort an Array](https://leetcode.com/problems/sort-an-array/).

Implementation: [`solution.py`](solution.py), Python 3, `Solution.sortArray(nums)`.

## 1. Problem

Given an array of integers, return its values in ascending order. Keep every value,
including duplicates. Negative values must also appear in the correct order.
The solution must avoid built-in sorting and achieve `O(n log n)` time.

This implementation changes the input list and returns that same list. It also
handles an empty list as a useful defensive case.

## 2. Approach

Merge sort solves a smaller version of the same problem before combining results:

1. Represent the current part of the array by indices `[lo, hi)`. The `hi` index
   is excluded, so this part has `hi - lo` elements.
2. Stop when the part has zero or one element: it is already sorted.
3. Split at `mid = (lo + hi) // 2` and recursively sort both parts.
4. Compare the first unused value from each sorted part. Copy the smaller one
   into a temporary buffer and advance its pointer.
5. When one part is exhausted, copy all remaining values from the other part.
6. Copy the merged values back into the original array at the same indices.

The buffer is allocated once for the entire array. Recursive calls use indices
instead of creating sublists. The function uses neither `sort()` nor `sorted()`.

### Why merging is correct

Before each iteration, the buffer's filled prefix is sorted and contains exactly
the values already consumed from the two parts. Because each part is sorted,
the next smallest unused value must be at one of the two pointers. Choosing the
smaller value preserves that invariant. When one part ends, the remaining part is
already sorted, so appending its remaining values completes the merge.

The recursion starts with sorted single-element parts. Correct merging therefore
makes every larger part sorted, including the entire array. Each input position
is copied exactly once into its merge output, so duplicates are preserved.
On equality the code chooses the left part, making the algorithm stable: equal
values retain their original relative order.

### Written trace

Use `[5, -1, 3, -1, 2]`. The subarrays below describe index ranges; the code does
not create these sublists.

```text
Split [5, -1, 3, -1, 2]
  left:  [5, -1]
    split into [5] and [-1]
    compare 5 and -1 -> take -1
    right part ends -> append 5
    merged result: [-1, 5]
  right: [3, -1, 2]
    split into [3] and [-1, 2]
    split [-1, 2] into [-1] and [2]
    compare -1 and 2 -> take -1; append remaining 2
    merged result: [-1, 2]
    merge [3] with [-1, 2]
    compare 3 and -1 -> take -1
    compare 3 and 2  -> take 2
    append remaining 3
    merged result: [-1, 2, 3]
```

The final merge combines `[-1, 5]` and `[-1, 2, 3]`:

| Step | Left candidate | Right candidate | Action | Buffer prefix |
| --- | --- | --- | --- | --- |
| 1 | -1 | -1 | Take the left -1 on equality | `[-1]` |
| 2 | 5 | -1 | Take the right -1 | `[-1, -1]` |
| 3 | 5 | 2 | Take 2 | `[-1, -1, 2]` |
| 4 | 5 | 3 | Take 3 | `[-1, -1, 2, 3]` |
| 5 | 5 | exhausted | Append the remaining 5 | `[-1, -1, 2, 3, 5]` |

Copy the buffer range back to the input. Both occurrences of `-1` are retained.

### Challenges and a failed approach experiment

The main implementation challenges are keeping the split boundaries consistent,
copying the unconsumed tail after merging, and avoiding repeated sublist
allocation. Half-open intervals and one shared buffer make those details explicit.
For example, merging `[1, 4]` with `[2, 3]` must append `4` after the comparison
loop finishes; otherwise a value is lost.

A tempting simpler approach is a **single** left-to-right pass that swaps adjacent
values whenever they are out of order. This experiment was run separately:

```python
values = [3, 2, 1]
for i in range(len(values) - 1):
    if values[i] > values[i + 1]:
        values[i], values[i + 1] = values[i + 1], values[i]
print(values)  # [2, 1, 3]
```

The first swap produces `[2, 3, 1]`; the second produces `[2, 1, 3]`. The maximum
reaches the end, but `2` and `1` remain inverted. Thus one linear pass is
insufficient. Repeating passes can produce bubble sort, but it needs `O(n^2)`
time in the worst case. This counterexample distinguishes a single pass from
a complete sorting algorithm.

### Running and testing

From the repository root:

```bash
python week5/sort_an_array/solution.py
python -m unittest discover -s tests -v
```

The demonstration prints `[-1, -1, 2, 3, 5]`. The test suite checks edge cases,
duplicates, negative values, sorted and reverse-sorted inputs, and deterministic
random inputs against Python's trusted sorting result. Built-in sorting is used
only as a test oracle, never in the solution.

For LeetCode, submit the `typing` import and the `Solution` class from
`solution.py`; the guarded demonstration is optional.

## 3. Time and Space Complexity

**Time Complexity: `O(n log n)` for the worst, average, and best cases**
(more precisely `Theta(n log n)` for `n >= 2` in this implementation).

For a part of size `m`, merging writes all `m` values into the buffer and copies
all `m` values back. That is `Theta(m)` work even if the part was already sorted.
The two recursive parts have sizes `floor(m / 2)` and `ceil(m / 2)`, giving

```text
T(n) = T(floor(n / 2)) + T(ceil(n / 2)) + Theta(n).
```

There are `Theta(log n)` split levels. At each merge level, the total number of
values processed is at most `n`, with linear work per level. The resulting total
is `Theta(n log n)`. The initial buffer allocation costs `Theta(n)` and does not
change that bound. Empty and single-element inputs return in `O(1)` time.

**Auxiliary Space Complexity: `O(n)`**.

The reusable buffer has `n` entries. Recursion holds `O(log n)` active calls,
each with a constant number of indices. These costs add to
`O(n + log n) = O(n)`. Recursive calls run sequentially, so their stack space is
determined by recursion depth, not the total number of calls. No separate result
list is created: the returned object is the input list. Changing the input does
not make the algorithm constant-space; the buffer still uses linear storage.

## 4. Reflection / Improvement

For unrestricted comparison sorting, `O(n log n)` is the optimal worst-case time
bound. There are `n!` possible orders of distinct values, and a binary comparison
decision tree needs height at least `log2(n!) = Omega(n log n)` to distinguish
them. A different comparison sort cannot improve that worst-case time order.

Possible changes depend on what needs improvement:

- **Reduce memory:** use heapsort instead. Building a heap and repeatedly moving
  its maximum to the end gives `O(n log n)` worst-case time and `O(1)` auxiliary
  space with iterative heap operations. The usual implementation is not stable.
- **Help already ordered inputs:** after sorting the halves, skip merging when
  `nums[mid - 1] <= nums[mid]`. All values in the left part then precede all values
  in the right part. This can reduce the best-case time to `O(n)` while preserving
  `O(n log n)` worst-case time. The present version always merges for a simpler
  trace and complexity analysis.
- **Use a limited value range:** counting sort can take `O(n + k)` time with
  `O(k)` additional counts when values fall in a range of width `k`. It requires
  mapping negative values to nonnegative count indices and is attractive only
  when that range is reasonably small. It is not a general comparison sort.

Before submitting, run the tests and trace a different example by hand so that
you can explain the implementation and its tradeoffs yourself.
