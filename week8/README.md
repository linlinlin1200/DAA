# Week 8: BST Leetcode lab COMSE

Three standalone Java projects for the three problems listed in the OCS lab.
Each folder contains a complete `Main.java`, tests and its own analysis report.
No Maven, Gradle or external libraries are needed. Requires JDK 11 or newer.

| Problem | Project | Time | Auxiliary space |
| --- | --- | --- | --- |
| 700. Search in a BST | [01-search-in-bst](01-search-in-bst/) | O(h) | O(1) |
| 938. Range Sum of BST | [02-range-sum-of-bst](02-range-sum-of-bst/) | O(h + k), worst O(n) | O(h) |
| 108. Sorted Array to BST | [03-sorted-array-to-bst](03-sorted-array-to-bst/) | O(n) | O(log n), plus O(n) output |

Here `n` is the node count, `h` the height and `k` the number of range matches.

## Run a project

Open any problem folder and run:

```sh
java Main.java
```

Or open that folder in an IDE and run `Main.main`. All needed classes, including
`TreeNode`, are in that file. The sample inputs are in `main` and can be edited.
The folders are separate projects: do not compile their different `Main` classes
together into the same output directory.

## Run all tests

From the repository root in PowerShell:

```powershell
./week8/run-tests.ps1
```

From the repository root in Bash:

```sh
bash week8/run-tests.sh
```

Tests run locally and in GitHub Actions. They do not submit to the LeetCode judge.
This work was prepared with AI assistance. Review the code and traces before
presenting it as coursework. The reports explain actual checks and pitfalls,
without inventing personal experiences.

The separate [classroom practice project](https://github.com/linlinlin1200/DAA-bst-classroom)
covers all ten exercises from the attached OCS slide file.
