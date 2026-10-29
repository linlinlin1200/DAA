import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(Collections.emptyList(), solution.postorderTraversal(null), "empty tree");
        TestSupport.equal(Arrays.asList(1), solution.postorderTraversal(TestSupport.tree(1)), "single node");
        TestSupport.equal(Arrays.asList(3, 2, 1), solution.postorderTraversal(TestSupport.tree(1, null, 2, 3)), "official first example");
        TestSupport.equal(Arrays.asList(4, 6, 7, 5, 2, 9, 8, 3, 1), solution.postorderTraversal(TestSupport.tree(1, 2, 3, 4, 5, null, 8, null, null, 6, 7, 9)), "official larger example");
        TestSupport.equal(Arrays.asList(2, 3, 1), solution.postorderTraversal(TestSupport.tree(1, 2, 3)), "root must follow both children");
        TestSupport.equal(Arrays.asList(2, 2, 1), solution.postorderTraversal(TestSupport.tree(1, 2, 2)), "lastVisited compares node identity");
        TestSupport.equal(Arrays.asList(-2, -3, -2, 4, 0), solution.postorderTraversal(TestSupport.tree(0, -2, 4, -2, -3)), "negative and repeated values");
        TestSupport.equal(Collections.nCopies(100, 7), solution.postorderTraversal(TestSupport.chain(100, 7, true)), "maximum-size left chain");
        TestSupport.equal(Collections.nCopies(100, -7), solution.postorderTraversal(TestSupport.chain(100, -7, false)), "maximum-size right chain");
        TestSupport.equal(Collections.nCopies(10000, 7), solution.postorderTraversal(TestSupport.chain(10000, 7, false)), "extra deep-chain robustness check");

        Random random = new Random(145);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 0);
            List<Integer> expected = new ArrayList<>();
            reference(root, expected);
            List<Integer> before = TestSupport.serialize(root);
            TestSupport.equal(expected, solution.postorderTraversal(root), "random traversal " + i);
            TestSupport.equal(before, TestSupport.serialize(root), "input preserved " + i);
        }
        System.out.println("Postorder Traversal: " + TestSupport.count() + " checks passed");
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
        reference(node.left, result);
        reference(node.right, result);
        result.add(node.val);
    }
}
