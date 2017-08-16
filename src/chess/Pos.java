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
	public Move(Pos a,Pos b) {
		this.from = a; this.to = b;
	}
}
