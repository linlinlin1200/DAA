import java.util.ArrayList;
import java.util.List;

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

    public static TreeNode searchBST(TreeNode root, int val) {
        TreeNode current = root;
        while (current != null) {
            if (current.val == val) {
                return current;
            }
            current = val < current.val ? current.left : current.right;
        }
        return null;
    }

    private static void inorder(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }
        inorder(root.left, values);
        values.add(root.val);
        inorder(root.right, values);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7));

        TreeNode found = searchBST(root, 2);
        List<Integer> values = new ArrayList<>();
        inorder(found, values);
        System.out.println("Search 2, subtree inorder: " + values);
        System.out.println("Search 5: " + (searchBST(root, 5) == null ? "not found" : "found"));
    }
}
