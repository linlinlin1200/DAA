"""Sort a singly linked list with iterative, bottom-up merge sort."""

from typing import Optional


class ListNode:
    """The same node interface that LeetCode provides for Sort List."""

    def __init__(self, val: int = 0, next: Optional["ListNode"] = None):
        self.val = val
        self.next = next


class Solution:
    def sortList(self, head: Optional[ListNode]) -> Optional[ListNode]:
        """Return the sorted head by relinking the existing nodes.

        Time: O(n log n). Auxiliary space: O(1), excluding input nodes.
        """
        if head is None or head.next is None:
            return head

        length = 0
        current = head
        while current is not None:
            length += 1
            current = current.next

        # Reuse this sentinel for every pass; it is not part of the result.
        sentinel = ListNode(0, head)
        width = 1
        while width < length:
            tail = sentinel
            current = sentinel.next

            while current is not None:
                left = current
                right = self._split(left, width)
                current = self._split(right, width)
                tail = self._merge_after(tail, left, right)

            width *= 2

        return sentinel.next

    @staticmethod
    def _split(head: Optional[ListNode], width: int) -> Optional[ListNode]:
        """Detach up to width nodes; return the start of the remaining list."""
        if head is None:
            return None

        for _ in range(width - 1):
            if head.next is None:
                break
            head = head.next

        remainder = head.next
        head.next = None
        return remainder

    @staticmethod
    def _merge_after(
        tail: ListNode,
        left: Optional[ListNode],
        right: Optional[ListNode],
    ) -> ListNode:
        """Append two sorted, detached runs and return their final node."""
        while left is not None and right is not None:
            # Taking the left node on ties preserves equal-node order.
            if left.val <= right.val:
                tail.next = left
                left = left.next
            else:
                tail.next = right
                right = right.next
            tail = tail.next

        tail.next = left if left is not None else right
        # Find the end of the unused suffix so the next pair can be attached.
        while tail.next is not None:
            tail = tail.next
        return tail
