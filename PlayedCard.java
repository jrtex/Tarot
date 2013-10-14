package v05;

public class PlayedCard {
	
	private Player player;
	private TarotCard card;

	
	public PlayedCard(Player p, TarotCard c){
		this.player = p;
		this.card = c;
	}
	public PlayedCard(CardLabel l){
		this.player = l.getPlayer();
		this.card = l.getCard();
	}
	
	
	// Getters
	public Player getPlayer(){
		return player;
	}
	public TarotCard getCard(){
		return card;
	}
	
	
	// Extend Card properties
	public TarotCard.Suit getSuit(){
		return card.getSuit();
	}
	public int getRank(){
		return card.getRank();
	}
	
	
	// Other
	public String toString(){
		return ("Player " + player.getName() + " played card " + card.toString() );
	}
}
