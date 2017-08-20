package helper;

public class Board {
	public String[][] board;
	public Board(String str) {
		board = new String[8][8];
		board = strToArr(str);
	}
	public static String[][] strToArr(String str) {
		String[][] board = new String[8][8];
		String[] temp = str.split("\n");
		for(int i=0;i<8;i++) {
			board[i] = temp[i].split(",");
		}
		return board;
	}
	public static String arrToStr(String[][] board) {
		String ans = "";
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) ans+=(board[i][j]+",");
			ans+="\n";
		}
		return ans;
	}
	public static Warrior[][] sToW(String s) {
		String[][] ab = strToArr(s);
		Warrior[][] ans = new Warrior[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				Character c = ab[i][j].charAt(0);
				String type = ab[i][j].substring(1);
				if(type.equals("king")) ans[i][j] = new King(c);
				if(type.equals("pawn")) ans[i][j] = new Soldier(c);
				if(type.equals("queen")) ans[i][j] = new Queen(c);
				if(type.equals("rook")) ans[i][j] = new Rook(c);
				if(type.equals("bishop")) ans[i][j] = new Bishop(c);
				if(type.equals("knight")) ans[i][j] = new Knight(c);
			}
		}
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {}//System.out.print(ans[i][j]+",");
			//System.out.println();
		}
		return ans;
	}
	
	public static String wToS(Warrior[][] w) {
		String[][] ans = new String[8][8];
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(w[i][j]!=null) ans[i][j] = w[i][j].toString();
			}
		}
		return arrToStr(ans);
	}
	
	public static State four(String s,int a,int b,int c,int d,boolean kmv) {
		Warrior[][] w = sToW(s);
		State st = new State(w,'b',kmv);
		return st.upd(new Move(new Pos(a,b),new Pos(c,d)));
	}
	
	private static void placePieces(String[][] temp,char c,int fr,int bk) {
		for(int i=0;i<8;i++) {
			temp[fr][i] = c+"pawn";
		}
		temp[bk][0] = c+"rook";temp[bk][7] = c+"rook";
		temp[bk][1] = c+"knight";temp[bk][6] = c+"knight";
		temp[bk][2] = c+"bishop";temp[bk][5] = c+"bishop";
		temp[bk][3] = c+"queen";temp[bk][4] = c+"king";
	}
	
	public static String init() {
		String[][] temp = new String[8][8];
		placePieces(temp, 'w',1,0);
		placePieces(temp, 'b',6,7);
		return arrToStr(temp);
	}

}











