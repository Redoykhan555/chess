package human;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import helper.Warrior;

class Box extends JButton{
	int row,col;
	public Box(ImageIcon img,int x,int y) {
		super(img);
		this.row = x;
		col = y;
	}
	public Box(String s,int x,int y) {
		super(s);
		row=x;col=y;
	}
}

public class Window extends JFrame implements MouseListener{
	Box a,b;
	private boolean ready = false;
	public int fx,fy,tx,ty;
	private HashMap<Integer,JButton> buts = new HashMap<Integer,JButton> ();
	
	public  Window() {
		boolean r=false,y=false;
		for(int i=7;i>=0;i--) {
			y=r;
			for(int j=0;j<8;j++) {
				y=y^true;
				Box b = new Box("",i,j);
				b.addMouseListener(this);
				b.setSize(40,18);
				b.setVisible(true);
				if(y) b.setBackground(new Color(255,255,255));
				else b.setBackground(new Color(30,50,10));
				add(b);
				buts.put(i*8+j, b);
			}
			r = r^true;
		}	
		setLayout(new GridLayout(8,8));
		setSize(700,500);
		setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {
		Box bx = (Box) e.getSource();
		System.out.println(bx.row+":"+bx.col);
		if(a==null) a = bx;
		else if(b==null) {
			Box tmp = bx;
			if(a==tmp) {System.out.println("Same mal clicked"); a=null ;return;}
			b = tmp;	
			fx = a.row;fy =a.col;tx = b.row;ty= b.col;
			ready = true;
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	public void wait_for() {
		System.out.println("Wait..");
		
		a=null;b=null;fx=0;fy=0;tx=0;ty=0;
		ready = false;
		
		while(!ready) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public void paint(String[][] board) {
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				String w = board[i][j];
				String path = "faka.png";
				if(w!=null) {path = w+".png";}
				buts.get(i*8+j).setIcon(new ImageIcon("images/"+path));;
			}
			//System.out.println();
			
		}
	}
	
	public Character pawnPromotion() {
	     String[] arr = {"Rook","Queen","Knight","Bishop"};
	     String s = (String) JOptionPane.showInputDialog(null,"Choose a piece:","Pawn Promotion ceremony", 
	    		 1, null,arr,null);
	     return s.charAt(0);
	}
	
	public void over() {
		JOptionPane.showMessageDialog(null,"GAME OVER");
	}
}





