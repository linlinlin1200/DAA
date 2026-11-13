class Solution:
    def removeElements(self, head, val):
        while head is not None and head.val == val:
            head = head.next
        node = head
        while node is not None and node.next is not None:
            if node.next.val == val:
                node.next = node.next.next
            else:
                node = node.next
        return head
