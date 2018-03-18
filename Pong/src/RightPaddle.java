
public class RightPaddle {
	static int topHeight=GameRunner.getHeight()/2-17;
	static int bottomHeight=topHeight+34;
	
	static int ballCenterHeightFake=Ball.centerYValue();
	
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
		ballCenterHeightFake=Ball.centerYValue();
		
		
	}
	
	public static void artificialIntelligence(){
		if(Ball.topBallEdge()<=10){
			ballCenterHeightFake=Ball.centerYValue();
			setBothHeights(ballCenterHeightFake-17);
		}
		else if(Ball.bottomBallEdge()>=GameRunner.getHeight()-10){
				ballCenterHeightFake=Ball.centerYValue();
				setBothHeights(ballCenterHeightFake-17);
		}
		else{
			setBallCenterHeightFake();
			setBothHeights(ballCenterHeightFake-17);
		}
		
	}
	
	public static void setBallCenterHeightFake(){
		if(GameRunner.frameCounter%4!=0)
			ballCenterHeightFake=ballCenterHeightFake+Ball.directionY;
	}
	

}
