# 108. Convert Sorted Array to Binary Search Tree

[Problem](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)

## Problem

Build a height-balanced BST from a strictly increasing integer array.
The inorder traversal must reproduce the array. At every node, the left
and right subtree heights must differ by at most one.

## Approach

Choose the middle element as the root. Build the left subtree from the
elements before it, and the right subtree from the elements after it.
An empty interval returns `null`. Pass index boundaries instead of copying
smaller arrays. For an even-length interval, use the lower middle.

Trace for `[-10, -3, 0, 5, 9]`:

| Indices | Middle | Node | Remaining intervals |
| --- | ---: | ---: | --- |
| 0..4 | 2 | 0 | 0..1 and 3..4 |
| 0..1 | 0 | -10 | empty and 1..1 |
| 1..1 | 1 | -3 | both empty |
| 3..4 | 3 | 5 | empty and 4..4 |
| 4..4 | 4 | 9 | both empty |

```text
      0
     / \
   -10  5
     \   \
     -3   9
```

Inorder is `[-10, -3, 0, 5, 9]`. Different choices of middle can produce
another correct tree, so tests check ordering and balance rather than one
exact level-order representation.

## Time complexity

`O(n)`. Each element creates exactly one node and each call does constant
work apart from its recursive children. The recurrence is
`T(n) = T(floor((n-1)/2)) + T(ceil((n-1)/2)) + O(1)`.

## Space complexity

`O(log n)` auxiliary stack space because each interval is roughly halved.
The returned tree itself uses `O(n)` space. The array is not changed and
no subarrays are copied. Empty input is also handled locally.

## Challenges and checks

Reusing `middle` in a child interval can prevent progress. The children
must use `middle - 1` and `middle + 1`. Inserting sorted values into a normal
BST would create a chain, which fails the balance requirement.

Tests check empty, singleton, two-element and negative-valued arrays, every
length from 3 through 100, and 10,000 elements. Every node is checked for
balance. Inorder must match the input exactly, and the input must remain unchanged.

## Reflection / improvement

Constructing `n` output nodes requires `O(n)` work, so the running time is
already optimal. Index boundaries avoid the extra allocation and copying
of array slices. An iterative version would need its own bookkeeping and
offers little benefit with logarithmic recursion depth.

## Run

JDK 11 or newer, from this folder:

```sh
java Main.java
```

The demo prints the input, a tree with `L:`/`R:` child labels, and the inorder
list. `Main.java` includes all needed classes. For tests:
`javac -d build Main.java TestCases.java`, then `java -cp build TestCases`.
