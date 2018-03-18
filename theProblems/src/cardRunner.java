
class cardRunner {
	
	public static void main (String args[]){
		
		DeckOfCards aDeck = new DeckOfCards();
		
		System.out.println(aDeck.getCard(0));
		aDeck.swapVisibility(0);
		System.out.println(aDeck.getCard(0));
		aDeck.swapVisibility(0);
		System.out.println(aDeck.getCard(0));
		
		
		
		
	}

}
