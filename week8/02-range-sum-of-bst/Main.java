import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        }

        int sum = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            if (current.val >= low && current.val <= high) {
                sum += current.val;
            }
            // Only visit a subtree if it can contain a value in the range.
            if (current.left != null && current.val > low) {
                stack.push(current.left);
            }
            if (current.right != null && current.val < high) {
                stack.push(current.right);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(10,
                new TreeNode(5, new TreeNode(3), new TreeNode(7)),
                new TreeNode(15, null, new TreeNode(18)));
        System.out.println("Range [7, 15]: " + rangeSumBST(root, 7, 15));
        System.out.println("Range [5, 5]: " + rangeSumBST(root, 5, 5));
        System.out.println("Range [20, 30]: " + rangeSumBST(root, 20, 30));
    }
}
