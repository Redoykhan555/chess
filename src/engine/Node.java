package engine;

import java.util.ArrayList;

public interface Node {
	public int getAlpha();
	public void setAlpha(int x);
	public int getBeta();
	public void setBeta(int x);
	public boolean maxPlayer();
	public boolean terminated();
	public ArrayList<? extends Node> children ();
	public int heuristic();
}
