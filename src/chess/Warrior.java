package chess;
/*
 if(w.w==1 && p.x==lastRow) {
						char[] arr = {'q','r','b','k'};
						for(Character c:arr) {
							Move m = new Move(new Pos(i+1,j+1),p);m.type=1;m.c=c;
							ans.add(m);
						}	
					}
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import engine.Minimax;
import engine.Node;

public abstract class Warrior{
	public final Character c;
	public final int w;
	public Warrior(Character c,int w) {
		this.c = c;
		this.w = w;
	}
	
	
	boolean limit (Pos p) {
		return p.x<=8 && p.x>=1 && p.y>=1 && p.y<=8;
	}
	boolean blank (Pos p,Warrior[][] board) {
		return limit(p) && board[p.x-1][p.y-1]==null;
	}
	abstract ArrayList<Move> moves (Warrior[][] board);
	
	ArrayList<Pos> line(Function func,Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos succ = func.call(findp(board));
		while (blank(succ,board)) {
			ans.add(succ);
			succ = func.call(succ);
		}
		if (limit(succ) && board[succ.x-1][succ.y-1].c!=this.c) ans.add(succ);
		return ans;
	}
	
	Pos findp(Warrior[][] board) {
		for(int i=1;i<=8;i++) {
			for(int j=1;j<=8;j++) {
				if(board[i-1][j-1]==this) return new Pos(i,j);
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
	public Soldier(Character c, int w) {
		super(c, w);
	}
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
		if(row>8 || row<1) return ptom(ans,p);
		
		
		if(p.y<8 && board[row-1][p.y]!=null && board[row-1][p.y].c!=this.c) ans.add(new Pos(row,p.y+1));
		if(p.y>1 && board[row-1][p.y-2]!=null && board[row-1][p.y-2].c!=this.c) ans.add(new Pos(row,p.y-1));
		
		if(board[row-1][p.y-1]==null) {
			Pos forwd = new Pos(row,p.y);
			if(row==1||row==8) {
				//Pawn Promotion
				char [] ch = {'q','b','k','r'};
				for(Character c:ch) {
					temp.add(new Move(p,forwd,1,c));
				}
			}
			else {ans.add(forwd);}
			if((p.x==2 && this.c=='w')||(p.x==7&&this.c=='b')) {
				int rw2 = ford(board,2);
				if(board[rw2-1][p.y-1]==null) ans.add(new Pos(rw2,p.y));
			}
		}
		
		
		
		temp.addAll(ptom(ans.stream().filter(q->limit(q)).collect(Collectors.toCollection(ArrayList::new)),p));
		return temp;
	}
	
	public String toString() {return this.c+"pawn";}
}

class Rook extends Warrior{
	public Rook(Character c, int w) {super(c, w);}
	
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

	public Knight(Character c, int w) {super(c, w);}

	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos p = findp(board);
		ans.add(new Pos(p.x-2,p.y-1));ans.add(new Pos(p.x-2,p.y+1));
		ans.add(new Pos(p.x+2,p.y-1));ans.add(new Pos(p.x+2,p.y+1));
		ans.add(new Pos(p.x-1,p.y-2));ans.add(new Pos(p.x-1,p.y+2));
		ans.add(new Pos(p.x+1,p.y-2));ans.add(new Pos(p.x+1,p.y+2));
		
		return ptom(ans.stream().filter(
				q->limit(q) &&(
					board[q.x-1][q.y-1]==null || board[q.x-1][q.y-1].c!=this.c)).collect(
							Collectors.toCollection(ArrayList::new)),p);
	}
	public String toString() {return this.c+"knight";}
	
}

class Bishop extends Warrior{
	public Bishop(Character c, int w) {super(c, w);}
	
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

	public King(Character c, int w) { super(c, w);}

	ArrayList<Move> moves(Warrior[][] board) {
		ArrayList<Pos> ans = new ArrayList<Pos>();
		Pos p = findp(board);
		ans.add(new Pos(p.x-1,p.y+1));ans.add(new Pos(p.x+1,p.y-1));
		ans.add(new Pos(p.x-1,p.y));ans.add(new Pos(p.x+1,p.y));
		ans.add(new Pos(p.x-1,p.y-1));ans.add(new Pos(p.x+1,p.y+1));
		ans.add(new Pos(p.x,p.y+1));ans.add(new Pos(p.x,p.y-1));
		
		return ptom(ans.stream().filter(
				q->limit(q) &&(
					board[q.x-1][q.y-1]==null || board[q.x-1][q.y-1].c!=this.c)).collect(
							Collectors.toCollection(ArrayList::new)),p);
	}
	public String toString() {return this.c+"king";}
	
}

class Queen extends Warrior{

	public Queen(Character c, int w) {super(c, w);}

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















