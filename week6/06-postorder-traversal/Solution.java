import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode node = stack.peek();
                if (node.right != null && node.right != lastVisited) {
                    current = node.right;
                } else {
                    result.add(node.val);
                    lastVisited = stack.pop();
                }
            }
        }
        return result;
    }
}
