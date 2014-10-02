package ha.it.drinkapp;

public class Card  {

	private static final String knightBackup = "Come up with a game!";
	private static final String queenBackup = "Come up with a rule!";
	private static final String kingBackup = "The rule of thumb!";
	private static final String aceBackup = "One poor sip for you mate!";
	public final static int SPADES 		= 0;
	public final static int HEARTS 		= 1;
	public final static int DIAMONDS 	= 2;
	public final static int CLUBS 		= 3;
	private int value;
	private int suit;
	boolean gameInit = false;
	private String knightRule;
	private String queenRule;
	private String kingRule;
	private String aceRule;
	
	Card(int suit, int value) {
		this.setSuit(suit);
		this.setValue(value);
	}

	public void backupRules() {
		knightRule = getKnightbackup();
		queenRule = getQueenbackup();
		kingRule = getKingbackup();
		aceRule = getAcebackup();
	}
	
	public void changeKnightRule(String newKnightRule) {
		knightRule = newKnightRule;
	}
	
	public void changeQueenRule(String newQueenRule) {
		queenRule = newQueenRule;
	}
	
	public void changeKingRule(String newKingRule) {
		kingRule = newKingRule;
	}
	
	public void changeAceRule(String newAceRule) {
		aceRule = newAceRule;
	}
	
	public String getValueAsString() {
	    switch (value) {
	        case 1:   	return "ace";
	        case 2:   	return "2";
	        case 3:   	return "3";
	        case 4:   	return "4";
	        case 5:		return "5";
	        case 6:   	return "6";
	        case 7:   	return "7";
	        case 8:   	return "8";
	        case 9:   	return "9";
	        case 10:	return "10";
	        case 11:	return "jack";
	        case 12:  	return "queen";
	        case 13:  	return "king";
	        default:  	return null;
        }
	}
	
	public String getSuitAsString() {
		switch (suit) {
			case SPADES:   	return "spades";
		    case HEARTS:   	return "hearts";
		    case DIAMONDS: 	return "diamonds";
		    case CLUBS:    	return "clubs";
			default: 		return null;
		}
	}
	
	public String drawableToString() {
		return getSuitAsString() + getValueAsString(); 
	}

	
	public String getRuleForCard(int cardValue) {
		
		
		if (cardValue == 1) {
			return aceRule;
		}
		if(cardValue >= 2 && cardValue <= 5) {
			return "You'll have to drink: " + cardValue + " sips!";	
		}
		if(cardValue >= 6 && cardValue <= 10) {
			return "Deal out: " + cardValue + " sips to others!";
		}
		if(cardValue == 11) {
			return knightRule;
		}
		if(cardValue == 12) {
			return queenRule;
		}
		if(cardValue == 13) {
			return kingRule;
		}
		
		return null;
		
	}
	

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static String getAcebackup() {
		return aceBackup;
	}

	public static String getKingbackup() {
		return kingBackup;
	}

	public static String getQueenbackup() {
		return queenBackup;
	}

	public static String getKnightbackup() {
		return knightBackup;
	}

	public String getKnightRule() {
		return knightRule;
	}
	public String getQueenRule() {
		return queenRule;
	}
	public String getKingRule() {
		return kingRule;
	}
	public String getAceRule() {
		return aceRule;
	}
}
	
