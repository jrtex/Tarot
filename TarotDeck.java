package v05;

import java.util.List;

public interface TarotDeck {

	public List<TarotCard> getCards();
	public void distribute(); // random;
	public void distribute(Player dealer);
	public void shuffle();
	
	public void addCards(List<TarotCard> p1Cards, List<TarotCard> p2Cards,
			List<TarotCard> p3Cards, List<TarotCard> p4Cards,
			List<TarotCard> chien);
	
	public void cut(int i);
}
