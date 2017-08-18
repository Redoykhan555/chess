package chess;


import java.util.ArrayList;

import engine.Node;


public class State implements Node{
	public Character c;
	int inf = 99999999;
	int alpha=-inf,beta = inf;
	Character kingNai = null;
	
	Warrior[][] board = new Warrior[8][8];
	
	public State(Warrior[][] board,Character c) {
		this.board = board;
		this.c=c;
	}
	public State(Warrior[][] board,Character c,Character kingless) {
		this.board = board;
		this.c=c;
		this.kingNai = kingless;
	}

	public ArrayList<State> children(){
		//Pawn Promotion
		int lastRow = 8;
		if(this.c=='b') lastRow = 1;
		ArrayList<Move> ans = new ArrayList<Move>();
		ArrayList<State> temp = new ArrayList<State>();
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Warrior w = board[i][j];
				if (!(w!=null && w.c==this.c)) continue;
				ArrayList<Move> s = w.moves(board);
				ans.addAll(s);
			}
		}	
		for(Move m:ans) {
			temp.add(upd(m));
		}
		return temp;
	}
	
	State upd(Move m) {
		Warrior[][] brd = new Warrior[8][8];
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				brd[i][j] = board[i][j];
			}
		}

		Pos prev = m.from,nxt = m.to;
		Warrior w = brd[prev.x-1][prev.y-1];
		Warrior nw = brd[nxt.x-1][nxt.y-1];
		brd[nxt.x-1][nxt.y-1]=w;
		brd[prev.x-1][prev.y-1]= null;
		
		if(m.type==1) {
			brd[nxt.x-1][nxt.y-1]=null;
			
			if(m.c=='q') {brd[nxt.x-1][nxt.y-1]= new Queen(this.c,8);}
			if(m.c=='b') {brd[nxt.x-1][nxt.y-1]= new Bishop(this.c,3);}
			if(m.c=='k') {brd[nxt.x-1][nxt.y-1]= new Knight(this.c,3);}
			if(m.c=='r') {brd[nxt.x-1][nxt.y-1]= new Rook(this.c,4);}
			System.out.println("PROMOTION:"+m.c+" "+brd[nxt.x-1][nxt.y-1]);
		}
		
		else if(m.type==2) {
			//Castling
		}
		
		if(nw!=null && nw.w==2) return new State(brd,(char)(217-this.c),nw.c);
		return new State(brd,(char)(217-this.c));
	}
	public boolean maxPlayer() {
		return this.c=='w';
	}

	public boolean terminated() {
		return kingNai!=null;
	}
	
	private boolean noKing(char ch) {
		return (kingNai!=null && kingNai==ch);
	}
	public int heuristic() {
		if(noKing('w'))  return -inf;
		if(noKing('b')) return inf;
		
		int ans = 0;
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Warrior w = this.board[i][j];
				if(w==null) continue;
				if(w.c=='w') ans += w.w;
				if(w.c=='b') ans -= w.w;
			}
		}	
		return ans;
	}

	public int getAlpha() {
		return alpha;
	}
	public int getBeta() {
		return beta;
	}
	
	public void setAlpha(int x) {
		this.alpha = x;
	}
	
	public void setBeta(int x) {
		this.beta = x;
	}
}











