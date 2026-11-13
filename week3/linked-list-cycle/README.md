# Linked List Cycle

## Problem
Determine whether following next pointers eventually revisits a node.

## Approach
Advance a slow pointer one step and a fast pointer two steps. Inside a cycle their relative distance shrinks by one per iteration until they meet.

## Trace
For A → B → C → B, the first pair is (B, C) and the second is (C, C), so there is a cycle.

## Complexity
- Time: O(n), including at most one additional lap after both pointers enter a cycle.
- Auxiliary space: O(1) for the two pointers.

## Reflection
A set of visited nodes also works but uses O(n) memory. Comparing values fails when different nodes contain the same value.
