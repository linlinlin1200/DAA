import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(Collections.emptyList(), solution.inorderTraversal(null), "empty tree");
        TestSupport.equal(Arrays.asList(1), solution.inorderTraversal(TestSupport.tree(1)), "single node");
        TestSupport.equal(Arrays.asList(1, 3, 2),
                solution.inorderTraversal(TestSupport.tree(1, null, 2, 3)), "official example");
        TestSupport.equal(Arrays.asList(4, 2, 5, 1, 3),
                solution.inorderTraversal(TestSupport.tree(1, 2, 3, 4, 5)), "balanced tree");
        TestSupport.equal(Arrays.asList(-2, -1, -2),
                solution.inorderTraversal(TestSupport.tree(-1, -2, -2)), "negative and duplicate values");
        TestSupport.equal(Collections.nCopies(100, 7),
                solution.inorderTraversal(TestSupport.chain(100, 7, true)), "100-node left chain");
        TestSupport.equal(Collections.nCopies(100, 7),
                solution.inorderTraversal(TestSupport.chain(100, 7, false)), "100-node right chain");

        TreeNode counterexample = TestSupport.tree(2, 3, 1);
        List<Integer> correct = solution.inorderTraversal(counterexample);
        List<Integer> rejectedSortedValues = new ArrayList<>(correct);
        Collections.sort(rejectedSortedValues);
        TestSupport.equal(Arrays.asList(3, 2, 1), correct, "binary tree is not necessarily a BST");
        TestSupport.check(!correct.equals(rejectedSortedValues), "sorting is not an inorder traversal");

        Random random = new Random(94);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 6);
            List<Integer> before = TestSupport.serialize(root);
            List<Integer> expected = new ArrayList<>();
            reference(root, expected);
            TestSupport.equal(expected, solution.inorderTraversal(root), "random tree " + i);
            TestSupport.equal(before, TestSupport.serialize(root), "tree unchanged " + i);
        }
        TestSupport.equal(Collections.emptyList(), solution.inorderTraversal(null), "reused Solution has no saved result");
        System.out.println("PASS 01-inorder-traversal: " + TestSupport.count() + " checks");
    }

    private static void reference(TreeNode node, List<Integer> result) {
        if (node == null) return;
        reference(node.left, result);
        result.add(node.val);
        reference(node.right, result);
    }

    private static TreeNode randomTree(Random random, int levels) {
        if (levels == 0 || random.nextInt(4) == 0) return null;
        return new TreeNode(random.nextInt(11) - 5,
                randomTree(random, levels - 1), randomTree(random, levels - 1));
    }
}
