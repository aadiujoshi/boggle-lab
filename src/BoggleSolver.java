import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class BoggleSolver
{
	private String[] dictionary;

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName)
	{
		try {
			File dict = new File(dictionaryName);

			Scanner in = new Scanner(dict);

			ArrayList<String> loaded = new ArrayList<>();

			while(in.hasNext()){
				loaded.add(in.next());
			}

			dictionary = new String[loaded.size()];

			System.arraycopy(loaded.toArray(), 0, dictionary, 0, dictionary.length);

		} catch (IOException e){
			System.out.println("stfu");
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board)
	{
		HashSet<String> words = new HashSet<>();

		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				solve(board, words, board.getLetter(i, j)+"", i, j);
			}
		}

		return words;
	}

	private int checkWord(String word){
		for (int i = 0; i < dictionary.length; i++) {
			if(dictionary[i].length() < word.length())
				continue;

			int c = 0;

			for (; c < word.length(); c++) {
				if(dictionary[i].charAt(c) == word.charAt(c))
					break;
			}

			if(c == dictionary[i].length())
				return 2;

			if(c == word.length())
				return 1;
		}

		return 0;
	}

	private String solve(BoggleBoard board, HashSet<String> list, String prev, int row, int col){

		HashSet<String> local = new HashSet<>();

		for (int r = row-1; r < row+1; r++) {
			for (int c = col-1; c < col+1; c++) {

				if(!(r > -1 && r < board.rows() &&
					c > -1 && c < board.cols()))
						continue;

				String n = board.getLetter(r, c) == 'Q' ? "QU": board.getLetter(r, c)+"";

				int status = checkWord(prev+n);
				
				//invalid permuatation
				if(status == 0)
					continue;

				//correct word
				if(status == 2)
					local.add(prev+n);

				list.add(solve(board, list, prev+n, r, c));
			}
		}
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		//TODO

		return 0;
	}

	public static void main(String[] args) {
		System.out.println("WORKING");

		final String PATH   = "data/";
		BoggleBoard  board  = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); //should print 84

		//new BoggleGame(4, 4);
	}

}
