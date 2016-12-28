
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TicTacToe.java
// File:             TicTacToe.java
// Semester:         CS540 Fall 2016
// Section:          CS540-1
// Homework Number:  5
// Submission Date:  October 27th, 2016
//
// Author:           Junxiong Huang / jhuang292@wisc.edu
// WiscUserName:     jhuang292
// CS Login:         junxiong
// Lecturer's Name:  Jerry Zhu
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                 
//
// Description:      Alpha-Beta Pruning for Tic-Tac-Toe Game
//                   
//
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;
import java.util.ArrayList;

/*
 * Board Class
 */
class Board {
	// Define 2-dimensional Array
	private char[][] board;
	// The score value stored in the array
	private int score;

	private List<Board> allChildlist;

	// Constructor to initialize the board object
	public Board() {
		// Initialize score value
		this.score = 0;

		// Initialize the child list
		this.allChildlist = new ArrayList<Board>();

		this.board = new char[3][4];

		// Initialize board array
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = '_';
			}
		}

		board[1][0] = '#';

	}

	// Add child to the list to save the board object into list
	public void addChild(Board s) {
		allChildlist.add(s);
	}

	// Return the childList
	public List<Board> getChildlist() {
		return allChildlist;
	}

	// Board value evaluate 'O' or 'X'
	public boolean evaluate(char c) {
		// Row Check
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][1] == board[i][2] && board[i][1] == c
						&& ((board[i][1] == board[i][0] && board[i][1] != board[i][3])
								|| (board[i][1] != board[i][0] && board[i][1] == board[i][3]))) {
					return true;
				}
			}
		}

		// Column Check
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[0][j] == 'c') {
					return true;
				}
			}
		}

		// Diagonal Check
		if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] == c)
				|| (board[0][1] == board[1][2] && board[1][2] == board[2][3] && board[1][2] == c)
				|| (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[1][1] == c)
				|| (board[2][1] == board[1][2] && board[1][2] == board[0][3] && board[1][2] == c)) {
			return true;
		}

		return false;
	}

	// Read values from the arguments and save them to board array
	public void readBoard(char[][] c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = c[i][j];
			}
		}
	}

	// Print board
	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	// Player O win the game
	public boolean oHasWon() {
		if (evaluate('O') == true) {
			return true;
		} else
			return false;
	}

	// Player X win the game
	public boolean xHasWon() {
		if (evaluate('X') == true) {
			return true;
		} else
			return false;
	}

	// Board full
	public boolean full() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == '_') {
					return false;
				}
			}
		}
		return true;
	}

	// Neither of the players wins
	public boolean neitherHasWon() {
		if (!xHasWon() && !oHasWon() && full()) {
			return true;
		} else
			return false;
	}

	// Game over when any one wins or board is full
	public boolean isTerminal() {
		if (oHasWon() || xHasWon() || neitherHasWon()) {
			return true;
		} else
			return false;
	}

	// Get the score for terminal state
	public int getTerminalScore() {
		int s;
		if (xHasWon()) {
			s = -1;
		} else if (oHasWon()) {
			s = 1;
		} else
			s = 0;

		// Update the score
		score = s;
		return s;
	}

	// Set the board score
	public void setScore(int score) {
		this.score = score;
	}

	// Return the board score
	public int getScore() {
		return score;
	}

	// Determine next move is O or not
	public boolean oMove() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == '_') {
					count++;
				}
			}
		}

		if (count % 2 == 1) {
			return true;
		} else
			return false;
	}

	// Set label for the certain index
	public void setLabel(int row, int col, char c) {
		board[row][col] = c;
	}

	// Get the label at certain index
	public char getLabel(int row, int col) {
		return board[row][col];
	}

	// Copy board
	public void copyBoard(Board b) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				this.setLabel(i, j, b.getLabel(i, j));
			}
		}
		this.score = b.getScore();
	}

	// Get the successor of the current board
	public List<Board> getSucc() {
		List<Board> successor = new ArrayList<Board>();

		// Determine the next move
		char c;
		if (oMove()) {
			c = 'O';
		} else
			c = 'X';

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (board[i][j] == '_') {
					Board b = new Board();
					b.copyBoard(this);
					b.setLabel(i, j, c);
					successor.add(b);
				}
			}
		}
		return successor;
	}

}

