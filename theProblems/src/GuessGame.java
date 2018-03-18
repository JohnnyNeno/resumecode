
 class GuessGame {
	 	
	 Player p1, p2, p3;
	 
	void StartGame(){
		 p1 = new Player();
		 p2= new Player();
		 p3= new Player();
		 
		 int correctNumber = (int)(Math.random()*10);
		 
		 boolean p1Correct = false;
		 boolean p2Correct = false;
		 boolean p3Correct = false;
		 
		 while(true){
			 
			 p1.guess();
			 p2.guess();
			 p3.guess();
			 
			int p1GuessedNumber= p1.number;
			int p2GuessedNumber= p2.number;
			int p3GuessedNumber= p3.number;
			
			if(p1GuessedNumber==correctNumber){
				p1Correct= true;
			}
			if(p2GuessedNumber==correctNumber){
				p2Correct= true;
			}
			if(p3GuessedNumber==correctNumber){
				p3Correct= true;
			}
			
			if(p1Correct||p2Correct||p3Correct){
				System.out.println(correctNumber+" is the correct number.");
				
				System.out.print("P1 guessed: "+p1GuessedNumber+ " , P2 guessed: "+p2GuessedNumber+" , P3 guessed: "+p3GuessedNumber);
				break;
			}
			
			 
		 }
	}
}
