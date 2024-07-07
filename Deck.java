import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private int size;
    private ArrayList<Card> deck = new ArrayList<>();
    private int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private char[] suits = {'♥', '♠', '♦', '♣'};

    public Deck(){
        for (char suit : suits){
            for (int i = 0; i < ranks.length; i++){
                this.deck.add(new Card(suit, ranks[i], values[i]));
            }
        }
        this.size = 52;
        this.shuffle();
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public Card drawCard(){
        size--;
        return deck.remove(0);
    }

    public int getSize(){
        return size;
    }
}
