"""Behavioral checks; Python's sorted is used only as a test oracle."""

import itertools
import random
import unittest

from week5.sort_an_array.solution import Solution


class SortArrayTests(unittest.TestCase):
    def assert_sorted_same_object(self, values):
        expected = sorted(values)
        original = values
        result = Solution().sortArray(values)
        self.assertIs(result, original, "Return the same list that was supplied.")
        self.assertEqual(result, expected)

    def test_leetcode_examples(self):
        for values in ([5, 2, 3, 1], [5, 1, 1, 2, 0, 0]):
            with self.subTest(values=values):
                self.assert_sorted_same_object(values)

    def test_empty_array_defensively(self):
        self.assert_sorted_same_object([])

    def test_single_element(self):
        self.assert_sorted_same_object([17])

    def test_two_elements(self):
        for values in ([2, 1], [1, 2], [1, 1]):
            with self.subTest(values=values):
                self.assert_sorted_same_object(values)

    def test_all_equal(self):
        self.assert_sorted_same_object([7] * 257)

    def test_already_sorted_and_reversed(self):
        for values in (list(range(-50, 51)), list(range(50, -51, -1))):
            with self.subTest(first=values[0]):
                self.assert_sorted_same_object(values)

    def test_duplicates_and_constraint_boundaries(self):
        self.assert_sorted_same_object([50000, -50000, 0, -1, 50000, -50000, 1, 0])

    def test_odd_and_non_power_of_two_lengths(self):
        for length in (3, 5, 6, 7, 9, 15, 17, 31, 33, 63, 65, 127, 129):
            with self.subTest(length=length):
                self.assert_sorted_same_object(list(range(length, 0, -1)))

    def test_exhaustive_short_arrays(self):
        for length in range(8):
            for values in itertools.product((-1, 0, 1), repeat=length):
                with self.subTest(values=values):
                    self.assert_sorted_same_object(list(values))

    def test_deterministic_random_arrays(self):
        rng = random.Random(912)
        for case in range(250):
            length = rng.randint(1, 256)
            # A narrow range regularly creates repeated values.
            values = [rng.randint(-25, 25) for _ in range(length)]
            with self.subTest(case=case, length=length):
                self.assert_sorted_same_object(values)

    def test_maximum_supported_length(self):
        self.assert_sorted_same_object(list(range(50000, 0, -1)))


if __name__ == "__main__":
    unittest.main()
