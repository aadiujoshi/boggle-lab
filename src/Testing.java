import java.util.ArrayList;

public class Testing {

    public static void main(String[] args) {
        BoggleBoard board = new BoggleBoard("data/board-points777.txt");
        BoggleSolver solver = new BoggleSolver("data/dictionary-yawl.txt");

        Iterable<String> words = solver.getAllValidWords(board);

        System.out.println(words);

        for (String s: words) {
            System.out.print(solver.scoreOf(s) + " ");
        }
    }
}
