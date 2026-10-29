import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class TestCases {
    private static final Solution SOLUTION = new Solution();

    public static void main(String[] args) {
        verify(null, List.of(), "empty tree");
        verify(TestSupport.tree(1), List.of("1"), "single root");
        verify(TestSupport.tree(1, 2, 3, null, 5),
                List.of("1->2->5", "1->3"), "statement example");
        verify(TestSupport.tree(-1, -2, 0),
                List.of("-1->-2", "-1->0"), "negative and zero");
        verify(TestSupport.tree(1, 1, 1),
                List.of("1->1", "1->1"), "equal paths must both remain");
        verify(TestSupport.tree(1, 2, 3, 4, 5, 6, 7),
                List.of("1->2->4", "1->2->5", "1->3->6", "1->3->7"),
                "backtracking restores parent path");
        verify(TestSupport.tree(1, null, 2, 3), List.of("1->2->3"), "one-sided branches");

        TreeNode chain = TestSupport.chain(100, -100, true);
        verify(chain, List.of(String.join("->", Collections.nCopies(100, "-100"))),
                "maximum allowed height");

        Random random = new Random(257);
        for (int i = 0; i < 500; i++) {
            TreeNode root = randomTree(random, 7);
            verify(root, reference(root), "random tree " + i);
        }
        System.out.println("Binary Tree Paths: " + TestSupport.count() + " checks passed");
    }

    private static void verify(TreeNode root, List<String> expected, String label) {
        List<Integer> before = TestSupport.serialize(root);
        List<String> actual = new ArrayList<>(SOLUTION.binaryTreePaths(root));
        List<String> sortedExpected = new ArrayList<>(expected);
        Collections.sort(actual);
        Collections.sort(sortedExpected);
        TestSupport.equal(sortedExpected, actual, label);
        TestSupport.equal(before, TestSupport.serialize(root), label + " leaves input intact");
    }

    // The reference uses a queue and separate immutable strings instead of backtracking.
    private static List<String> reference(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> nodes = new ArrayDeque<>();
        Deque<String> paths = new ArrayDeque<>();
        nodes.add(root);
        paths.add(Integer.toString(root.val));
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.remove();
            String path = paths.remove();
            if (node.left == null && node.right == null) {
                result.add(path);
            }
            if (node.left != null) {
                nodes.add(node.left);
                paths.add(path + "->" + node.left.val);
            }
            if (node.right != null) {
                nodes.add(node.right);
                paths.add(path + "->" + node.right.val);
            }
        }
        return result;
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 0 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(201) - 100,
                randomTree(random, depth - 1), randomTree(random, depth - 1));
    }
}
