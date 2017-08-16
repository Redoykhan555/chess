package engine;

import java.util.ArrayList;

public interface Node {
	public int getAlpha();
	public void setAlpha(int x);
	public int getBeta();
	public void setBeta(int x);
	public boolean maxPlayer();
	public ArrayList<? extends Node> children ();
	public ArrayList<? extends Node> children (int p);
	public int heuristic();
}
