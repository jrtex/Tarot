package v05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Player {

	private String name;
	private List<TarotCard> hand;
	private List<TarotCard> stash;
	
	private int totalScore;
	
	private Player owed;
	
	
	public Player(String name){
		this.name = name;
		this.hand = new ArrayList<TarotCard>(18);
		this.stash = new ArrayList<TarotCard>(78);
		
		this.totalScore = 0;
	}
	
	
	// Getters
	public String getName(){
		return name;
	}
	public List<TarotCard> getCards(){
		return hand;
	}
	public List<TarotCard> getStash(){
		return stash;
	}
	public int getScore(){
		return totalScore;
	}
	public void setScore(int i){
		this.totalScore += i;
	}
	
	
	public void setOwed(Player p){
		owed = p;
	}
	public Player owesTo(){
		return owed;
	}
	
	
	// Lists business
	public void addCard(TarotCard card){
		hand.add(card);
	}
	public void removeCard(TarotCard card){
		hand.remove(card);
	}
	
	public void addToStash(TarotCard card){
		stash.add(card);
	}
	public void addToStash(List<TarotCard> cards){
		for (TarotCard card: cards)
			stash.add(card);
	}
	public void addToStash(Set<TarotCard> cards){
		stash.addAll(cards);
	}
	public int getPoints(){
		int i = 0;
		for (TarotCard card: stash)
			i += card.getValue();
		
		return i;
	}
	
	
	public List<TarotCard> takeAllCards(){
		
		@SuppressWarnings("unchecked")
		List<TarotCard> list = (List<TarotCard>) ((ArrayList<TarotCard>) hand).clone();
		this.hand = new ArrayList<TarotCard>(18);
		
		return list;
	}
	
	
/*	public TarotCard replaceExcuse(TarotCard excuse){
		
		addToStash(excuse);
		for (TarotCard card: stash){
			if (card.getValue() == 0.5){
				stash.remove(card);
				return card;
			}
		}
		throw new RuntimeException("Cannot Play excuse at the moment");
	}
	
	
	public TarotCard switchExcuse(TarotCard card) throws ExcuseException{
		
		if (card.getRank() == 0){
			for (TarotCard c: stash){
				if (c.getValue() == 0.5){
					stash.remove(c);
					return c;
				}
			}
			throw new RuntimeException("No card to switch excuse with");
			
		} else if (card.getRank() == 0.5){
			for (TarotCard c: stash){
				if (c.getRank() == 0){
					stash.remove(c);
					return c;
				}
			}
			throw new RuntimeException("The Excuse is not in this player's stash");
			
		} else {
			throw new RuntimeException("This card cannot be switched with the Excuse");
		}
	}*/
	
	
	public TarotCard takeLowValueCard() throws ExcuseException{
		// Replaces an excuse Card by a low Value card
		
		for (TarotCard card: stash)
			if (card.getValue() == 0.5 ){
				stash.remove(card);
				return card;
			}
		
		
		throw new ExcuseException();
	}
	
	
	public List<TarotCard> takeStash(){
		
		@SuppressWarnings("unchecked")
		List<TarotCard> list = (List<TarotCard>) ((ArrayList<TarotCard>) stash).clone();
		this.stash = new ArrayList<TarotCard>(78);
		
		return list;
	}
	
	
	@Override
	public String toString(){
		return getName();
	}
	
	
	// Misc Methods
	public boolean has(TarotCard.Suit suit){
		
		boolean bool = false;
		for (TarotCard card : hand){
			if (card.getSuit() == suit && card.getRank() != 0)
				bool = true;
		}
		
		return bool;
	}
	
	
	private List<TarotCard> listAll(TarotCard.Suit suit){	
		
		List<TarotCard> suitlist = new ArrayList<TarotCard>(18);
		for (TarotCard card: hand) {
			if (card.getSuit() == suit)
				suitlist.add( (TarotCard) card );
		}
		
		Collections.sort(suitlist);
		Collections.reverse(suitlist);
		
		return suitlist;	
	}
	
	
	public void orderHand(){
		
		List<TarotCard> newHand = new ArrayList<TarotCard>( hand.size() );
		
		List<TarotCard> atoutList   = listAll(TarotCard.Suit.ATOUT  );
		List<TarotCard> trefleList  = listAll(TarotCard.Suit.TREFLE );
		List<TarotCard> coeurList   = listAll(TarotCard.Suit.COEUR  );
		List<TarotCard> piqueList   = listAll(TarotCard.Suit.PIQUE  );
		List<TarotCard> carreauList = listAll(TarotCard.Suit.CARREAU);
		
		newHand.addAll(atoutList  );
		newHand.addAll(trefleList );	
		newHand.addAll(coeurList  );	
		newHand.addAll(piqueList  );	
		newHand.addAll(carreauList);	
		
		this.hand = newHand;
	}
	
	
	public int countScore(){
		
		int score = 0;
		for (TarotCard card: stash)
			score += card.getValue();
		
		return score;
	}


	public void removeCards() {
		this.hand = new ArrayList<TarotCard>(18);
	}
	
	
	
	
}

