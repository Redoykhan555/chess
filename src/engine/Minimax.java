package engine;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Minimax{
	static int inf = 99999999;
	static int count = 0;
	
	static int minimax(Node parent,Node state,int depth) {
		count++;
		if(depth==0 || state.terminated()) {
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
		int a = inf;
		ArrayList<? extends Node> childs = state.children();

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
		ArrayList <Node> possb = new ArrayList<Node> ();
		try {
			executor.awaitTermination((long) 99999999, TimeUnit.SECONDS);
			for(int i=0;i<fs.size();i++) {
				int tt = fs.get(i).get();
				System.out.println("Eval :"+tt);
				if(tt<a) {
					a = tt;
					possb.clear();
					possb.add(childs.get(i));
				}
				else if(tt==a) {
					possb.add(childs.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Childs:"+fs.size()+", Count:"+count);
		count = 0;
		if(possb.isEmpty()||a==inf) return null;
		int randomNum = ThreadLocalRandom.current().nextInt(0, 101)%possb.size();
		return possb.get(randomNum);
	}
	
}







