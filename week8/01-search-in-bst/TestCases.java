import java.util.Random;

public class TestCases {
    private static int checks;

    private static void check(boolean condition) {
        checks++;
        if (!condition) {
            throw new AssertionError("Check " + checks + " failed");
        }
    }

    public static void main(String[] args) {
        Main.TreeNode left = new Main.TreeNode(2, new Main.TreeNode(1), new Main.TreeNode(3));
        Main.TreeNode root = new Main.TreeNode(4, left, new Main.TreeNode(7));
        check(Main.searchBST(root, 2) == left);
        check(Main.searchBST(root, 4) == root);
        check(Main.searchBST(root, 5) == null);
        check(Main.searchBST(null, 1) == null);
        check(Main.searchBST(root, 1) == left.left);
        check(Main.searchBST(root, 7) == root.right);

        Main.TreeNode chain = new Main.TreeNode(1);
        Main.TreeNode current = chain;
        Random random = new Random(700);
        Main.TreeNode[] nodes = new Main.TreeNode[5000];
        nodes[0] = chain;
        for (int i = 1; i < nodes.length; i++) {
            current.right = new Main.TreeNode(i + 1);
            current = current.right;
            nodes[i] = current;
        }
        check(Main.searchBST(chain, 5000) == current);
        check(Main.searchBST(chain, 5001) == null);
        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(nodes.length);
            check(Main.searchBST(chain, index + 1) == nodes[index]);
        }
        System.out.println("Search in BST: " + checks + " checks passed");
    }
}
