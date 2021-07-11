public class Card {

	private String colorName;
    private int suitVal;
    
    public Card (String color, int a){
    	 colorName = color;
    	 suitVal = a;
    }
	public String getSuitName(){
        return colorName;
    }
    public int getSuitVal(){
        return suitVal;
    }
    public void setCardColor(String color){
        colorName = color;
    }
    public void setCardVal(int num){
        suitVal = num;
    }
}
