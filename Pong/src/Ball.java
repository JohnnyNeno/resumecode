
public class Ball {
	static int centerX= GameRunner.getWidth()/2;
	static int centerY= GameRunner.getHeight()/2;
	static int directionX=-5;
	static int directionY=-5;
	
	
	public static void moveTheBall(){
		centerX=centerX+directionX;
		centerY=centerY+directionY;
		
	}
	
	public static void resetCommand(){
		centerX= GameRunner.getWidth()/2;
		centerY= GameRunner.getHeight()/2;
		
	}
	
	public static void changeDirectionX(){
		directionX=directionX*(-1);
	}

	public static void changeDirectionY(){
		directionY=directionY*(-1);
	}
	
	public static int centerYValue(){
		return centerY;
	}
	public static int centerXValue(){
		return centerX;
	}
	
	public static int leftBallEdge(){
		int leftPoint=centerX-10;
		return leftPoint;
	}
	
	public static int topBallEdge(){
		int topPoint=centerY-10;
		return topPoint;
	}
	public static int bottomBallEdge(){
		int bottomPoint=centerY+10;
		return bottomPoint;
	}
	public static int rightBallEdge(){
		int rightPoint=centerX+10;
		return rightPoint;
	}
}
