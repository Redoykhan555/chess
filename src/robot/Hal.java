package robot;

import engine.Minimax;
import game.Player;
import helper.Board;
import helper.State;
import helper.Warrior;

public class Hal implements Player{
	Character color;
	private boolean kingMoved = false;
	
	public String move(String board) {
		Warrior[][] w = Board.sToW(board);
		/*for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) System.out.print(w[i][j]+",");
			System.out.println();
		}*/
		State s =  (State) Minimax.go(new State(w,this.color,kingMoved));
		if(s==null) return null;
		if(s.kingMoved) { kingMoved = true;}
		return Board.wToS(s.board);
	}
	
	public void init(Character c) {
		color = c;
		
	}
}
