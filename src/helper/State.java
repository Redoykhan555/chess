package helper;


import java.util.ArrayList;

import helper.Pos;
import helper.State;
import engine.Node;


public class State implements Node{
	public final Character c;
	final int inf = 99999999;
	int alpha=-inf,beta = inf;
	final Character kingNai;
	public final boolean kingMoved;
	public Warrior[][] board = new Warrior[8][8];
	
	public State(Warrior[][] board,Character c,boolean kmove) {
		this.board = board;
		this.c=c;
		kingNai = null;
		kingMoved = kmove;
	}
	public State(Warrior[][] board,Character c,boolean kmove,Character kingless) {
		this.board = board;
		this.c=c;
		this.kingNai = kingless;
		kingMoved = kmove;
	}

	public ArrayList<State> children(){
		ArrayList<Move> ans = new ArrayList<Move>();
		ArrayList<State> temp = new ArrayList<State>();
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Warrior w = board[i][j];
				if (w!=null && w.c==this.c) {
					ArrayList<Move> s = w.moves(board);
					ans.addAll(s);
				}
			}
		}	
		//check for castling
		if(kingMoved==false && kingNai==null) {
			int row = 0;
			if(this.c=='b') row = 7;
			if(this.board[row][5]==null && this.board[row][6]==null && 
					this.board[row][7]!=null && this.board[row][7].c==this.c && this.board[row][7].w==4) {
				ans.add(new Move(null,null,2,'7'));
			}
			if(this.board[row][3]==null && this.board[row][2]==null && this.board[row][1]==null &&
					this.board[row][0]!=null && this.board[row][0].c==this.c && this.board[row][0].w==4) {
				ans.add(new Move(null,null,2,'0'));
			}
		}
		
		for(Move m:ans) {
			temp.add(upd(m));
		}
		return temp;
	}
	
	
	public State upd(Move m) {
		Warrior[][] brd = new Warrior[8][8];
		for(int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				brd[i][j] = board[i][j];
			}
		}

		if(m.type==2) {
			//castling
			int row = 0;
			if(this.c=='b') row = 7;
			int col = (int)m.c-48;
			System.out.println("Castling at:"+col);
			Warrior king = brd[row][4];brd[row][4] = null;
			Warrior rook = brd[row][col];brd[row][col] = null;
			System.out.println(king+" "+rook);
			if(col==0) {brd[row][2] = king;brd[row][3] = rook;}
			if(col==7) {brd[row][6] = king;brd[row][5] = rook;}
			return new State(brd,(char)(217-this.c),true);
		}
		
		Pos prev = m.from,nxt = m.to;
		Warrior w = brd[prev.x][prev.y];
		Warrior nw = brd[nxt.x][nxt.y];
		brd[nxt.x][nxt.y]=w;
		brd[prev.x][prev.y]= null;
		
		if(m.type==1) {
			System.out.println("Pro"+m.c+","+prev+","+nxt);
			brd[nxt.x][nxt.y]=null;			
			if(m.c=='q') {brd[nxt.x][nxt.y]= new Queen(this.c);}
			if(m.c=='b') {brd[nxt.x][nxt.y]= new Bishop(this.c);}
			if(m.c=='k') {brd[nxt.x][nxt.y]= new Knight(this.c);}
			if(m.c=='r') {brd[nxt.x][nxt.y]= new Rook(this.c);}
		}

		boolean kv=kingMoved;
		if(w.w==2) kv = true;
		if(nw!=null && nw.w==2) return new State(brd,(char)(217-this.c),kv,nw.c);
		return new State(brd,(char)(217-this.c),kv);
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











