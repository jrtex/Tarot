package v05;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CardLabel extends JLabel{
	
	private TarotCard card;
	private Player player;
	
	public CardLabel(TarotCard card, Player player){
		super(card.toString());
		this.card = card;
		this.player = player;
		
		paint();
	}
	
	
	public CardLabel(PlayedCard c){
		super(c.getCard().toString());
		this.card = c.getCard();
		this.player = c.getPlayer();
		
		paint();
	}

	
	private void paint(){
		if (card.getSuit() == TarotCard.Suit.CARREAU || card.getSuit() == TarotCard.Suit.COEUR)
			this.setForeground(Color.RED);
		else if (card.getSuit() == TarotCard.Suit.TREFLE || card.getSuit() == TarotCard.Suit.PIQUE)
			this.setForeground(Color.BLACK);
		else
			this.setForeground(Color.GREEN);
	}
	
	
	// Getters
	public TarotCard getCard(){
		return card;
	}
	public Player getPlayer(){
		return player;
	}

}
