import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(Collections.emptyList(), solution.preorderTraversal(null), "empty tree");
        TestSupport.equal(Arrays.asList(1), solution.preorderTraversal(TestSupport.tree(1)), "single node");
        TestSupport.equal(Arrays.asList(1, 2, 3), solution.preorderTraversal(TestSupport.tree(1, null, 2, 3)), "official first example");
        TestSupport.equal(Arrays.asList(1, 2, 4, 5, 6, 7, 3, 8, 9), solution.preorderTraversal(TestSupport.tree(1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9)), "official larger example");
        TestSupport.equal(Arrays.asList(1, 2, 3), solution.preorderTraversal(TestSupport.tree(1, 2, 3)), "left subtree must precede right");
        TestSupport.equal(Arrays.asList(0, -2, -2, -3, 4), solution.preorderTraversal(TestSupport.tree(0, -2, 4, -2, -3)), "negative and duplicate values");
        TestSupport.equal(Collections.nCopies(100, 7), solution.preorderTraversal(TestSupport.chain(100, 7, true)), "maximum-size left chain");
        TestSupport.equal(Collections.nCopies(100, -7), solution.preorderTraversal(TestSupport.chain(100, -7, false)), "maximum-size right chain");
        TestSupport.equal(Collections.nCopies(10000, 7), solution.preorderTraversal(TestSupport.chain(10000, 7, true)), "extra deep-chain robustness check");

        Random random = new Random(144);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 0);
            List<Integer> expected = new ArrayList<>();
            reference(root, expected);
            List<Integer> before = TestSupport.serialize(root);
            TestSupport.equal(expected, solution.preorderTraversal(root), "random traversal " + i);
            TestSupport.equal(before, TestSupport.serialize(root), "input preserved " + i);
        }
        System.out.println("Preorder Traversal: " + TestSupport.count() + " checks passed");
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 6 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(21) - 10, randomTree(random, depth + 1), randomTree(random, depth + 1));
    }

    private static void reference(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        reference(node.left, result);
        reference(node.right, result);
    }
}
