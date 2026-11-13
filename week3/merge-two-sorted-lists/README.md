# Merge Two Sorted Lists

## Problem
Combine two ascending linked lists into one sorted list.

## Approach
Choose the smaller first node as the head. Then attach the smaller of the two current nodes. Once one list is empty, attach the remainder of the other.

## Trace
[1, 4] and [2, 3] produce the choices 1, 2, 3, then append 4.

## Complexity
- Time: O(m + n), since every step advances a pointer and neither list is restarted.
- Auxiliary space: O(1), since the existing nodes are relinked.

## Reflection
Copying nodes would use O(m + n) space. Recursive merging also adds stack frames.
