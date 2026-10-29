import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestCases {
    public static void main(String[] args) {
        Solution solution = new Solution();
        TestSupport.equal(false, solution.hasPathSum(null, 0), "empty tree is not a path");
        TestSupport.equal(true, solution.hasPathSum(TestSupport.tree(7), 7), "single matching leaf");
        TestSupport.equal(false, solution.hasPathSum(TestSupport.tree(7), 0), "single nonmatching leaf");
        TestSupport.equal(true, solution.hasPathSum(TestSupport.tree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1), 22), "official true example");
        TestSupport.equal(false, solution.hasPathSum(TestSupport.tree(1, 2, 3), 5), "official false example");
        TestSupport.equal(false, solution.hasPathSum(TestSupport.tree(1, 2), 1), "matching internal node is not a leaf");
        TestSupport.equal(true, solution.hasPathSum(TestSupport.tree(5, -2), 3), "negative value prevents sum pruning");
        TestSupport.equal(true, solution.hasPathSum(TestSupport.tree(0, 0, 0), 0), "zero and duplicate values");
        TestSupport.equal(true, solution.hasPathSum(TestSupport.tree(-2, null, -3), -5), "negative right-only path");

        TreeNode deep = TestSupport.chain(5000, 0, true);
        TestSupport.equal(true, solution.hasPathSum(deep, 0), "5000-node chain succeeds");
        TestSupport.equal(false, solution.hasPathSum(deep, 1), "5000-node chain exhausts search");

        Random random = new Random(112);
        for (int i = 0; i < 300; i++) {
            TreeNode root = randomTree(random, 0);
            int target = random.nextInt(61) - 30;
            TestSupport.equal(reference(root, target), solution.hasPathSum(root, target), "random target " + i);
            if (root != null) {
                TreeNode leaf = root;
                int sum = leaf.val;
                while (leaf.left != null || leaf.right != null) {
                    leaf = leaf.left != null ? leaf.left : leaf.right;
                    sum += leaf.val;
                }
                TestSupport.equal(true, solution.hasPathSum(root, sum), "generated existing path " + i);
            }
        }
        System.out.println("Path Sum: " + TestSupport.count() + " checks passed");
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 6 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(21) - 10, randomTree(random, depth + 1), randomTree(random, depth + 1));
    }

    private static boolean reference(TreeNode root, int target) {
        List<Integer> sums = new ArrayList<>();
        collectLeafSums(root, 0, sums);
        return sums.contains(target);
    }

    private static void collectLeafSums(TreeNode node, int sum, List<Integer> sums) {
        if (node == null) {
            return;
        }
        int next = sum + node.val;
        if (node.left == null && node.right == null) {
            sums.add(next);
        } else {
            collectLeafSums(node.left, next, sums);
            collectLeafSums(node.right, next, sums);
        }
    }
}
