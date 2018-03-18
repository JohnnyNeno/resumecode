import java.applet.*;
import java.awt.*;

public class theThirdGame extends Applet implements Runnable{
	Thread minTrad;
	
	Image offscreenImage;
	Graphics offscr;
	int width, height, moving;
	Image img;
	
	public void init(){
		width=size().width;
		height=size().height;
		
		try{
			img=getImage(getCodeBase(),"Trollface_HD.png");
		}
		catch(Exception e){
			
		}
		offscreenImage= createImage(width,height);
		offscr=offscreenImage.getGraphics();
		minTrad= new Thread(this);
		minTrad.start();
		
	}
	
	
	
	public void run(){
		while(true){
			
			
			repaint();
			try{
				minTrad.sleep(10);
			}
			catch(InterruptedException e){
			}
		}
}
	public void paint(Graphics g){
			String s= " "+getCodeBase();
			offscr.setColor(Color.yellow);
			offscr.fillRect(0, 0, width, height);
			offscr.setColor(Color.black);
			offscr.fillRect(width/2,height/2,10,10);
			offscr.drawString(s,10,10);
			offscr.drawImage(img,0,0,this);
			g.drawImage(offscreenImage,0,0,this);
		
			
	}
	
	public boolean keyDown(Event e, int key){
		 moving=key;
		return true;
	}
	public boolean keyUp(Event e, int key){
		
		return true;
	}
	
	public boolean mouseEnter(Event e, int x, int y){
		
		return true;
	}
	public boolean mouseExit(Event e,int x, int y){
		return true;
	}
	public boolean mouseDown(Event e,int x, int y){
		
		return true;
	}
	public boolean mouseUp(Event e,int x, int y){
		
		return true;
	}
	public boolean mouseMove(Event e,int x, int y){
		return true;
	}
	public boolean mouseDrag(Event e,int x, int y){
		
		return true;
	}
	
	public void update(Graphics g){
		paint(g);
	}

}
