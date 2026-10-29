import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int sum = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                TreeNode left = node.left;
                if (left.left == null && left.right == null) {
                    sum += left.val;
                } else {
                    stack.push(left);
                }
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return sum;
    }
}
