# DAA — Design and Analysis of Algorithms

Coursework repository. Each week has its own folder; each problem has source code and an individual report.

## Week 5: sorting

| Problem | Implementation | Report | Time | Auxiliary space |
| --- | --- | --- | --- | --- |
| [912. Sort an Array](https://leetcode.com/problems/sort-an-array/) | [Python solution](week5/sort_an_array/solution.py) | [Analysis and tracing](week5/sort_an_array/README.md) | O(n log n) | O(n) |
| [148. Sort List](https://leetcode.com/problems/sort-list/) | [Python solution](week5/sort_list/solution.py) | [Analysis and tracing](week5/sort_list/README.md) | O(n log n) | O(1) |

Both solutions implement merge sort directly, without built-in sorting. Each report contains a problem summary, algorithm explanation, worked traces, development challenges and counterexamples, time and space derivations, and possible improvements. Auxiliary space excludes the existing input and includes recursion or temporary buffers where used.

```text
DAA/
├── README.md
├── week5/
│   ├── STUDY_GUIDE_RU.md
│   ├── sort_an_array/
│   │   ├── README.md
│   │   └── solution.py
│   └── sort_list/
│       ├── README.md
│       └── solution.py
├── tests/
│   ├── test_sort_an_array.py
│   └── test_sort_list.py
└── .github/workflows/tests.yml
```

## Run locally

Python 3.10 or newer is sufficient. There are no third-party dependencies. From the repository root:

```bash
python -m unittest discover -s tests -v
```

The test suite checks the examples, boundary values, duplicates, short exhaustive inputs, deterministic random inputs, and inputs of 50,000 elements. Linked-list tests also check stability, absence of cycles, preservation of node identities, and preservation of each node's value. Python's `sorted` is used only as an independent test oracle, never in the solution algorithms.

GitHub Actions runs the same command on Python 3.10, 3.12, and 3.14.

## Using the code on LeetCode

- **Sort an Array:** paste the contents of `week5/sort_an_array/solution.py` into the Python3 editor. The entry point is `Solution().sortArray(nums)`.
- **Sort List:** paste the contents of `week5/sort_list/solution.py` into the Python3 editor. The local `ListNode` class provides the same `val` and `next` interface; LeetCode also supplies this class. The entry point is `Solution().sortList(head)`.

Local verification does not represent a submission to the LeetCode judge.

For an explanation in Russian and questions to prepare for discussing the algorithms, see [Week 5 study notes](week5/STUDY_GUIDE_RU.md).
