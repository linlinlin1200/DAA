# 938. Range Sum of BST

[Problem](https://leetcode.com/problems/range-sum-of-bst/)

## Problem

Add the node values inside the inclusive interval `[low, high]`. The input
is a BST with unique values. Both endpoints count toward the sum.

## Approach

Use a stack for depth-first traversal. Add a node if its value is in the
interval. Visit its left child only when the current value is greater than
`low`, and its right child only when the current value is less than `high`.
The ordering lets entire subtrees be skipped.

For `[10, 5, 15, 3, 7, null, 18]`, range `[7, 15]`:

| Popped node | Sum afterwards | Action |
| --- | ---: | --- |
| 10 | 10 | Push 5, then 15 |
| 15 | 25 | Skip 18: all values there exceed 15 |
| 5 | 25 | Skip 3; push 7 |
| 7 | 32 | Add the lower endpoint |

The stack is LIFO, so pushing the right child last visits it first. The
order does not affect addition. The answer is `32`.

## Time complexity

`O(n)` in the worst case, when the interval covers the whole tree. More
precisely, with `k` matching nodes, pruning gives `O(h + k)`: the boundary
paths cost up to `h`, and matching nodes are visited once. Even a balanced
tree needs `O(n)` when all values must be included.

## Space complexity

`O(h)` auxiliary space in the worst case. DFS keeps pending subtrees along
a path. This is `O(log n)` for a balanced tree and at most `O(n)` for an
arbitrary tree. A simple chain actually keeps only one pending node here.

## Challenges and checks

Using strict comparisons for inclusion would drop `low` and `high`.
Pruning in the wrong direction would lose valid nodes. Tests include both
official examples, a single-value interval, no matches, all matches and an
empty tree. Another 200 intervals are compared with a `TreeSet` sum. A
20,000-node chain checks that traversal does not depend on Java's call stack.

The return type is `int`, as in the problem. Under its bounds, even
`20,000 * 100,000 = 2,000,000,000` fits. A wider general-purpose input would
need a `long` sum and return type.

## Reflection / improvement

A full traversal is simpler but always takes `O(n)`. This implementation
already prunes irrelevant branches and uses a stack to avoid deep recursion.
For many repeated queries, a balanced BST augmented with subtree sums could
answer prefix-sum queries in `O(log n)`, at the cost of extra metadata and
update logic. The supplied input does not have that metadata.

## Run

JDK 11 or newer, from this folder:

```sh
java Main.java
```

Expected output:

```text
Range [7, 15]: 32
Range [5, 5]: 5
Range [20, 30]: 0
```

`Main.java` includes the node class and entry point. For tests:
`javac -d build Main.java TestCases.java`, then `java -cp build TestCases`.
