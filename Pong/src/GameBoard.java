import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;

public class GameBoard extends JPanel {
	
	
	
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, GameRunner.getWidth(), GameRunner.getWidth());
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GameRunner.getWidth(), 10);
		g.fillRect(0,GameRunner.getHeight()-10,GameRunner.getWidth(),10);
		
		g.setColor(Color.RED);
		g.fillRect(0,LeftPaddle.getTopHeight(),10,34); 
		
		g.setColor(Color.ORANGE);
		g.fillRect(GameRunner.getWidth()-10,RightPaddle.getTopHeight(),10,34); 
		
		g.setColor(Color.YELLOW);
		g.fillOval(Ball.centerX-10,Ball.centerY-10,20,20);
		
		
	}

}
