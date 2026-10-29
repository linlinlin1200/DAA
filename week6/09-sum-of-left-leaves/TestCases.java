import java.util.List;
import java.util.Random;

public class TestCases {
    private static final Solution SOLUTION = new Solution();

    public static void main(String[] args) {
        verify(null, 0, "empty tree");
        verify(TestSupport.tree(1), 0, "root is not a left leaf");
        verify(TestSupport.tree(3, 9, 20, null, null, 15, 7), 24, "statement example");
        verify(TestSupport.tree(1, 2, 3), 2, "exclude right leaf");
        verify(TestSupport.tree(1, 2, 3, 4, null, 5, 6), 9,
                "exclude left internal node, include left leaf under right branch");
        verify(TestSupport.tree(1, -2, 3, null, null, -4, 5), -6, "negative leaves");
        verify(TestSupport.tree(1, 0, 0), 0, "zero leaves");
        verify(TestSupport.chain(1000, 1000, true), 1000, "maximum left chain");
        verify(TestSupport.chain(1000, 1000, false), 0, "maximum right chain");

        Random random = new Random(404);
        for (int i = 0; i < 500; i++) {
            TreeNode root = randomTree(random, 7);
            verify(root, reference(root, false), "random tree " + i);
        }
        System.out.println("Sum of Left Leaves: " + TestSupport.count() + " checks passed");
    }

    private static void verify(TreeNode root, int expected, String label) {
        List<Integer> before = TestSupport.serialize(root);
        TestSupport.equal(expected, SOLUTION.sumOfLeftLeaves(root), label);
        TestSupport.equal(before, TestSupport.serialize(root), label + " leaves input intact");
    }

    // Reference carries the direction from the parent rather than inspecting left children.
    private static int reference(TreeNode node, boolean isLeft) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return isLeft ? node.val : 0;
        }
        return reference(node.left, true) + reference(node.right, false);
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 0 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(2001) - 1000,
                randomTree(random, depth - 1), randomTree(random, depth - 1));
    }
}
