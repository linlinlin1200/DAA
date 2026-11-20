import java.util.ArrayList;
import java.util.Arrays;
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

    public static TreeNode sortedArrayToBST(int[] nums) {
        return build(nums, 0, nums.length - 1);
    }

    private static TreeNode build(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int middle = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = build(nums, left, middle - 1);
        root.right = build(nums, middle + 1, right);
        return root;
    }

    private static void inorder(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }
        inorder(root.left, values);
        values.add(root.val);
        inorder(root.right, values);
    }

    private static void printTree(TreeNode root, String indent, String side) {
        if (root == null) {
            return;
        }
        System.out.println(indent + side + root.val);
        printTree(root.left, indent + "  ", "L: ");
        printTree(root.right, indent + "  ", "R: ");
    }

    public static void main(String[] args) {
        int[] nums = {-10, -3, 0, 5, 9};
        TreeNode root = sortedArrayToBST(nums);
        System.out.println("Input: " + Arrays.toString(nums));
        printTree(root, "", "root: ");
        List<Integer> values = new ArrayList<>();
        inorder(root, values);
        System.out.println("Inorder: " + values);
    }
}
