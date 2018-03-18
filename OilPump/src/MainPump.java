


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainPump {
	
	public static JPanel panel;
	
	
	public static JTextField incomingOilAmountText, initialOilAmountText, leavingOilAmountText,
	sensorOneText, sensorTwoText;               /*These are the values that user inputs*/
	
	public static double lZero, sOne, sTwo, v, p;         /*These are numerical values of users inputs. JTextField strings
	are converted to these with intakeValuesToDouble() method. */
	
	public static boolean sensorOne, sensorTwo, valveOpen;
	/* Sensor and actuator values in boolean format*/
	
	public static JButton confirmButton, nextButton;      /*This + following lines are user interface texts and minutes elapsed counter*/
	
	public static int minute=0;
	
	public static JLabel curOil, senOne, senTwo, valve, minutes;
	
	public static void main(String args[]){
		
		createWindow();
		
		
	}
	
	
	public static void createWindow(){               /*This method creates the
	user interface which wants 5 inputs from user and theres also 2 buttons involved.
	One button confirms user choices, however it requires user to input correct values at correct
	spots. Eg sensor one can not be activated before sensor 2, so L2 value needs to be higher than L1.
	The amount leaving tank when valve is open also has to be higher than the amount entering the tank to 
	prevent the tank from flooding. No negative values are allowed. */
		
		JFrame frame = new JFrame ("OilPumpSimulator");
		
		panel = new JPanel(new FlowLayout());
		
		JLabel initialOilAmountLabel = new JLabel("Insert the litres of oil in tank at start moment (L0)");
		
		initialOilAmountText = new JTextField(7); 
		
		JLabel incomingOilAmountLabel = new JLabel("Insert the litres of oil entering tank each minute (p)");
		
		incomingOilAmountText = new JTextField(7);
		
        JLabel leavingOilAmountLabel = new JLabel("Insert the litres of oil leaving tank each minute (v)");
		
		leavingOilAmountText = new JTextField(7);
		
        JLabel sensorOneLabel = new JLabel("Insert the litres of oil required to activate sensor one (L1)");
		
		sensorOneText = new JTextField(7);
		
        JLabel sensorTwoLabel = new JLabel("Insert the litres of oil required to activate sensor two (L2)");
		
		sensorTwoText = new JTextField(7);
		
		panel.add(initialOilAmountLabel);
		
		panel.add(initialOilAmountText);
		
        panel.add(incomingOilAmountLabel);
		
		panel.add(incomingOilAmountText);
		
        panel.add(leavingOilAmountLabel);
		
		panel.add(leavingOilAmountText);
		
        panel.add(sensorOneLabel);
		
		panel.add(sensorOneText);
		
        panel.add(sensorTwoLabel);
		
		panel.add(sensorTwoText);
		
		curOil = new JLabel();
		
		senOne = new JLabel();
		
		senTwo =  new JLabel();
		
		valve = new JLabel();
		
		minutes = new JLabel();
		
		
		panel.add(curOil);
		panel.add(senOne);
		panel.add(senTwo);
		panel.add(valve);
		panel.add(minutes);
		
		
		confirmButton = new JButton("Confirm choices");
		
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				intakeValuesToDouble();
				
				if(!okInput())
				JOptionPane.showMessageDialog(panel,"Your input values are faulty and will put the systm at risk, choose other values!");
				
				else{
					confirmButton.setEnabled(false);
					nextButton.setEnabled(true);
					
					curOil.setText("Current amount of oil in tank: "+ lZero);
					settingSensors();
					isValveOpen();
					senOne.setText("Status for sensor one: "+ sensorOne);
					senTwo.setText("Status for sensor two: "+ sensorTwo);
					valve.setText("Status for valve: "+ valveOpen);
					minutes.setText("Time elapsed in minutes: "+ minute);
					
				}
				
				}
			});
		
		nextButton = new JButton("Show next minute");
		nextButton.setEnabled(false);
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				minute++;
				
				if(valveOpen)                              /*The oil amount in tank depends if*/
					lZero=lZero+p-v;                         /*valve is open or not*/
				else
					lZero=lZero+p;
				
				curOil.setText("Current amount of oil in tank: "+ lZero);
				settingSensors();
				isValveOpen();
				senOne.setText("Status for sensor one: "+ sensorOne);
				senTwo.setText("Status for sensor two: "+ sensorTwo);
				valve.setText("Status for valve: "+ valveOpen);
				minutes.setText("Time elapsed in minutes: "+ minute);
				
				
			}
			
		});
		
		panel.add(confirmButton);
		panel.add(nextButton);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);
		
		frame.setVisible(true);
		
		frame.setSize(500, 500);
	}
	
	public static void intakeValuesToDouble(){
		try{
		lZero=Double.parseDouble(initialOilAmountText.getText());
		sOne =Double.parseDouble(sensorOneText.getText());
		sTwo =Double.parseDouble(sensorTwoText.getText());
		p= Double.parseDouble(incomingOilAmountText.getText());
		v =Double.parseDouble(leavingOilAmountText.getText());
		}
		catch(NumberFormatException e){
			
		}
		
	}
	
	public static boolean okInput(){
		
		if(lZero<0.0)
			return false;
		if(sOne<0.0||sOne>sTwo)
			return false;
		if(sTwo<0.0||sTwo<sOne)
			return false;
		if(p<0.0||v<0.0||p>=v)
			return false;
		return true;
	}
	
	
	public static void settingSensors(){     /*This method checks how much oil there is 
	in the tank. If theres more oil than respective sensor values, then the sensors will be set true*/
		if(lZero>sOne)
			sensorOne=true;
		else
			sensorOne=false;
		
		if(lZero>sTwo)
			sensorTwo=true;
		else
			sensorTwo=false;
	}
	
	public static void isValveOpen(){  /*This method handles the actuator.
	If both sensors are true then the valve will be opened. If both sensors are false, the valve
	will close*/
		if(sensorOne&&sensorTwo)
			valveOpen=true;
		if(!sensorOne&&!sensorTwo)
			valveOpen=false;
	}
	
	

}
