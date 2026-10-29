import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestCases {
    private static final Solution SOLUTION = new Solution();

    public static void main(String[] args) {
        verify(null, null, null, "two empty trees");
        verify(TestSupport.tree(1), null, TestSupport.tree(1), "second tree empty");
        verify(null, TestSupport.tree(2, 3, 4), TestSupport.tree(2, 3, 4), "first tree empty");
        verify(TestSupport.tree(1, 3, 2, 5), TestSupport.tree(2, 1, 3, null, 4, null, 7),
                TestSupport.tree(3, 4, 5, 5, 4, null, 7), "statement example");
        verify(TestSupport.tree(1), TestSupport.tree(1, 2),
                TestSupport.tree(2, 2), "non-overlapping branch survives");
        verify(TestSupport.tree(-2, -3, 0), TestSupport.tree(2, 4, -5),
                TestSupport.tree(0, 1, -5), "negative values and zero sums");
        verify(TestSupport.tree(10000), TestSupport.tree(10000),
                TestSupport.tree(20000), "maximum node values");
        verify(TestSupport.chain(2000, 1, true), TestSupport.chain(2000, 2, true),
                TestSupport.chain(2000, 3, true), "maximum overlapping chains");
        verify(TestSupport.chain(2000, 1, true), TestSupport.chain(2000, 2, false),
                joinedChains(), "opposite long branches");

        Random random = new Random(617);
        for (int i = 0; i < 500; i++) {
            TreeNode first = randomTree(random, 6);
            TreeNode second = randomTree(random, 6);
            verify(first, second, reference(first, second), "random pair " + i);
        }
        System.out.println("Merge Two Binary Trees: " + TestSupport.count() + " checks passed");
    }

    private static void verify(TreeNode first, TreeNode second, TreeNode expected, String label) {
        List<Integer> beforeFirst = TestSupport.serialize(first);
        List<Integer> beforeSecond = TestSupport.serialize(second);
        TreeNode actual = SOLUTION.mergeTrees(first, second);
        TestSupport.equal(TestSupport.serialize(expected), TestSupport.serialize(actual), label);
        TestSupport.equal(beforeFirst, TestSupport.serialize(first), label + " preserves first input");
        TestSupport.equal(beforeSecond, TestSupport.serialize(second), label + " preserves second input");

        Set<TreeNode> inputNodes = Collections.newSetFromMap(new IdentityHashMap<>());
        collectNodes(first, inputNodes);
        collectNodes(second, inputNodes);
        Set<TreeNode> outputNodes = Collections.newSetFromMap(new IdentityHashMap<>());
        collectNodes(actual, outputNodes);
        boolean independent = true;
        for (TreeNode node : outputNodes) {
            if (inputNodes.contains(node)) {
                independent = false;
            }
        }
        TestSupport.check(independent, label + " output has no shared input nodes");
    }

    private static void collectNodes(TreeNode root, Set<TreeNode> nodes) {
        if (root == null) {
            return;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            nodes.add(node);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
    }

    private static TreeNode joinedChains() {
        return new TreeNode(3, TestSupport.chain(1999, 1, true),
                TestSupport.chain(1999, 2, false));
    }

    private static TreeNode reference(TreeNode first, TreeNode second) {
        if (first == null && second == null) {
            return null;
        }
        int value = (first == null ? 0 : first.val) + (second == null ? 0 : second.val);
        return new TreeNode(value,
                reference(first == null ? null : first.left, second == null ? null : second.left),
                reference(first == null ? null : first.right, second == null ? null : second.right));
    }

    private static TreeNode randomTree(Random random, int depth) {
        if (depth == 0 || random.nextInt(4) == 0) {
            return null;
        }
        return new TreeNode(random.nextInt(20001) - 10000,
                randomTree(random, depth - 1), randomTree(random, depth - 1));
    }
}
