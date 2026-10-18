"""LeetCode 912: Sort an Array, using merge sort without built-in sorting."""

from typing import List


class Solution:
    def sortArray(self, nums: List[int]) -> List[int]:
        """Mutate nums into sorted order and return it; use O(n) auxiliary storage."""
        size = len(nums)
        if size < 2:
            return nums

        # Allocate once. All recursive calls reuse this same scratch space.
        buffer = [0] * size

        def merge_sort(lo: int, hi: int) -> None:
            # A half-open interval [lo, hi) contains hi - lo elements.
            if hi - lo <= 1:
                return

            mid = (lo + hi) // 2
            merge_sort(lo, mid)
            merge_sort(mid, hi)

            left, right, out = lo, mid, lo
            while left < mid and right < hi:
                # Taking the left value on equality preserves stable ordering.
                if nums[left] <= nums[right]:
                    buffer[out] = nums[left]
                    left += 1
                else:
                    buffer[out] = nums[right]
                    right += 1
                out += 1

            # One side can still contain values when the comparison loop ends.
            while left < mid:
                buffer[out] = nums[left]
                left += 1
                out += 1
            while right < hi:
                buffer[out] = nums[right]
                right += 1
                out += 1

            for index in range(lo, hi):
                nums[index] = buffer[index]

        merge_sort(0, size)
        return nums


if __name__ == "__main__":
    example = [5, -1, 3, -1, 2]
    print(Solution().sortArray(example))
