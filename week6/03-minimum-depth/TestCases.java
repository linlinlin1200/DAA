import java.util.List;
import java.util.Random;

class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(0, solution.minDepth(null), "empty tree");
        TestSupport.equal(1, solution.minDepth(TestSupport.tree(7)), "single leaf");
        TestSupport.equal(2, solution.minDepth(TestSupport.tree(3, 9, 20, null, null, 15, 7)), "official example");
        TestSupport.equal(5, solution.minDepth(TestSupport.tree(2, null, 3, null, 4, null, 5, null, 6)), "official right chain");
        TestSupport.equal(3, solution.minDepth(TestSupport.tree(-1, -2, -2, -3, null, -3, -3)), "negative and duplicate values");
        TestSupport.equal(100000, solution.minDepth(TestSupport.chain(100000, 1, true)), "100000-node left chain");
        TestSupport.equal(100000, solution.minDepth(TestSupport.chain(100000, 1, false)), "100000-node right chain");

        TreeNode oneChild = TestSupport.tree(1, null, 2);
        TestSupport.equal(2, solution.minDepth(oneChild), "missing child is not a leaf");
        TestSupport.equal(1, rejectedNaiveMin(oneChild), "counterexample for naive minimum formula");

        TreeNode shallowLeaf = new TreeNode(1, new TreeNode(2), TestSupport.chain(99998, 3, false));
        TestSupport.equal(2, solution.minDepth(shallowLeaf), "leaf found before traversing long branch");

        Random random = new Random(111);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 8);
            List<Integer> before = TestSupport.serialize(root);
            TestSupport.equal(reference(root), solution.minDepth(root), "random tree " + i);
            TestSupport.equal(before, TestSupport.serialize(root), "tree unchanged " + i);
        }
        TestSupport.equal(0, solution.minDepth(null), "reused Solution has no saved depth");
        System.out.println("PASS 03-minimum-depth: " + TestSupport.count() + " checks");
    }

    private static int reference(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) return 1 + reference(root.right);
        if (root.right == null) return 1 + reference(root.left);
        return 1 + Math.min(reference(root.left), reference(root.right));
    }

    private static int rejectedNaiveMin(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.min(rejectedNaiveMin(root.left), rejectedNaiveMin(root.right));
    }

    private static TreeNode randomTree(Random random, int levels) {
        if (levels == 0 || random.nextInt(4) == 0) return null;
        return new TreeNode(random.nextInt(11) - 5,
                randomTree(random, levels - 1), randomTree(random, levels - 1));
    }
}
