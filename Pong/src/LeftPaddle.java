
public class LeftPaddle {
	
	static int topHeight=GameRunner.getHeight()/2-17;
	static int bottomHeight=topHeight+34;
	
	public static void setBothHeights(int proposedTopHeight){
		if(proposedTopHeight<10)
			proposedTopHeight=10;
		
		if(proposedTopHeight>(GameRunner.getHeight()-44))
			proposedTopHeight=(GameRunner.getHeight()-44);
		
		topHeight=proposedTopHeight;
		bottomHeight= topHeight+34;
	}
	
	
	public static int getTopHeight(){
		return topHeight;
	}
	
	public static int getBottomHeight(){
		return bottomHeight;
	}
	
	
	public static void resetCommand(){
		topHeight=GameRunner.getHeight()/2-17;
		bottomHeight=topHeight+34;
		
	}
	
	
	
	

}
