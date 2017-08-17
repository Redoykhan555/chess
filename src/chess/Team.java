package chess;

import java.util.HashMap;
import java.util.Scanner;
import engine.Minimax;

public class Team {
	Character c;
	Scanner inp = new Scanner(System.in);
	public Team(Character c ) {
		this.c=c;
	}
	
	public void init(Warrior[][] board) {
		int p=1,q=2;
		if (this.c=='b') {p=8;q=7;}
		for(int i=1;i<=8;i++) {
			board[q-1][i-1]=new Soldier(this.c,1);
		}
		board[p-1][0]=new Rook(this.c,4);
		board[p-1][7]=new Rook(this.c,4);
		board[p-1][1]=new Knight(this.c,4);
		board[p-1][6]=new Knight(this.c,4);
		board[p-1][2]=new Bishop(this.c,4);
		board[p-1][5]=new Bishop(this.c,4);
		board[p-1][3]=new Queen(this.c,8);
		board[p-1][4]=new King(this.c,2);
	}
	
	public State move(Warrior[][] board) {
		if(this.c=='b') {
			State s=  (State) Minimax.go(new State(board,this.c));
			return s;
		}
		return null;
	}
}












