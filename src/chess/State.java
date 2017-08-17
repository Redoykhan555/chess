package chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import engine.Node;


public class State implements Node,Serializable{
	public Character c;
	int inf = 99999999;
	int alpha=-inf,beta = inf;
	
	Warrior[][] board = new Warrior[8][8];
	
	public State(Warrior[][] board,Character c) {
		this.board = board;
		this.c=c;
	}
	public ArrayList<State> children(int x){
		ArrayList<Move> ans = new ArrayList<Move>();
		ArrayList<State> temp = new ArrayList<State>();
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				Warrior w = board[i-1][j-1];
				if (!(w!=null && w.c==this.c)) continue;
				ArrayList<Pos> s = w.moves(board);
				System.out.println(i+","+j+" : "+s.size());
				for (Pos p:s) {
					ans.add(new Move(new Pos(i,j),p));
				}
			}
		}
		for(Move m:ans) {
			//System.err.println(m.from + "to" + m.to);
			temp.add(upd(m));
		}
		return temp;
	}
	public ArrayList<State> children(){
		ArrayList<Move> ans = new ArrayList<Move>();
		ArrayList<State> temp = new ArrayList<State>();
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				Warrior w = board[i-1][j-1];
				if (!(w!=null && w.c==this.c)) continue;
				ArrayList<Pos> s = w.moves(board);
				for (Pos p:s) {
					ans.add(new Move(new Pos(i,j),p));
				}
			}
		}
		for(Move m:ans) {
			temp.add(upd(m));
		}
		return temp;
	}
	
	State upd(Move m) {
		Warrior[][] brd = new Warrior[8][8];
		for(int i=1;i<=8;i++) {
			for (int j=1;j<=8;j++) {
				brd[i-1][j-1] = board[i-1][j-1];
			}
		}

		Pos prev = m.from,nxt = m.to;
		Warrior w = brd[prev.x-1][prev.y-1];
		brd[nxt.x-1][nxt.y-1]=w;
		brd[prev.x-1][prev.y-1]= null;
		return new State(brd,(char)(217-this.c));
	}
	public boolean maxPlayer() {
		return this.c=='w';
	}

	
	private boolean terminal() {
		int d=0;
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				Warrior w = this.board[i-1][j-1];
				if(w!=null && w.w==2) d++;
			}
		}
		//System.out.println("TERMINATED");
		return d<2;
	}
	public int heuristic() {
		if(terminal()) {
			if(this.c=='w') return -inf;
			if(this.c=='b') return inf;
		}
		
		int ans = 0;
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				Warrior w = this.board[i-1][j-1];
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











