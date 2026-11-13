def first_bad_version(n, is_bad_version):
    low, high = 1, n
    while low < high:
        middle = (low + high) // 2
        if is_bad_version(middle):
            high = middle
        else:
            low = middle + 1
    return low


class Solution:
    def firstBadVersion(self, n):
        return first_bad_version(n, isBadVersion)
