package chess;
import java.util.*;
import gui.Window;

public class Game {
	Team black,white;
	Warrior[][] board;
	static Window w = new Window();
	
	public Game() {
		this.black = new Team('b');
		this.white = new Team('w');
		this.board = new Warrior[8][8];
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) board[i][j]=null;
		}
		black.init(board);
		white.init(board);
	}
	
	public void run() {
		while (true){
			w.paint(board);
			w.wait_for();
			int fx=w.fx,fy=w.fy,tx=w.tx,ty=w.ty;
			State s = new State(board,white.c).upd(new Move(new Pos(fx,fy),new Pos(tx,ty)));
			if(s==null) return;
			board = s.board;
			w.paint(board);
			
			
			s = black.move(board);
			
			if(s==null) return ;
			board = s.board;		
		}
	}
	public static void main(String[] args) {
		Game g = new Game();
		g.run();
		w.over();
	}
	
}










