package ha.it.drinkapp;

import java.util.ArrayList;
import java.util.Random;

public class DeckCreator {
	
	private ArrayList<Card> deck;
	public DeckCreator() {
		
		deck = new ArrayList<Card>();
		
			for (int cardSuit = 0; cardSuit <= 3; cardSuit++) {
				for(int cardValue = 1; cardValue <= 13; cardValue++) {
					deck.add(new Card(cardSuit, cardValue) );
				}
			}
	}

	//Draw a random card from the deck, NOTE not the top element!
    public Card drawFromDeck()
    {
        Random generator = new Random();
        int index = generator.nextInt(deck.size());
        return deck.remove(index);
    }
	
    //Check size of the deck, a.k.a. ArrayList size
	public int getTotalCards() {
		return deck.size();
	}

	/*
	public Card randomizeCard() {
		Random random = new Random();
	    return deck.get(random.nextInt( deck.size() ));
	}
	*/

}
