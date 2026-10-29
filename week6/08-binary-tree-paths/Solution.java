import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root != null) {
            collect(root, new StringBuilder(), paths);
        }
        return paths;
    }

    private void collect(TreeNode node, StringBuilder path, List<String> paths) {
        int oldLength = path.length();
        if (oldLength > 0) {
            path.append("->");
        }
        path.append(node.val);

        if (node.left == null && node.right == null) {
            paths.add(path.toString());
        } else {
            if (node.left != null) {
                collect(node.left, path, paths);
            }
            if (node.right != null) {
                collect(node.right, path, paths);
            }
        }
        path.setLength(oldLength);
    }
}
