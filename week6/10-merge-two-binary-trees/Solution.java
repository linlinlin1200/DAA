import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }

        TreeNode result = new TreeNode(value(root1) + value(root2));
        Deque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(new TreeNode[] {root1, root2, result});

        while (!stack.isEmpty()) {
            TreeNode[] current = stack.pop();
            TreeNode first = current[0];
            TreeNode second = current[1];
            TreeNode merged = current[2];

            TreeNode left1 = first == null ? null : first.left;
            TreeNode left2 = second == null ? null : second.left;
            if (left1 != null || left2 != null) {
                merged.left = new TreeNode(value(left1) + value(left2));
                stack.push(new TreeNode[] {left1, left2, merged.left});
            }

            TreeNode right1 = first == null ? null : first.right;
            TreeNode right2 = second == null ? null : second.right;
            if (right1 != null || right2 != null) {
                merged.right = new TreeNode(value(right1) + value(right2));
                stack.push(new TreeNode[] {right1, right2, merged.right});
            }
        }
        return result;
    }

    private int value(TreeNode node) {
        return node == null ? 0 : node.val;
    }
}
