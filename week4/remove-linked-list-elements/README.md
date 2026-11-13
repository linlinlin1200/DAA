# Remove Linked List Elements

## Problem
Remove all nodes with the requested value.

## Approach
Skip the matching prefix. For every remaining node, unlink a matching successor or advance when the successor should stay.

## Trace
Removing 6 from [6, 1, 6, 6, 2] skips the first 6, then removes both successors of 1, leaving [1, 2].

## Complexity
- Time: O(n), since each original node is inspected at most once.
- Auxiliary space: O(1) for the head and current pointer.

## Reflection
Only checking successors misses matches at the head. A sentinel can handle both cases in a single loop.
