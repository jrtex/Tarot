package v05;

public class TarotCardImpl implements TarotCard {

	private TarotCard.Suit suit;
	private int rank;
	private double value;


	public TarotCardImpl(TarotCard.Suit suit, int rank, TarotDeck deck){

		this.suit = suit;
		this.rank = rank;
		if (suit != TarotCard.Suit.ATOUT) {
			if (rank < 1 || rank > ROI)
				throw new RuntimeException("Rank is out of range for standard card");

			if (rank == ROI) value = 4.5;
			else if (rank == DAME) value = 3.5;
			else if (rank == CAVALIER) value = 2.5;
			else if (rank == VALET) value = 1.5;
			else value = 0.5;

		} else {
			if (rank < 0 || rank > 21)
				throw new RuntimeException("Rank is out of range for standard card");

			if (rank == 0 || rank == 1 || rank == 21){
				this.value = 4.5;
			} else 
				this.value = 0.5;
		}
	}


	// Getters
	public TarotCard.Suit getSuit(){
		return suit;
	}
	public int getRank(){
		return rank;
	}
	public double getValue(){
		return value;
	}


	// Mandatory implementation methods
	@Override
	public int compareTo(TarotCard c) {
		return (this.rank - c.getRank());
	}
	
	
	// @Override
	public boolean equals(TarotCard c){
		return (rank == c.getRank() && suit == c.getSuit());
	}


	private String oneCharSuit(){
		if (getSuit() == TarotCard.Suit.CARREAU)
			return ("\u2662");
		else if (getSuit() == TarotCard.Suit.PIQUE)
			return ("\u2660");
		else if (getSuit() == TarotCard.Suit.COEUR)
			return ("\u2661");
		else if (getSuit() == TarotCard.Suit.TREFLE)
			return ("\u2663");
		else
			return ("A");
	}
	public String toString(){

		if (suit != TarotCard.Suit.ATOUT){
			if (getRank() == 14)
				return (oneCharSuit() + "K ");
			else if (getRank() == 13)
				return (oneCharSuit() + "Q ");
			else if (getRank() == 12)
				return (oneCharSuit() + "C ");
			else if (getRank() == 11)
				return (oneCharSuit() + "V ");
			else
				return (oneCharSuit() + rank + " ");
		}
		else {
			if (rank == 0)
				return "Exc ";
			else
				return (oneCharSuit() + rank + " ");
		}
	}


}
