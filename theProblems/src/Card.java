
 class Card {
	   private boolean isVisible;
       private int suite;
       private int valour;
       private boolean isSecondDeck;

       Card(int _suite, int _valour, boolean _isVisible, boolean _isSecondDeck) {
               suite = _suite;
               valour = _valour;
               isVisible = _isVisible;
               isSecondDeck=_isSecondDeck;
       }

       int getSuite() {
               return suite;
       }

       int getValour() {
               return valour;
       }
       
       boolean getIsSecondDeck(){
    	   return isSecondDeck;
       }

       boolean getVisible() {
               return isVisible;
       }

       void setVisible(boolean newValue) {
               isVisible = newValue;
       }

       public String toString() {
               String s = new String();

               if (isVisible) {
                       switch (suite) {
                               case 0: s += "S"; break;
                               case 1: s += "H"; break;
                               case 2: s += "C"; break;
                               case 3: s += "D"; break;
                               case 4: s += "S"; break;
                               case 5: s += "H"; break;
                               case 6: s += "C"; break;
                               case 7: s += "D"; break;
                       }

                       switch (valour) {
                               case 0: s += "A"; break;
                               case 10: s += "J"; break;
                               case 11: s += "Q"; break;
                               case 12: s += "K"; break;
                               default: s += Integer.toString(valour+1);
                       }
               } else {
                       if(isSecondDeck)
                    	   s="RR";
                       else
                    	   s="BB";
                    	
               }
               return s;
       }

}
