import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Run with: java week6/RunTests.java (JDK 17 or newer). */
public class RunTests {
    private static final List<String> TASKS = List.of(
        "01-inorder-traversal",
        "02-maximum-depth",
        "03-minimum-depth",
        "04-path-sum",
        "05-preorder-traversal",
        "06-postorder-traversal",
        "07-invert-binary-tree",
        "08-binary-tree-paths",
        "09-sum-of-left-leaves",
        "10-merge-two-binary-trees"
    );

    public static void main(String[] args) throws Exception {
        Path week = Files.isDirectory(Path.of("week6", "common"))
            ? Path.of("week6") : Path.of(".");
        if (!Files.isRegularFile(week.resolve("common/TreeNode.java"))) {
            throw new IllegalStateException("Run from the repository root or the week6 folder.");
        }
        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
        Path javaBin = Path.of(System.getProperty("java.home"), "bin");
        String javac = javaBin.resolve(windows ? "javac.exe" : "javac").toString();
        String java = javaBin.resolve(windows ? "java.exe" : "java").toString();

        for (String task : TASKS) {
            Path folder = week.resolve(task);
            Path build = week.resolve(".build").resolve(task);
            Files.createDirectories(build);
            System.out.println("Testing " + task);
            run(List.of(javac, "--release", "17", "-encoding", "UTF-8", "-Xlint:all", "-Werror",
                "-d", build.toString(),
                week.resolve("common/TreeNode.java").toString(),
                week.resolve("common/TestSupport.java").toString(),
                folder.resolve("Solution.java").toString(),
                folder.resolve("TestCases.java").toString()));
            run(List.of(java, "-ea", "-cp", build.toString(), "TestCases"));
        }
        System.out.println("PASS: all " + TASKS.size() + " tasks compiled and passed their tests.");
    }

    private static void run(List<String> command) throws IOException, InterruptedException {
        int result = new ProcessBuilder(command).inheritIO().start().waitFor();
        if (result != 0) {
            throw new IllegalStateException("Command failed (exit " + result + "): " + command.get(0));
        }
    }
}
