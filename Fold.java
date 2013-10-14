package v05;

import java.util.List;
import java.util.ArrayList;

public class Fold {

	private List<PlayedCard> fold;

	private List<TarotCard> cards;
	private TarotCard.Suit suit;


	public Fold(){
		this.fold = new ArrayList<PlayedCard>(4);
		this.cards = new ArrayList<TarotCard>(4);
	}


	// Getters
	public List<PlayedCard> getFold(){
		return fold;
	}
	public List<TarotCard> getCards(){
		return cards;
	}
	public TarotCard.Suit getSuit(){
		return suit;
	}
	public boolean isEmpty(){
		return fold.isEmpty();
	}
	public boolean isFull(){
		return (fold.size() == 4);
	}


	// Implementation
	public void addCard(PlayedCard playedCard){
		if (isFull())
			throw new RuntimeException("Cannot Add Card to fold");
		else if ( (isEmpty() && playedCard.getRank() != 0) ||
				  (fold.size() == 1 && getSuit() == null) )
			this.suit = playedCard.getSuit();

		fold.add(playedCard);
		cards.add(playedCard.getCard());
	}
	
	
	private boolean has(TarotCard.Suit suit){
		boolean has = false;
		
		for (PlayedCard card: fold)
			if (card.getSuit() == suit && card.getRank() != 0)
				has = true;
		
		return has;
	}

	
	public Player getWinner(){
		if (!isFull())
			throw new RuntimeException("Can only check winner when fold is full");

		PlayedCard winningCard = fold.get(0);

		if ( !has(TarotCard.Suit.ATOUT) ){
			for (PlayedCard card: fold)
				if (card.getSuit() == suit && card.getRank() > winningCard.getRank())
					winningCard = card;
		} else {
			for (PlayedCard card: fold)
				if ( (card.getSuit() == TarotCard.Suit.ATOUT && card.getRank() > winningCard.getRank()) ||
						card.getSuit() == TarotCard.Suit.ATOUT && winningCard.getSuit() != TarotCard.Suit.ATOUT )
					winningCard = card;
		}
		
		return winningCard.getPlayer();
	}




}
