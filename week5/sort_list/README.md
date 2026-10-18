# Sort List

Source: [LeetCode — Sort List](https://leetcode.com/problems/sort-list/description/). Implementation: [solution.py](solution.py).

## 1. Problem

Given the first node of a singly linked list, return the first node of a list whose values are in nondecreasing order. Keep every value, including duplicates. An empty list should remain empty.

For example, `4 -> 2 -> 1 -> 3` should become `1 -> 2 -> 3 -> 4`.

The solution rearranges the existing nodes by changing their `next` references. It does not copy their values into an array or create replacement data nodes.

## 2. Approach

### Bottom-up merge sort

A one-node list is already sorted. The algorithm uses this observation to build larger sorted runs, where a *run* is a consecutive group of nodes:

1. Count the nodes to know when the entire list has been merged.
2. Start with run width `1`.
3. Take two adjacent runs, each containing up to `width` nodes. Detach both runs from the remaining list.
4. Merge the runs by repeatedly attaching the node with the smaller value. When one run becomes empty, attach the remainder of the other run.
5. Connect that merged run to the end of the output built during this pass. Continue with the unprocessed nodes.
6. Double `width` and make another pass. Stop when `width >= length`.

`_split` detaches a run and returns the unprocessed remainder. `_merge_after` attaches two sorted runs after an output tail and returns the new tail. A single sentinel node makes attaching the first node work the same way as attaching later nodes. The sentinel is reused across all passes and is excluded from the returned list.

The important pointer order is to save the next pair in `current` **before** merging the current pair. Merging changes links inside the detached runs. After a merge, the returned tail identifies where the next pair must be attached.

### Trace: `4 -> 2 -> 1 -> 3`

Initially, each node is a sorted run of length one.

| Pass | Width | Runs being merged | Decisions | Result of this merge |
| --- | --- | --- | --- | --- |
| 1 | 1 | `[4]` and `[2]` | `2 < 4`: take `2`, then attach `4` | `[2, 4]` |
| 1 | 1 | `[1]` and `[3]` | `1 <= 3`: take `1`, then attach `3` | `[1, 3]` |
| 2 | 2 | `[2, 4]` and `[1, 3]` | Take `1`, then `2`, then `3`; attach `4` | `[1, 2, 3, 4]` |

After the first pass, the connected list is:

```text
2 -> 4  |  1 -> 3
```

The separator marks a run boundary; the list is connected between passes. During the second pass, `_split` cuts these two runs apart before merging them. The result is:

```text
1 -> 2 -> 3 -> 4
```

The next width is `4`, equal to the list length, so the algorithm stops.

### Trace with a negative value and an odd length

Input: `-1 -> 5 -> 3 -> 4 -> 0`.

| Width | Merges during the pass | Connected list after the pass |
| --- | --- | --- |
| 1 | `[-1] + [5] -> [-1, 5]`; `[3] + [4] -> [3, 4]`; `[0] + [] -> [0]` | `-1 -> 5 -> 3 -> 4 -> 0` |
| 2 | `[-1, 5] + [3, 4] -> [-1, 3, 4, 5]`; `[0] + [] -> [0]` | `-1 -> 3 -> 4 -> 5 -> 0` |
| 4 | `[-1, 3, 4, 5] + [0] -> [-1, 0, 3, 4, 5]` | `-1 -> 0 -> 3 -> 4 -> 5` |

At width `4`, compare `-1` with `0` and take `-1`. Then compare `3` with `0` and take `0`. The right run is empty, so attach the left suffix `3 -> 4 -> 5`. Width becomes `8`, which is at least `5`, and sorting ends.

An unmatched final run is safe: `_split(None, width)` returns `None`, and merging a sorted run with an empty run preserves it. Negative values need no special branch because the algorithm compares integer values directly.

### Why it is correct

At the start of the width-`1` pass, every run of up to one node is sorted. Assume the runs at the beginning of a pass are sorted. Comparing their first remaining nodes always selects the smallest available value, so merging two adjacent runs produces a sorted run of up to twice the width. The merge preserves every node exactly once, and attaching the merged runs preserves their order as groups. Therefore the next pass starts with sorted runs of the new width. Once the width reaches the list length, there is one sorted run containing every input node.

On equal values, the comparison uses `<=` and selects the left node first. The sort is therefore stable: nodes with equal values keep their original relative order.

### Pointer challenges and a failed experiment

The following experiment isolates an important pointer error encountered when designing the split operation.

The main challenge is that the two merge inputs must be separate lists. A deliberately broken split prototype saved the next run but omitted the line `head.next = None`. On input `2 -> 1`, that gives overlapping runs:

```text
left:  2 -> 1
right:      1
```

The merge takes `1` first and then attaches the supposed remaining left run `2 -> 1`. Since the old `2.next` still points at `1`, the result contains a cycle:

```text
1 -> 2 -> 1 -> 2 -> ...
```

A bounded traversal of this prototype was run and produced `[1, 2, 1, 2, 1]`, confirming the failure without allowing an infinite loop. The final `_split` saves the remainder and then cuts the link, so each input run ends in `None`.

Another detail is locating the output tail after attaching an unused suffix. Assigning `tail.next` alone does not move `tail` to the last node. `_merge_after` walks through that suffix before returning, so the next pair is connected after the entire merged run.

### Testing

From the repository root, run:

```bash
python -m unittest discover -s tests -v
```

The repository test suite checks empty and singleton lists, sorted and reversed lists, duplicates, negative values, odd lengths, randomized cases, and a list of the maximum allowed length. It also checks node identity, the absence of cycles, and stability, since matching the sorted values alone would miss pointer errors.

An additional exhaustive check during preparation passed **3,280 lists**: every list of length `0` through `7` with values drawn from `{-1, 0, 1}`. Each result was compared with Python's `sorted` as an independent test oracle. The check also verified that the result contained exactly the original node objects, contained no cycle, and preserved the original order of equal-valued nodes. The implementation itself does not call `sorted`.

## 3. Time Complexity

**Time complexity: O(n log n)** for `n >= 2`. Empty and singleton lists take **O(1)** time.

The initial node count costs `O(n)`. Run widths are `1, 2, 4, 8, ...`, so a list of `n` nodes needs `ceil(log2(n))` merging passes.

Each pass takes `O(n)`:

- Splitting walks through disjoint runs, so the total number of nodes visited by `_split` in one pass is linear.
- Merging consumes each node once, either during comparisons or while walking the unused suffix to find its tail.
- The runs of different pairs do not overlap, so the suffix walks do not create an extra factor of `n`.

Combining the work gives `O(n) + O(n) * O(log n) = O(n log n)`. The nested loops do not imply `O(n^2)`: the inner pair-processing loop advances through the list, and the outer loop doubles the run width.

**Auxiliary space complexity: O(1)**, excluding the input list.

There is one reusable sentinel node and a fixed number of node references and counters. No array of nodes is created, and there are no recursive calls. The existing `n` input nodes remain the output nodes, so the output does not require a separate `O(n)` allocation.

## 4. Reflection / Improvement

For general comparison-based sorting, the worst-case lower bound is `Omega(n log n)` comparisons. This solution already matches that asymptotic bound and uses constant auxiliary space.

A top-down merge sort would split the list in halves recursively and could make the control flow shorter. Its time would still be `O(n log n)`, but balanced recursion would require `O(log n)` stack space, so it would not improve this solution's space usage.

For lists that are often already sorted, an initial sortedness check could return in `O(n)` time. Natural merge sort could also detect existing sorted runs and merge those instead of always starting at width one. That can reduce work on partially sorted inputs while preserving `O(n log n)` worst-case time; it requires additional run-detection and scheduling logic.

If integer values are restricted to a small range of size `k`, counting values could achieve `O(n + k)` time using `O(k)` extra space. That approach depends on the value range and would need a different design to preserve the identity and stable ordering of existing equal-valued nodes. The current algorithm needs only comparisons and works directly with the linked structure.
