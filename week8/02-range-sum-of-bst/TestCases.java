import java.util.Random;
import java.util.TreeSet;

public class TestCases {
    private static int checks;

    private static void check(int expected, int actual) {
        checks++;
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }

    private static Main.TreeNode insert(Main.TreeNode root, int value) {
        if (root == null) {
            return new Main.TreeNode(value);
        }
        if (value < root.val) {
            root.left = insert(root.left, value);
        } else if (value > root.val) {
            root.right = insert(root.right, value);
        }
        return root;
    }

    public static void main(String[] args) {
        Main.TreeNode root = new Main.TreeNode(10,
                new Main.TreeNode(5, new Main.TreeNode(3), new Main.TreeNode(7)),
                new Main.TreeNode(15, null, new Main.TreeNode(18)));
        check(32, Main.rangeSumBST(root, 7, 15));
        check(5, Main.rangeSumBST(root, 5, 5));
        check(0, Main.rangeSumBST(root, 20, 30));
        check(58, Main.rangeSumBST(root, 1, 100));
        check(0, Main.rangeSumBST(null, 1, 100));
        root = insert(root, 13);
        root = insert(root, 1);
        root = insert(root, 6);
        check(23, Main.rangeSumBST(root, 6, 10));

        Random random = new Random(938);
        TreeSet<Integer> values = new TreeSet<>();
        root = null;
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(100000) + 1;
            if (values.add(value)) {
                root = insert(root, value);
            }
        }
        for (int i = 0; i < 200; i++) {
            int a = random.nextInt(100000) + 1;
            int b = random.nextInt(100000) + 1;
            int low = Math.min(a, b);
            int high = Math.max(a, b);
            int expected = values.subSet(low, true, high, true).stream().mapToInt(Integer::intValue).sum();
            check(expected, Main.rangeSumBST(root, low, high));
        }

        Main.TreeNode chain = new Main.TreeNode(1);
        Main.TreeNode current = chain;
        for (int i = 2; i <= 20000; i++) {
            current.right = new Main.TreeNode(i);
            current = current.right;
        }
        check(200010000, Main.rangeSumBST(chain, 1, 20000));
        check(20000, Main.rangeSumBST(chain, 20000, 20000));
        System.out.println("Range sum of BST: " + checks + " checks passed");
    }
}
