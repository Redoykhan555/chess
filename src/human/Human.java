package human;

import helper.State;
import helper.Warrior;
import game.Player;
import helper.Board;
import helper.Pos;


public class Human implements Player{
	Window w;
	Character color;
	boolean kingMoved = false;
	public Human() {
		w = new Window();
	}
	
	private State checkPawnPromotion(State s) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++){
				if (i==7 && s.board[i][j]!=null && s.board[i][j].c==color && s.board[i][j].w==1) {
					char c = (char)(w.pawnPromotion()+32);
					System.out.println("PRO:"+c);
					s.board[i][j]= Warrior.build(c,color);
				}
			}
		}
		return s;
	}
	
	void castle(State s,int fx,int fy,int tx,int ty) {
		if(fy==4 && (fx==0 || fx==7) && (tx==fx) &&
				((ty==2)||(ty==6))&& s.board[tx][ty].w==2 ){
			
			if(ty==2) {s.board[fx][3] = s.board[fx][0];s.board[fx][0]=null;}
			else if(ty==6) {s.board[fx][5] = s.board[fx][7];s.board[fx][7]=null;}
		}
		
	}
	
	public String move(String board) {
		w.paint(Board.strToArr(board));
		w.wait_for();
		int fx=w.fx,fy=w.fy,tx=w.tx,ty=w.ty;
		State s = Board.four(board, fx, fy, tx, ty,kingMoved);
		castle(s,fx,fy,tx,ty);
		State ss = checkPawnPromotion(s);
		if(ss.kingMoved) { kingMoved = true;}
		String ans = Board.wToS(s.board);
		w.paint(Board.strToArr(ans));
		return ans;
	}

	public void init(Character c) {
		color = c;
		
	}
	
}
