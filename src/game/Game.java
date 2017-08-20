package game;
import java.util.*;
import helper.Board;

import robot.Hal;
import human.Human;

public class Game {
	Player black,white;
	ArrayList<String> states = new ArrayList<String>();;
	
	public Game(Player w,Player b) {
		white = w;white.init('w');
		black = b;	black.init('b');
		states.add(Board.init());
	}
	private boolean ok(String s) {return true;}
	
	public void run() {
		while (true){
			String board = states.get(states.size()-1);
			String next;
			
			if(states.size()%2==1) next = white.move(board);
			else next = black.move(board);
			
			if(next==null) {System.out.println("GAME OVER");return;}
			if(ok(next)) states.add(next);	
		}
	}
	
	
	
	public static void main(String[] args) {
		Game g = new Game(new Human(),new Hal());
		g.run();
	}
	
}











