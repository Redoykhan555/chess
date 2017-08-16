package engine;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Minimax{
	static int inf = 99999999;
	
	static int minimax(Node parent,Node state,int depth) {
		if(depth==0) {
			return state.heuristic();
		}
		int bestVal = -inf;
		if(state.maxPlayer()) {
			
			for(Node child:state.children()) {
				bestVal = max(bestVal,minimax(state,child,depth-1));
				state.setAlpha(max(state.getAlpha(),bestVal));
				if(parent!=null && parent.getBeta()<=state.getAlpha()) break;
			}
		}
		else {
			bestVal = inf;
			for(Node child:state.children()) {
				bestVal = min(bestVal,minimax(state,child,depth-1));
				state.setBeta(min(state.getBeta(),bestVal));
				if(parent!=null && state.getBeta()<=parent.getAlpha()) break;
			}
		}
		
		return bestVal;
	}
	
	private static int max(int bestVal, int minimax) {
		if(bestVal>minimax) return bestVal;
		return minimax;
	}
	private static int min(int bestVal, int minimax) {
		if(bestVal<minimax) return bestVal;
		return minimax;
	}

	public static Node go(Node state) {
		//System.out.println("GO"+state.maxPlayer());
		int a = inf-99;
		Node ans = null;
		ArrayList<? extends Node> childs = state.children();

		int al,be;
		ArrayList <Future<Integer>> fs = new ArrayList <Future<Integer>>();
		ExecutorService executor = Executors.newFixedThreadPool(4);
		for(Node child:childs) {
			Future<Integer> temp = executor.submit(new Callable<Integer>() {
				public Integer call() {
					return minimax(null,child,4);
				}
			});
			fs.add(temp);
		}
		executor.shutdown();
		try {
			executor.awaitTermination((long) 99999999, TimeUnit.SECONDS);
			for(int i=0;i<fs.size();i++) {
				int tt = fs.get(i).get();
				if(tt<a) {
					a = tt;
					ans = childs.get(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	
}