public class TicTacToe {

	// Alpha-beta pruning Max-Value
	// s: current state in game, Max about to play
	// alpha: best score (highest) for Max along path to s
	// beta: best score (lowest) for Min along path to s
	public static int maxValue(Board s, int alpha, int beta, boolean output) {

		// If (s is a terminal state ) then return (terminal value of s)
		if (s.isTerminal()) {
			if (output) {
				s.printBoard();
				System.out.println("Alpha: " + alpha + " Beta: " + beta);
			}
			s.setScore(s.getTerminalScore());
			return s.getTerminalScore();
		}
		// else for each's in Succ(s)
		else {
			List<Board> mySucc = s.getSucc();
			Iterator<Board> itr = mySucc.iterator();
			Board temp = null;
			while (itr.hasNext()) {
				temp = itr.next();
				s.addChild(temp);
				alpha = minValue(temp, alpha, beta, output);
				temp.setScore(alpha);
				if (alpha >= beta) {
					s.setScore(alpha);

					if (output) {
						s.printBoard();
						System.out.println("Alpha: " + alpha + " Beta: " + beta);
					}
					return beta;
				}
			}
			s.setScore(alpha);
			if (output) {
				s.printBoard();
				System.out.println("Alpha: " + alpha + " Beta: " + beta);
			}
			return alpha;
		}

	}

	public static int minValue(Board s, int alpha, int beta, boolean output) {
		// if (s is a terminal state) then return (terminal value of s)
		if (s.isTerminal()) {
			s.setScore(s.getTerminalScore());
			if (output) {
				s.printBoard();
				System.out.println("Alpha: " + alpha + " Beta: " + beta);
			}
			return s.getTerminalScore();
		} else {
			List<Board> mySucc = s.getSucc();
			Iterator<Board> itr = mySucc.iterator();
			Board temp = null;

			while (itr.hasNext()) {
				temp = itr.next();
				s.addChild(temp);
				beta = maxValue(temp, alpha, beta, output);
				temp.setScore(beta);

				if (alpha >= beta) {
					s.setScore(beta);

					if (output) {
						s.printBoard();
						System.out.println("Alpha: " + alpha + " Beat: " + beta);
					}
					return alpha;
				}
			}
			s.setScore(beta);

			if (output) {
				s.printBoard();
				System.out.println("Alpha: " + alpha + " beta: " + beta);
			}
			return beta;
		}
	}

	public static void main(String[] args) {
		// Error check for input
		if (args.length != 13) {
			System.out.println("Invalid arguments.");
			System.exit(-1);
		}

		int argIndex = 0;

		// Board board = new Board();
		char[][] board = new char[3][4];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = args[argIndex].charAt(0);
				argIndex++;
			}
		}

		// Read the last character to output or not
		boolean output = false;
		if (args[12].charAt(0) == 'Y') {
			output = true;
		} else
			output = false;

		Board myBoard = new Board();
		myBoard.readBoard(board);

		// minimax algorithm to print alpha-beta pruning values
		minValue(myBoard, -2, 2, output);

		List<Board> list = myBoard.getChildlist();
		int myscore;
		int bestscore = 1000000;

		for (int i = 0; i < list.size(); i++) {
			myscore = list.get(i).getScore();
			if (myscore < bestscore) {
				bestscore = myscore;
			}
		}

		System.out.println("SOLUTION");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getScore() == bestscore) {
				list.get(i).printBoard();
				break;
			}
		}
	}
}
