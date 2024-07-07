public class Card {
    private char suit;
    private int value;
    private String rank; 
    private boolean faceDown;

    public Card(char suit, String rank, int value){
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public char getSuit() {
        return this.suit;
    }

    public String getRank(){
        return this.rank;
    }

    public void setSuit(char suit) {
        this.suit = suit;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFaceDown() {
        return this.faceDown;
    }


    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    public void setFaceUp(){
        this.faceDown = false;
    }

    @Override
    public String toString(){
        String cardString = ""+ suit + rank;
        return cardString;
    }
}
