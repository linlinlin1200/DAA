# Remove Duplicates from Sorted List

## Problem
Keep one node for each value in a sorted linked list.

## Approach
Compare each node with its successor. If the values match, unlink the successor and check the same node again.

## Trace
[1, 1, 1, 2] becomes [1, 1, 2] and then [1, 2].

## Complexity
- Time: O(n), because every iteration removes a node or moves forward.
- Auxiliary space: O(1) for one current pointer.

## Reflection
Moving forward immediately after removal can skip a run of three equal nodes. Rechecking the same node handles any run length.
