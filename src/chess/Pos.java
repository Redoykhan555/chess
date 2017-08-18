package chess;

import javax.net.ssl.HandshakeCompletedListener;

public class Pos{
	int x,y;
	public Pos(int xx,int xy) {
		x = xx; y = xy;
	}
	public String toString() {return "("+this.x+","+this.y+")";}
}

class Move {
	Pos from,to;
	int type = 0;//1=promotion,2=castling
	Character c = null;
	public Move(Pos a,Pos b) {
		this.from = a; this.to = b;
	}
	public Move(Pos a,Pos b,int t,char cc) {
		this.from = a; this.to = b;this.type=t;this.c=cc;
	}
}
