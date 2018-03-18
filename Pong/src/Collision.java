
public class Collision {
	
	
	
	public static void allCollides(){
		leftPaddleCollision();
		leftWallCollision();
		roofCollision();
		floorCollision();
		rightPaddleCollision();
	}
	
	public static void leftPaddleCollision(){
		int leftMostBallPoint= Ball.leftBallEdge();
		if(leftMostBallPoint<=10&&leftMostBallPoint>5&&Ball.bottomBallEdge()>=LeftPaddle.getTopHeight()&&Ball.topBallEdge()<=LeftPaddle.getBottomHeight())
			Ball.changeDirectionX();
	}
	
	public static void leftWallCollision(){
		int leftMostBallPoint= Ball.leftBallEdge();
		if(leftMostBallPoint<=0&&(Ball.bottomBallEdge()<LeftPaddle.getTopHeight()||Ball.topBallEdge()>LeftPaddle.getBottomHeight()))
			Reset.resetGame();
	}
	
	public static void roofCollision(){
		int topMostBallPoint= Ball.topBallEdge();
		if(topMostBallPoint<=10)
			Ball.changeDirectionY();
	}
	
	public static void floorCollision(){
		int bottomMostBallPoint=Ball.bottomBallEdge();
		if(bottomMostBallPoint>=GameRunner.getHeight()-10)
			Ball.changeDirectionY();
	}
	

	public static void rightPaddleCollision(){
		int rightMostBallPoint=Ball.rightBallEdge();
		if(rightMostBallPoint>=GameRunner.getWidth()-10&&rightMostBallPoint<GameRunner.getWidth()-5&&Ball.bottomBallEdge()>=RightPaddle.getTopHeight()&&Ball.topBallEdge()<=RightPaddle.getBottomHeight())
			Ball.changeDirectionX();
	}
	

}
