"""Check sorted values and linked-list structure independently of the algorithm."""

import itertools
import random
import unittest

from week5.sort_list.solution import ListNode, Solution


class SortListTests(unittest.TestCase):
    def assert_sorted_nodes(self, values):
        nodes = [ListNode(value) for value in values]
        for left, right in zip(nodes, nodes[1:]):
            left.next = right
        original_values = {id(node): node.val for node in nodes}
        original_order = {}
        for node in nodes:
            original_order.setdefault(node.val, []).append(id(node))

        head = nodes[0] if nodes else None
        result = Solution().sortList(head)
        actual_nodes = []
        seen = set()
        current = result
        while current is not None:
            node_id = id(current)
            self.assertNotIn(node_id, seen, "The returned list contains a cycle.")
            self.assertIn(node_id, original_values, "An output node was newly allocated.")
            seen.add(node_id)
            self.assertIs(
                current.val,
                original_values[node_id],
                "Sorting should relink nodes without replacing their values.",
            )
            actual_nodes.append(current)
            current = current.next

        self.assertEqual(len(actual_nodes), len(nodes), "No input nodes may be lost.")
        self.assertSetEqual(seen, set(original_values))
        self.assertEqual([node.val for node in actual_nodes], sorted(values))

        actual_order = {}
        for node in actual_nodes:
            actual_order.setdefault(node.val, []).append(id(node))
        self.assertEqual(actual_order, original_order, "Equal-valued nodes must stay stable.")
        if len(nodes) == 1:
            self.assertIs(result, nodes[0])
        if not nodes:
            self.assertIsNone(result)
        return result

    def test_leetcode_examples(self):
        for values in ([4, 2, 1, 3], [-1, 5, 3, 4, 0], []):
            with self.subTest(values=values):
                self.assert_sorted_nodes(values)

    def test_empty_list(self):
        self.assert_sorted_nodes([])

    def test_single_node_identity(self):
        self.assert_sorted_nodes([17])

    def test_two_nodes(self):
        for values in ([2, 1], [1, 2], [1, 1]):
            with self.subTest(values=values):
                self.assert_sorted_nodes(values)

    def test_all_equal_preserves_node_order(self):
        self.assert_sorted_nodes([7] * 257)

    def test_already_sorted_and_reversed(self):
        for values in (list(range(-50, 51)), list(range(50, -51, -1))):
            with self.subTest(first=values[0]):
                self.assert_sorted_nodes(values)

    def test_constraint_boundaries_and_duplicates(self):
        self.assert_sorted_nodes([100000, -100000, 0, -1, 100000, -100000, 1, 0])

    def test_interleaved_equal_nodes_stay_stable(self):
        self.assert_sorted_nodes([3, 1, 2, 1, 3, 2, 1, 2, 3, 1, 3])

    def test_odd_and_non_power_of_two_lengths(self):
        for length in (3, 5, 6, 7, 9, 15, 17, 31, 33, 63, 65, 127, 129):
            with self.subTest(length=length):
                self.assert_sorted_nodes(list(range(length, 0, -1)))

    def test_exhaustive_short_lists(self):
        for length in range(8):
            for values in itertools.product((-1, 0, 1), repeat=length):
                with self.subTest(values=values):
                    self.assert_sorted_nodes(list(values))

    def test_deterministic_random_lists(self):
        rng = random.Random(148)
        for case in range(250):
            length = rng.randint(0, 256)
            values = [rng.randint(-25, 25) for _ in range(length)]
            with self.subTest(case=case, length=length):
                self.assert_sorted_nodes(values)

    def test_maximum_supported_length(self):
        self.assert_sorted_nodes(list(range(50000, 0, -1)))


if __name__ == "__main__":
    unittest.main()
