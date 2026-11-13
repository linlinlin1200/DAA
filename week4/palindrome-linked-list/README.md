# Palindrome Linked List

## Problem
Decide whether the list reads the same in both directions.

## Approach
Copy the node values into an array, then compare symmetric positions using two indices.

## Trace
[1, 2, 2, 1] compares the outer 1s and inner 2s, so it passes.

## Complexity
- Time: O(n), since collecting values and comparing the pairs both take linear time.
- Auxiliary space: O(n) for the array.

## Reflection
Comparing adjacent values cannot identify a palindrome. Reversing the second half in place and restoring it would reduce auxiliary space to O(1).
