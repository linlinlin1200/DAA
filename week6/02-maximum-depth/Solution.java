import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    private static class Entry {
        TreeNode node;
        int depth;

        Entry(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        Deque<Entry> stack = new ArrayDeque<>();
        stack.push(new Entry(root, 1));
        int maximum = 0;

        while (!stack.isEmpty()) {
            Entry current = stack.pop();
            maximum = Math.max(maximum, current.depth);
            if (current.node.right != null) {
                stack.push(new Entry(current.node.right, current.depth + 1));
            }
            if (current.node.left != null) {
                stack.push(new Entry(current.node.left, current.depth + 1));
            }
        }
        return maximum;
    }
}
