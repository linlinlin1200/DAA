# 700. Search in a Binary Search Tree

[Problem](https://leetcode.com/problems/search-in-a-binary-search-tree/)

## Problem

Find a value in a BST and return the existing node with its whole subtree.
Return `null` if the value is missing. Values in the left subtree are smaller
than the root, and values in the right subtree are larger.

## Approach

Start at the root. If the current value matches, return that node. Otherwise,
move left for a smaller target or right for a larger target. Repeat until a
match is found or the path ends. There is no need to copy any nodes.

For `[4, 2, 7, 1, 3]`, searching for `2`:

| Current node | Comparison | Next step |
| --- | --- | --- |
| 4 | 2 < 4 | Go left |
| 2 | 2 == 2 | Return this node, with children 1 and 3 |

Searching for `5` visits `4`, then `7`, then reaches a missing left child.
The result is `null`. The demo prints the found subtree in inorder, so the
output `[1, 2, 3]` describes the subtree rather than its level-order encoding.

## Time complexity

`O(h)` time, where `h` is the height. At most one node is visited at each
level. This is `O(log n)` for a balanced tree and `O(n)` for a chain.
An immediate root match takes `O(1)`.

## Space complexity

`O(1)` auxiliary space. The loop keeps one current-node reference and does
not use recursion or create a result tree. Demo printing is separate.

## Challenges and checks

Returning only the matching integer loses the subtree required by the
problem. Returning a new node would also lose its children. Tests check
object identity as well as missing values, both directions, an empty tree,
and a chain of 5,000 nodes.

## Reflection / improvement

A full DFS would inspect up to all `n` nodes even in a balanced tree. Using
the BST ordering avoids that work. A self-balancing tree could guarantee
`O(log n)` search, but would change how the input tree is maintained. For an
existing ordinary BST, this direct path search already avoids extra space.

## Run

JDK 11 or newer, from this folder:

```sh
java Main.java
```

Expected output:

```text
Search 2, subtree inorder: [1, 2, 3]
Search 5: not found
```

`Main.java` is complete: it includes `TreeNode`, the algorithm and `main`.
For tests: `javac -d build Main.java TestCases.java`, then
`java -cp build TestCases`.
