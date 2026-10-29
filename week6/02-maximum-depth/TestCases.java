import java.util.List;
import java.util.Random;

class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(0, solution.maxDepth(null), "empty tree");
        TestSupport.equal(1, solution.maxDepth(TestSupport.tree(0)), "one node counts as depth one");
        TestSupport.equal(3, solution.maxDepth(TestSupport.tree(3, 9, 20, null, null, 15, 7)), "official example");
        TestSupport.equal(2, solution.maxDepth(TestSupport.tree(1, null, 2)), "one right child");
        TestSupport.equal(3, solution.maxDepth(TestSupport.tree(-1, -1, -1, -1)), "values do not affect depth");
        TestSupport.equal(10000, solution.maxDepth(TestSupport.chain(10000, 1, true)), "10000-node left chain");
        TestSupport.equal(10000, solution.maxDepth(TestSupport.chain(10000, 1, false)), "10000-node right chain");

        TreeNode unbalanced = TestSupport.tree(1, 2, 3, 4, null, null, null, 5);
        TestSupport.equal(4, solution.maxDepth(unbalanced), "deep branch must not be ignored");
        TestSupport.equal(2, rejectedUseMin(unbalanced), "counterexample for replacing max with min");

        Random random = new Random(104);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 8);
            List<Integer> before = TestSupport.serialize(root);
            TestSupport.equal(reference(root), solution.maxDepth(root), "random tree " + i);
            TestSupport.equal(before, TestSupport.serialize(root), "tree unchanged " + i);
        }
        TestSupport.equal(0, solution.maxDepth(null), "reused Solution resets maximum");
        System.out.println("PASS 02-maximum-depth: " + TestSupport.count() + " checks");
    }

    private static int reference(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(reference(root.left), reference(root.right));
    }

    private static int rejectedUseMin(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.min(rejectedUseMin(root.left), rejectedUseMin(root.right));
    }

    private static TreeNode randomTree(Random random, int levels) {
        if (levels == 0 || random.nextInt(4) == 0) return null;
        return new TreeNode(random.nextInt(11) - 5,
                randomTree(random, levels - 1), randomTree(random, levels - 1));
    }
}
