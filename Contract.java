package v05;

public class Contract {

	private int multiplier;
	private Player player;
	private TarotGame game;

	public static final int Pass = 0;
	public static final int Simple = 1;
	public static final int Guarde = 2;
	public static final int Without = 4;
	public static final int Against = 6;

	
	public Contract(Player p, int m, TarotGame game){
		this.multiplier = m;
		this.player = p;
		this.game = game;
	}


	// Getters
	public int getMultiplier(){
		return multiplier;
	}
	public Player getPlayer(){
		return player;
	}

	public String name(){
		if (multiplier == Simple)
			return "Simple";
		else if (multiplier == Guarde)
			return "Guarde";
		else if (multiplier == Without)
			return "Guarde Without";
		else if (multiplier == Against)
			return "Guarde Against";
		else
			return "Pass";
	}


	// Other
	@Override
	public String toString(){
		return (player.getName() + " with contract: " + name());
	}


	public int pointsNeeded(){

		int bout = 0;
		for (TarotCard card : player.getCards() ){ 
			if ( card.getSuit() == TarotCard.Suit.ATOUT &&
					(card.getRank() == 0 || card.getRank() == 1 || card.getRank() == 21) )
				bout++;
		}
		for (TarotCard card: player.getStash() ){
			if ( card.getSuit() == TarotCard.Suit.ATOUT &&
					(card.getRank() == 0 || card.getRank() == 1 || card.getRank() == 21) )
				bout++;
		}
		for (PlayedCard card: game.getCurrentFold().getFold()){
			if (card.getPlayer() == player &&
					card.getSuit() == TarotCard.Suit.ATOUT &&
					(card.getRank() == 0 || card.getRank() == 1 || card.getRank() == 21) )
				bout++;
		}

		if (bout == 3)
			return 36;
		else if (bout == 2)
			return 41;
		else if (bout == 1)
			return 51;
		else
			return 56;
	}
	
	
	
}
