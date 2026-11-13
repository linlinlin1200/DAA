# First Bad Version

## Problem
Find the first failing version in a sequence where every later version also fails.

## Approach
Keep the first bad version inside the inclusive interval. A bad midpoint stays as the upper bound. A good midpoint and every earlier version can be discarded.

## Trace
With versions 1 through 5 and the first failure at 4, version 3 passes and version 4 fails. The remaining interval is [4, 4].

## Complexity
- Time: O(log n) API calls because each call halves the candidate interval.
- Auxiliary space: O(1) for two bounds and a midpoint.

## Reflection
A forward scan needs up to n API calls. Moving the upper bound to midpoint minus one on a failure would incorrectly discard a possible answer.
