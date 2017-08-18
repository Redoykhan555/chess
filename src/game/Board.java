package game;

public class Board {
	String[][] board;
	public Board(String str) {
		board = new String[8][8];
		init(str);
	}
	private void init(String str) {
		String[] temp = str.split(",");
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) board[i][j]=temp[i*8+j];
		}
	}
	public String toString() {
		String ans = "";
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) ans+=board[i][j];
		}
		return ans;
	}
	public static void main(String[] args) {
		//test 
	}
}











