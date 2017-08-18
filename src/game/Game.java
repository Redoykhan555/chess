package game;
import java.util.*;

public class Game {
	Player black,white;
	ArrayList<Board> states;
	public Game(Player w,Player b) {
		white = w;
		black = b;
	}
	private boolean ok(String s) {return true;}
	
	public void run() {
		while (true){
			String board = states.get(states.size()-1).toString();
			String next;
			if(states.size()%2==1) next = white.move(board);
			else next = black.move(board);
			if(ok(next)) states.add(new Board(next));
			
		}
	}
	public static void main(String[] args) {

	}
	
}











