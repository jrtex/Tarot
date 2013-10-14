package v05;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class TarotDeckImpl implements TarotDeck{

	private TarotGame game;
	private List<TarotCard> cards;
	
	
	public TarotDeckImpl(TarotGame game){
		this.game = game;
		this.cards = new ArrayList<TarotCard>(78);
		
		for (int i = 0; i < 78 ; i++) {
			if (i < 14)
				cards.add(new TarotCardImpl(TarotCard.Suit.PIQUE, ( i )%14 +1, this) );
			else if (i < 28)
				cards.add(new TarotCardImpl(TarotCard.Suit.COEUR, ( i )%14 +1, this) );
			else if (i < 42)
				cards.add(new TarotCardImpl(TarotCard.Suit.CARREAU, (i)%14 +1, this) );
			else if (i < 56)
				cards.add(new TarotCardImpl(TarotCard.Suit.TREFLE, ( i)%14 +1, this) );
			else
				cards.add(new TarotCardImpl(TarotCard.Suit.ATOUT, (i - 56), this ));
		}
	}
	
	
	// Getters
	public TarotGame getGame(){
		return game;
	}
	public List<TarotCard> getCards(){
		return cards;
	}
	public int getSize(){
		return cards.size();
	}
	public boolean isEmpty(){
		return (getSize() == 0);
	}
	
	
	// Deck Methods
	public TarotCard getTop(){
		if (cards.isEmpty())
			throw new RuntimeException("Deck is empty");
		
		return cards.get(0);
	}
	public TarotCard takeTop(){
		if (cards.isEmpty())
			throw new RuntimeException("Deck is empty");
		
		TarotCard top = getTop();
		List<TarotCard> newdeck = new ArrayList<TarotCard>(getSize() - 1) ;
		for (int i = 0; i < (getSize() - 1); i++) {
			newdeck.add( i,  cards.get(i + 1) );
		}
		
		cards = newdeck;
		
		return top;
	}
	
	
	// Mandatory Implementations
	public void shuffle(){
		Collections.shuffle(cards);
	}
	
	
	public void distribute(){
		
/*		for (int i = 0; i<78; i++){
			if (i<6)    game.getChien().add( cards.get(i) ) ;
			else if (i<24) game.getP1().addCard( cards.get(i) );
			else if (i<42) game.getP2().addCard( cards.get(i) );
			else if (i<60) game.getP3().addCard( cards.get(i) );
			else           game.getP4().addCard( cards.get(i) );
		}*/
		
		for (int i = 0; i<78; i++){
			if (i<6)    game.getChien().add( takeTop() ) ;
			else if (i<24) game.getP1().addCard( takeTop() );
			else if (i<42) game.getP2().addCard( takeTop() );
			else if (i<60) game.getP3().addCard( takeTop() );
			else           game.getP4().addCard( takeTop() );
		}
		
	}
	
	public void distribute(Player dealer){
		
		if (game.getWhoseTurn() != null && dealer != game.getWhoseTurn())
			throw new RuntimeException("Dealer suggested does not correspond to model");
		
		
		while( !isEmpty() ){
			
			game.switchTurn();
			game.getWhoseTurn().addCard(takeTop());
			game.getWhoseTurn().addCard(takeTop());
			game.getWhoseTurn().addCard(takeTop());
			
			boolean rand = ( Math.random() > 0.5);
			if ( cards.size() < 18)
				rand = true;
			if ( game.getChien().size() == 6)
				rand = false;
			

			if (rand)
				game.getChien().add(takeTop());
			
		}
		
	}
	
	
	public void cut(int i){
		
		if ( getSize() != 78 )
			throw new RuntimeException("Cant cut if deck isnt full");
		
		List<TarotCard> newdeck = new ArrayList<TarotCard>(78);
		
		if (i < 1 || i >= 78) {
			System.out.println("Cutting with too large int");
			newdeck = cards;
		}
		else {
			for (int j = 0; j < i ; j++) {
				newdeck.add(j, cards.get(78 - i + j) );
			}
			for (int j = i; j < 78; j++) {
				newdeck.add(j, cards.get(j - i));
			}
		}
		
		cards = newdeck;
	}
	
	
	@Override
	public void addCards(List<TarotCard> p1Cards, List<TarotCard> p2Cards,
			List<TarotCard> p3Cards, List<TarotCard> p4Cards,
			List<TarotCard> chien) {
		
		List<TarotCard> newCards = new ArrayList<TarotCard>(78);
		
		newCards.addAll(p1Cards);
		newCards.addAll(p2Cards);
		newCards.addAll(p3Cards);
		newCards.addAll(p4Cards);
		newCards.addAll(chien);
		
		this.cards = newCards;
		
	}

}
