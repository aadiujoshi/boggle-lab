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

	//return 2 means that exist word exists
	//return 1 is a possiblility
	//return 0 is not a possible word
	private int checkWord(String word){
		for (int i = 0; i < dictionary.length; i++) {
			if(dictionary[i].length() < word.length())
				continue;

			if(dictionary[i].equals(word))
				return 2;

			if(dictionary[i].indexOf(word) == 0)
				return 1;
		}
		return 0;
	}

	private void solve(BoggleBoard board, HashSet<String> list, String prev, int row, int col){

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
					list.add(prev+n);

				if((prev+n).length() > 9)
					return;

				solve(board, list, prev+n, r, c);
			}
		}
	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		int l = word.length();

		if(l <= 2)
			return 0;
		if(l >=3 && l <=4)
			return 1;
		if(l == 5)
			return 2;
		if(l == 6)
			return 3;
		if(l == 7)
			return 5;
		if(l >= 8)
			return 11;

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
