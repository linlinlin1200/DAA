import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCases {
    private static int checks;

    private static void check(boolean condition) {
        checks++;
        if (!condition) {
            throw new AssertionError("Check " + checks + " failed");
        }
    }

    // Check balance at EVERY node, not only at the root.
    private static int inspect(Main.TreeNode root, List<Integer> inorder) {
        if (root == null) {
            return -1;
        }
        int leftHeight = inspect(root.left, inorder);
        inorder.add(root.val);
        int rightHeight = inspect(root.right, inorder);
        check(Math.abs(leftHeight - rightHeight) <= 1);
        return 1 + Math.max(leftHeight, rightHeight);
    }

    private static void test(int[] input) {
        int[] original = input.clone();
        Main.TreeNode root = Main.sortedArrayToBST(input);
        List<Integer> values = new ArrayList<>();
        inspect(root, values);
        check(values.size() == input.length);
        for (int i = 0; i < input.length; i++) {
            check(input[i] == values.get(i));
        }
        check(Arrays.equals(original, input));
    }

    public static void main(String[] args) {
        test(new int[0]);
        test(new int[]{0});
        test(new int[]{1, 3});
        test(new int[]{-10, -3, 0, 5, 9});
        for (int length = 3; length <= 100; length++) {
            int[] input = new int[length];
            for (int i = 0; i < length; i++) {
                input[i] = i - 50;
            }
            test(input);
        }
        int[] large = new int[10000];
        for (int i = 0; i < large.length; i++) {
            large[i] = i - 5000;
        }
        test(large);
        System.out.println("Sorted array to BST: " + checks + " checks passed");
    }
}
