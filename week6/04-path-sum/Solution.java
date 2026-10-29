import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    private static class State {
        final TreeNode node;
        final int sum;

        State(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        Deque<State> stack = new ArrayDeque<>();
        stack.push(new State(root, root.val));

        while (!stack.isEmpty()) {
            State current = stack.pop();
            TreeNode node = current.node;
            if (node.left == null && node.right == null && current.sum == targetSum) {
                return true;
            }
            if (node.right != null) {
                stack.push(new State(node.right, current.sum + node.right.val));
            }
            if (node.left != null) {
                stack.push(new State(node.left, current.sum + node.left.val));
            }
        }
        return false;
    }
}
