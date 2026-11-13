class Solution:
    def isPalindrome(self, head):
        values = []
        while head is not None:
            values.append(head.val)
            head = head.next
        return all(values[i] == values[-1 - i] for i in range(len(values) // 2))
