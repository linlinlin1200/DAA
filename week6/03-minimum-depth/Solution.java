import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int depth = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.remove();
                if (node.left == null && node.right == null) return depth;
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            depth++;
        }
        return depth;
    }
}
