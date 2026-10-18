# Verification

From the repository root, run:

```sh
python -m unittest discover -s tests -v
```

No third-party packages are required. GitHub Actions runs the same suite on Python
3.10, 3.12, and 3.14 after a push or pull request.

The 23 test methods cover the published examples, empty and single-element inputs,
duplicates, sorted and reversed inputs, allowed value boundaries, odd lengths,
and lengths around powers of two. Each algorithm also receives all 3,280 sequences
of length 0 through 7 over `{-1, 0, 1}`, 250 reproducible random cases, and one
50,000-element case. The empty-array case checks defensive behavior beyond that
problem's minimum length of one.

The array tests check that the same array object is returned. The linked-list
checks also detect cycles, missing or newly allocated result nodes, replacement of
node values, and changes to the relative order of equal-valued nodes. These checks
validate the implementation's relinking and stability claims, beyond LeetCode's
required sorted values.

Python's built-in `sorted` appears only in the tests as a reference result; neither
solution uses it. The tests check behavior. The time and space complexity claims
come from analyzing the source, rather than estimating complexity from a timeout.
