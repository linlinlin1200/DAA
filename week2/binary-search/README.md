# Binary Search

## Problem
Find the index of a target in a sorted array, or return -1.

## Approach
Track an inclusive search interval. Compare the target to the middle value and discard the half that cannot contain it.

## Trace
In [-1, 0, 3, 5, 9, 12], searching for 9 checks index 2 and then index 4.

## Complexity
- Time: O(log n), since each comparison cuts the remaining range roughly in half.
- Auxiliary space: O(1) for the interval bounds.

## Reflection
A linear scan is simpler but uses O(n) comparisons in the worst case. Binary search relies on sorted input.
