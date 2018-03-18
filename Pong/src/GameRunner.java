import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.*;


public class GameRunner implements KeyListener{
	
	GameBoard board;
	JFrame frame;
	static int width=500;
	static int height=300;
	int fakeWidth= width+16;
	int fakeHeight=height+38;
	static int frameCounter=0;
	
	
	
	
	
	public static void main(String args[]){
		GameRunner test = new GameRunner();
		test.setup();
		
	}
	
	public void setup(){
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(fakeWidth,fakeHeight);
		
		frame.setVisible(true);
		
		board = new GameBoard();
		
		
		
		board.addKeyListener(this);
		board.setFocusable(true);
		
		frame.getContentPane().add(board);
		
		while(true){
			
			frame.repaint();
			frameCounter++;
			Ball.moveTheBall();
			RightPaddle.artificialIntelligence();
			Collision.allCollides();
			
			try{
				Thread.sleep(50);
			}catch(Exception ex){}
			
			
		}
		
		
	}
	
	public static int getWidth(){
		return width;		
	}
	
	public static int getHeight(){
		return height;
	}
	
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==40)
			LeftPaddle.setBothHeights(LeftPaddle.getTopHeight()+5);
		if(e.getKeyCode()==38)
			LeftPaddle.setBothHeights(LeftPaddle.getTopHeight()-5);
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		
		
	}
	
	public void keyTyped(KeyEvent e) {
	
		
	}
	
	public static void resetCommand(){
		frameCounter=0;
	}
	
}
