package helper;
/*
 if(w.w==1 && p.x==lastRow) {
						char[] arr = {'q','r','b','k'};
						for(Character c:arr) {
							Move m = new Move(new Pos(i+1,j+1),p);m.type=1;m.c=c;
							ans.add(m);
						}	
					}
 */

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Warrior{
	public final Character c;
	public final int w;
	public Warrior(Character c,int w) {
		this.c = c;
		this.w = w;
	}
	
	public static Warrior build(char t,char c) {
		if(t=='r') return new Rook(c);
		if(t=='b') return new Bishop(c);
		if(t=='q') return new Queen(c);
		if(t=='k') return new Knight(c);
		return null;
	}
	
	boolean limit (Pos p) {
		return p.x<8 && p.x>=0 && p.y>=0 && p.y<8;
	}
	boolean blank (Pos p,Warrior[][] board) {
		return limit(p) && board[p.x][p.y]==null;
	}
	abstract ArrayList<Move> moves (Warrior[][] board);
	
	ArrayList<Pos> line(Function func,Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos succ = func.call(findp(board));
		while (blank(succ,board)) {
			ans.add(succ);
			succ = func.call(succ);
		}
		if (limit(succ) && board[succ.x][succ.y].c!=this.c) ans.add(succ);
		return ans;
	}
	
	Pos findp(Warrior[][] board) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(board[i][j]==this) return new Pos(i,j);
			}
		}
		return null;
	}
	
	ArrayList<Pos> move_help(ArrayList<Function> funcs,
			Warrior[][] board){
		ArrayList<Pos> ans = new ArrayList<Pos>();
		for (Function f:funcs) {
			ans.addAll(line(f,board));
		}
		return ans;
	}
	
	ArrayList<Move> ptom(ArrayList<Pos> arr,Pos from){
		ArrayList<Move> ans = new ArrayList<Move>();
		for(Pos to:arr) {
			ans.add(new Move(from,to));
		}
		return ans;
	}
	
	public String toString() {return "Implement This Shit";}	
}

class Soldier extends Warrior{
	public Soldier(Character c) {super(c, 1);}
	private int ford(Warrior[][] board,int v) {
		Pos p = findp(board);
		if(this.c=='b') return p.x-v;
		return p.x+v;
	}
	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		ArrayList<Move> temp = new ArrayList<Move>();
		Pos p = findp(board);
		
		int row = ford(board,1);
		if(row>7 || row<0) return ptom(ans,p);
		
		
		if(p.y<7 && board[row][p.y+1]!=null && board[row][p.y+1].c!=this.c) ans.add(new Pos(row,p.y+1));
		if(p.y>0 && board[row][p.y-1]!=null && board[row][p.y-1].c!=this.c) ans.add(new Pos(row,p.y-1));
		
		if(board[row][p.y]==null) {
			ans.add(new Pos(row,p.y));
			if((p.x==1 && this.c=='w')||(p.x==6&&this.c=='b')) {
				int rw2 = ford(board,2);
				if(board[rw2][p.y]==null) ans.add(new Pos(rw2,p.y));
			}
		}
		for(Pos ps:ans) {
			if((ps.x==0 && this.c=='b')||(ps.x==7&&this.c=='w')) {
				//Pawn Promotion
				char [] ch = {'q','b','k','r'};
				for(Character juj:ch) {
					temp.add(new Move(p,ps,1,juj));
				}
			}
		}
		
		temp.addAll(ptom(ans.stream().filter(q->limit(q)).collect(Collectors.toCollection(ArrayList::new)),p));
		return temp;
	}
	
	public String toString() {return this.c+"pawn";}
}

class Rook extends Warrior{
	public Rook(Character c) {super(c, 4);}//change Castling rule in state if 4 changes
	
	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Function> funcs = new ArrayList<Function> ();
		
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x,p.y+1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y);}});
		return ptom(move_help(funcs, board),findp(board));
	}
	public String toString() {return this.c+"rook";}
}

class Knight extends Warrior{

	public Knight(Character c) {super(c, 3);}

	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos p = findp(board);
		ans.add(new Pos(p.x-2,p.y-1));ans.add(new Pos(p.x-2,p.y+1));
		ans.add(new Pos(p.x+2,p.y-1));ans.add(new Pos(p.x+2,p.y+1));
		ans.add(new Pos(p.x-1,p.y-2));ans.add(new Pos(p.x-1,p.y+2));
		ans.add(new Pos(p.x+1,p.y-2));ans.add(new Pos(p.x+1,p.y+2));
		
		return ptom(ans.stream().filter(
				q->limit(q) &&(
					board[q.x][q.y]==null || board[q.x][q.y].c!=this.c)).collect(
							Collectors.toCollection(ArrayList::new)),p);
	}
	public String toString() {return this.c+"knight";}
	
}

class Bishop extends Warrior{
	public Bishop(Character c) {super(c, 3);}
	
	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Function> funcs = new ArrayList<Function> ();
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y+1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y+1);}});
		return ptom(move_help(funcs, board),findp(board));
	}
	public String toString() {return this.c+"bishop";}
}

class King extends Warrior{

	public King(Character c) { super(c, 2);}

	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos p = findp(board);
		ans.add(new Pos(p.x-1,p.y+1));ans.add(new Pos(p.x+1,p.y-1));
		ans.add(new Pos(p.x-1,p.y));ans.add(new Pos(p.x+1,p.y));
		ans.add(new Pos(p.x-1,p.y-1));ans.add(new Pos(p.x+1,p.y+1));
		ans.add(new Pos(p.x,p.y+1));ans.add(new Pos(p.x,p.y-1));
		
		return ptom(ans.stream().filter(
				q->limit(q) &&(
					board[q.x][q.y]==null || board[q.x][q.y].c!=this.c)).collect(
							Collectors.toCollection(ArrayList::new)),p);
	}
	public String toString() {return this.c+"king";}
	
}

class Queen extends Warrior{

	public Queen(Character c) {super(c, 8);}

	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Function> funcs = new ArrayList<Function> ();
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y+1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y+1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x,p.y+1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x,p.y-1);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x+1,p.y);}});
		funcs.add(new Function() {Pos call (Pos p) {return new Pos(p.x-1,p.y);}});
		return ptom(move_help(funcs, board),findp(board));
	}
	public String toString() {return this.c+"queen";}
	
}















