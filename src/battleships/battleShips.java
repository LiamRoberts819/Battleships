package battleships;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class battleShips {

	//Configurable board size
	static String[][] board = new String[5][5];

	//Ship info ships[ship number][0 = row index, 1 = column index]
	static int[][] ships = new int[3][2];
	static int inputRow;
	static int inputColumn;
	static boolean winState = false;

	public static void main(String[] args) {

		initBoard(board);
		initShips(board);

		while (!winState){
			showBoard(board);		
			System.out.println(ships[0][0]);
			System.out.println(ships[0][1]);		
			makeGuess(board);
			checkGuess();
			checkWin();
		}
		showBoard(board);
		System.out.println("Congratulations! All ships destroyed.");

	}

	public static void initBoard(String[][] board) {

		for(int row=0; row<board.length; row++){
			for(int column=0; column<board[0].length; column++){
				board[row][column]="~";
			}
		}

	}

	public static void initShips(String[][] board) {

		for(int ship=0; ship<ships.length; ship++){

			ships[ship][0] = ThreadLocalRandom.current().nextInt(0, board.length);
			ships[ship][1] = ThreadLocalRandom.current().nextInt(0, board[0].length);

			for(int last=0; last<ship;) {

				while(ships[ship][0] == ships[last][0] && ships[ship][1] == ships[last][1]){
					ships[ship][0] = ThreadLocalRandom.current().nextInt(0, board.length);
					ships[ship][1] = ThreadLocalRandom.current().nextInt(0, board[0].length);
					last=0;

				}
				last++;
			}
		}
	}


	public static void showBoard(String[][] board) {

		breakln();

		for(int row=0; row<board.length; row++){
			for (int column=0;column<board[0].length;column++){
				System.out.print(" " + board[row][column]);
			}
			System.out.print(System.lineSeparator());
		}

		breakln();
	}

	public static void breakln(){
		System.out.println("___________________________________" + System.lineSeparator());
	}

	public static void makeGuess(String[][] board) {

		Scanner reader = new Scanner(System.in);

		inputRow = -1;
		inputColumn = -1;

		while(inputRow < 0 || inputRow > board.length) {
			System.out.println("Enter row number (integer) 1-" + board.length + ":");
			while(!reader.hasNextInt()) { 
				System.out.println("Please enter an integer between 1-" + board.length + ":");
				reader.next();
			}
			inputRow = reader.nextInt()-1;
			if(inputRow < 0 || inputRow > board.length)
				System.out.println("Invalid input:");
		}

		while(inputColumn < 0 || inputColumn > board[0].length) {
			System.out.println("Enter column number (integer) 1-" + board[0].length + ":");
			while(!reader.hasNextInt()) { 
				System.out.println("Please enter an integer between 1-" + board[0].length + ":");
				reader.next();
			}
			inputColumn = reader.nextInt()-1;
			if(inputColumn < 0 || inputColumn > board[0].length)
				System.out.println("Invalid input:");
		}
	}

	public static void checkGuess() {

		boolean hit=false;
		for(int ship=0; ship<ships.length; ship++){
			if(ships[ship][0]==inputRow && ships[ship][1]==inputColumn) {
				hit=true;
				break;
			}
		}
		if(hit) {
			switch(board[inputRow][inputColumn]){
			case "~":
				board[inputRow][inputColumn]="O";
				System.out.println("Shot Hit!");
				break;
			case "O":
				System.out.println("Ship already hit here, try another co-ordinate");
				break;
			}
		}
		else {
			switch (board[inputRow][inputColumn]){
			case "~":
				board[inputRow][inputColumn]="X";
				System.out.println("Miss.");

				break;
			case "X":
				System.out.println("Already guessed here, try another co-ordinate");
				break;				
			}
		}
	}

	public static void checkWin() {
		winState = true;
		for(int ship=0; ship<ships.length; ship++){	
			if (board[ships[ship][0]][ships[ship][1]]=="~"){
				winState = false;
			}				
		}
	}
}
