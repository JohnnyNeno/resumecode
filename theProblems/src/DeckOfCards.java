import java.util.Random;
class DeckOfCards {
	  private Card[] kort;
      private Random mixer;

      	DeckOfCards() {
              kort = new Card[104];
              mixer = new Random();

              for (int i = 0; i < 104; i++){
            	  boolean isSecondPack=false;
            	  if(i>51)
            		  isSecondPack=true;
            	  kort[i] = new Card(i / 13, i % 13, true,isSecondPack);}

              shuffle();
      }

      void shuffle() {
              int foo;
              Card tempcard;

              for (int i = 0; i < 104; i++) {
                      foo = mixer.nextInt(i + 1);
                      tempcard = kort[foo];
                      kort[foo] = kort[i];
                      kort[i] = tempcard;
              }
      }

      Card getCard(int i) {
              return kort[i];
      }
      
      void swapVisibility(int i){
    	  boolean maybe = kort[i].getVisible();
    	  if(maybe)
    		  kort[i].setVisible(false);
    	  else
    		  kort[i].setVisible(true);
      }

}
