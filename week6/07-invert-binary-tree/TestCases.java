import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(null, solution.invertTree(null), "empty tree");
        TestSupport.equal(Arrays.asList(4, 7, 2, 9, 6, 3, 1), TestSupport.serialize(solution.invertTree(TestSupport.tree(4, 2, 7, 1, 3, 6, 9))), "official full-tree example");
        TestSupport.equal(Arrays.asList(2, 3, 1), TestSupport.serialize(solution.invertTree(TestSupport.tree(2, 1, 3))), "official small example");
        TreeNode singleton = new TreeNode(-5);
        TestSupport.check(solution.invertTree(singleton) == singleton, "single node identity retained");
        TestSupport.equal(Arrays.asList(-5), TestSupport.serialize(singleton), "single value retained");
        TestSupport.equal(Arrays.asList(1, null, 2, null, 3), TestSupport.serialize(solution.invertTree(TestSupport.tree(1, 2, null, 3))), "all levels must be inverted");
        TestSupport.equal(Arrays.asList(0, -2, -2, null, null, 4), TestSupport.serialize(solution.invertTree(TestSupport.tree(0, -2, -2, null, 4))), "negative and duplicate values");

        TreeNode leftChain = TestSupport.chain(100, 7, true);
        solution.invertTree(leftChain);
        int length = 0;
        for (TreeNode node = leftChain; node != null; node = node.right) {
            TestSupport.check(node.left == null, "inverted maximum-size chain has no left child");
            length++;
        }
        TestSupport.equal(100, length, "maximum-size chain length retained");

        Random random = new Random(226);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 0);
            List<Integer> before = TestSupport.serialize(root);
            TreeNode expected = reference(root);
            TreeNode oldLeft = root == null ? null : root.left;
            TreeNode oldRight = root == null ? null : root.right;
            TreeNode actual = solution.invertTree(root);
            TestSupport.check(root == actual, "root identity retained " + i);
            TestSupport.equal(TestSupport.serialize(expected), TestSupport.serialize(actual), "random mirror " + i);
            if (root != null) {
                TestSupport.check(root.left == oldRight && root.right == oldLeft, "original child objects reused " + i);
            }
            solution.invertTree(root);
            TestSupport.equal(before, TestSupport.serialize(root), "double inversion restores tree " + i);
        }
        System.out.println("Invert Binary Tree: " + TestSupport.count() + " checks passed");
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 6 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(21) - 10, randomTree(random, depth + 1), randomTree(random, depth + 1));
    }

    private static TreeNode reference(TreeNode root) {
        if (root == null) {
            return null;
        }
        return new TreeNode(root.val, reference(root.right), reference(root.left));
    }
}
