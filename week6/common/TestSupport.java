import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/** Small helpers used only by the local tests. */
public final class TestSupport {
    private static int checks;

    private TestSupport() {}

    public static TreeNode tree(Integer... values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int index = 1;
        while (!queue.isEmpty() && index < values.length) {
            TreeNode node = queue.remove();
            if (values[index] != null) {
                node.left = new TreeNode(values[index]);
                queue.add(node.left);
            }
            index++;
            if (index < values.length && values[index] != null) {
                node.right = new TreeNode(values[index]);
                queue.add(node.right);
            }
            index++;
        }
        return root;
    }

    public static List<Integer> serialize(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        if (root == null) {
            return values;
        }
        // LinkedList accepts null, which marks missing children here.
        Deque<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node == null) {
                values.add(null);
            } else {
                values.add(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        int end = values.size();
        while (end > 0 && values.get(end - 1) == null) {
            end--;
        }
        return new ArrayList<>(values.subList(0, end));
    }

    public static void equal(Object expected, Object actual, String label) {
        checks++;
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(label + ": expected " + expected + ", got " + actual);
        }
    }

    public static void check(boolean condition, String label) {
        equal(true, condition, label);
    }

    public static int count() {
        return checks;
    }

    public static TreeNode chain(int length, int value, boolean left) {
        TreeNode root = null;
        for (int i = 0; i < length; i++) {
            root = left ? new TreeNode(value, root, null) : new TreeNode(value, null, root);
        }
        return root;
    }
}
