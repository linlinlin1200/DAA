# Intersection of Two Linked Lists

## Problem
Return the first node physically shared by two acyclic lists.

## Approach
Start at both heads. When a pointer reaches the end, send it to the other head. Both then travel equal total distances before meeting.

## Trace
For A → X → Y and B → C → X → Y, pointer pairs are (A,B), (X,C), (Y,X), (None,Y), (B,None), (C,A), (X,X).

## Complexity
- Time: O(m + n), since each pointer walks at most the combined list lengths.
- Auxiliary space: O(1) for two pointers.

## Reflection
Equal values do not prove an intersection. Use identity; a visited-node set would need extra memory.
