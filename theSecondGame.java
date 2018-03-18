import java.applet.*;
import java.awt.*;

public class theSecondGame extends Applet implements Runnable{
	Thread minTrad;
	
	Image offscreenImage;
	Graphics offscr;
	int width,height;
	int lvl =1;
	int i=0;
	int j=200;
	int moving;
	int mobileLaserOne=201;
	int mobileLaserTwo=51;
	
	int speedBuff=0;
	boolean speedBuffOneOnScreen=true;
	
	int laserCounterOne=0;
	int laserCounterTwo=0;
	int laserCounterThree=0;
	int laserCounterFour=0;
	
	boolean laserTwoOn;
	boolean laserOneOn;
	boolean laserThreeOn;
	boolean laserFourOn;
		
		
	
	public void init(){
		width=size().width;
		height=size().height;
		
		
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
		if(lvl==1){
		laserCounterOne++;
		laserCounterTwo++;
		laserCounterThree++;
		if(laserCounterOne==600)
			laserCounterOne=0;
		if(laserCounterTwo==400)
			laserCounterTwo=0;
		if(laserCounterThree==500)
			laserCounterThree=0;
		
		offscr.setColor(Color.black);
		offscr.fillRect(0,0,width,height);
		offscr.setColor(Color.white);
		offscr.fillRect(0,200,700,50);
		offscr.setColor(Color.blue);
		offscr.fillRect(650, 200, 50, 50);
		if(laserCounterOne<300){
			offscr.setColor(Color.red);
			offscr.drawLine(100,200,100,250);
			laserOneOn=true;
			}
		if(laserCounterTwo<200){
			offscr.setColor(Color.red);
			offscr.drawLine(250,200,250,250);
			laserTwoOn=true;
		}
		if(laserCounterThree<250){
			offscr.setColor(Color.red);
			offscr.drawLine(450,200,450,250);
			laserThreeOn=true;
		}
		
		
		if(moving==1006&&i>0)
			i=i-5;
		if(moving==1007&&i+10<700)
			i=i+5;
		if(moving==1004&&j>200)
			j=j-5;
		if(moving==1005&&j+10<250)
			j=j+5;
		
		if(laserOneOn&&i<=100&&(i+10)>=100){
			i=0;
			j=200;
		}
		if(laserTwoOn&&i<=250&&(i+10)>=250){
			i=0;
			j=200;
		}
		if(laserThreeOn&&i<=450&&(i+10)>=450){
			i=0;
			j=200;
		}
			
		
		moving=0;
		offscr.setColor(Color.red);
		offscr.fillRect(i, j, 10, 10);
		
		if(i+10>675){
			i=0;
			j=150;
			lvl=2;}
		laserOneOn=false;
		laserTwoOn=false;
		laserThreeOn=false;
		g.drawImage(offscreenImage,0,0,this);
		}
		
		
		if(lvl==2){
			offscr.setColor(Color.black);
			offscr.fillRect(0, 0, width, height);
			offscr.setColor(Color.white);
			offscr.fillRect(0, 150, 200, 50);
			offscr.fillRect(150, 200, 50, 400);
			offscr.fillRect(200, 550, 200, 50);
			offscr.setColor(Color.blue);
			offscr.fillRect(350, 550, 50, 50);
			
			if(moving==1006&&i>0&&(j+10)<=200)
				i=i-5;
			if(moving==1006&&i>150&&(j+10)>200)
				i=i-5;
			if(moving==1007&&(i+10)<200&&j<550)
				i=i+5;
			if(moving==1007&&(i+10)<400&&j>=550)
				i=i+5;
			if(moving==1004&&j>150&&(i+10)<=200)
				j=j-5;
			if(moving==1004&&j>550&&(i+10)>200)
				j=j-5;
			if(moving==1005&&(j+10)<200&&i<150)
				j=j+5;
			if(moving==1005&&(j+10)<600&&i>=150)
				j=j+5;
			offscr.setColor(Color.red);
			offscr.drawLine(150, mobileLaserOne, 200, mobileLaserOne);
			mobileLaserOne++;
			if(mobileLaserOne>547)
				mobileLaserOne=201;
			moving=0;
			if(j<=mobileLaserOne&&(j+10)>=mobileLaserOne){
				i=0;
				j=150;
			}
				
				
			offscr.setColor(Color.red);
			offscr.fillRect(i,j,10,10);
			if(i>350&&j>=550){
				lvl=3;
				i=0;
				j=300;
			}
				
			g.drawImage(offscreenImage,0,0,this);
		}
		
		if(lvl==3){
			offscr.setColor(Color.black);
			offscr.fillRect(0, 0, width, height);
			offscr.setColor(Color.white);
			offscr.fillRect(0, 300, 50, 400);
			offscr.fillRect(50, 400, 650, 50);
			
			if(speedBuffOneOnScreen){
			offscr.setColor(Color.green);
			offscr.drawRect(0, 690, 10, 10);
			offscr.setColor(Color.red);
			offscr.drawString("S",0,700);
			}
			
			offscr.setColor(Color.blue);
			offscr.fillRect(650, 400, 50, 50);
			
			if(moving==1006&&i>0)
				i=i-5-speedBuff;
			if(moving==1007&&(i+10)<700&&j>=400&&(j+10)<=450)
				i=i+5+speedBuff;
			if(moving==1007&&(i+10)<50&&j<400||moving==1007&&(i+10)<50&&(j+10)>450)
				i=i+5+speedBuff;
			if(moving==1004&&j>300&&(i+10)<=50)
				j=j-5-speedBuff;
			if(moving==1004&&j>400&&(i+10)>50)
				j=j-5-speedBuff;
			if(moving==1005&&(j+10)<700&&(i+10)<=50)
				j=j+5+speedBuff;
			if(moving==1005&&(j+10)<450&&(i+10)>50)
				j=j+5+speedBuff;
			
			moving=0;
			
			if(i==0&&(j+10)==700){
				speedBuffOneOnScreen=false;
				speedBuff=5;
			}
			laserCounterFour++;
			if(laserCounterFour==400)
				laserCounterFour=0;
			offscr.setColor(Color.red);
			laserFourOn=false;
			if(laserCounterFour<200){
				offscr.drawLine(0, 550, 50, 550);
				laserFourOn=true;
			}
			offscr.drawLine(mobileLaserTwo,400,mobileLaserTwo,450);
			mobileLaserTwo=mobileLaserTwo+5;
			if(mobileLaserTwo>500)
				mobileLaserTwo=51;
			
			if(i<=mobileLaserTwo&&(i+10)>=mobileLaserTwo){
				i=0;
				j=300;
				speedBuff=0;
				speedBuffOneOnScreen=true;
			}
			if(laserFourOn&&(j+10)>=550&&j<=550){
				i=0;
				j=300;
				speedBuff=0;
				speedBuffOneOnScreen=true;
			}
			offscr.setColor(Color.red);
			offscr.fillRect(i,j,10,10);
			if(i>660){
				lvl=4;
			}
			g.drawImage(offscreenImage,0,0,this);
			
			
		}
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
