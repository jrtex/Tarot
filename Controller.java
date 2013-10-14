package v05;


public class Controller extends java.util.Observable implements TarotListener{
	
	
	private TarotGame game;
	private TarotView view;
	
	
	public Controller(Player p1, Player p2, Player p3, Player p4){
		this.game = new TarotGameImpl(p1, p2, p3, p4);
		this.view = new TarotView(game);
		
		
		( (TarotGameImpl) game).addObserver(view);
		view.addTarotListener(this);
	}
	
	
	// Getters
	public TarotGame getGame(){
		return game;
	}
	public TarotView getView(){
		return view;
	}

	
	// Listener Methods
	private void attemptedMove(PlayedCard c) throws WrongPlayerException, IllegalMoveException, ExcuseException{
		
		if (game.getStatus() == TarotGame.Status.CONTRACT)
			throw new IllegalMoveException(c);
		else if (c.getPlayer() != game.getWhoseTurn())
			throw new WrongPlayerException(c.getPlayer());
		else if (!isMoveLegal(c))
			throw new IllegalMoveException(c);

		game.switchTurn();
		game.playCard(c);
		
		if (game.getP1().getCards().isEmpty() && game.getP2().getCards().isEmpty() &&
				game.getP3().getCards().isEmpty() && game.getP4().getCards().isEmpty()){
			game.nextPhase();
		}
	}
	
	
	private PlayedCard highestAtoutInFold(Fold f){
		
		int maxRank = 0;
		PlayedCard maxAtout = null;
		
		for (PlayedCard card: f.getFold()){
			if (card.getSuit() == TarotCard.Suit.ATOUT && card.getRank() > maxRank){
				maxRank = card.getRank();
				maxAtout = card;
			}
		}
		
		return maxAtout;
	}
	
	
	private boolean playerHasHigherAtoutThan(Player p, PlayedCard c){
		
		for (TarotCard card: p.getCards()){
			if (card.getSuit() == TarotCard.Suit.ATOUT && card.getRank() > c.getRank())
				return true;
		}
		
		return false;
	}
	
	
	
	private boolean isMoveLegal(PlayedCard card){
		TarotCard.Suit required = game.getCurrentFold().getSuit();
		
		if (game.getStatus() != TarotGame.Status.PLAY)
			return false;
		if ( card.getRank() == 0 || required == null)
			// is Card excuse or first card played?
			return true;
		else if ( card.getSuit() == required && required != TarotCard.Suit.ATOUT)
			// is Card within the suit?
			return true;
		else if (required == TarotCard.Suit.ATOUT && card.getSuit() == required ||
				card.getSuit() == TarotCard.Suit.ATOUT && !card.getPlayer().has( required ) ){
			
			if (highestAtoutInFold(game.getCurrentFold()) == null ||
					highestAtoutInFold( game.getCurrentFold() ).getRank() < card.getRank() )
				return true;
			else if (playerHasHigherAtoutThan(card.getPlayer(), highestAtoutInFold(game.getCurrentFold()) ))
				return false;
			else 
				return true;
			
		}
		else if ( card.getSuit() != required       && !card.getPlayer().has( required ) && !card.getPlayer().has( TarotCard.Suit.ATOUT) )
			return true;
		else
			return false;
	}


	private void attemptedContract(Contract c) throws IllegalContractException{

		if (game.getContract() == null || game.getContract().getMultiplier() < c.getMultiplier())
			game.setContract(c);
		else if (c.getMultiplier() == 0){
			// do nothing
		} 
		else
			throw new IllegalContractException(c);

		game.switchTurn();

		if (c.getPlayer() == game.getDealer()){
			if (game.getContract().getMultiplier() == 0){
				game.redistribute();
				// System.out.println("Redistributing");
				
			} else {
				for (TarotCard card: game.getChien()){
					game.getContract().getPlayer().getCards().add(card);
				}
				game.getChien().clear();
				game.getContract().getPlayer().orderHand();
				game.nextPhase();
			}
		}
	}
	
	
	private void putToChien(PlayedCard card) throws IllegalMoveException{
		
		if (game.getStatus() != TarotGame.Status.CHIEN)
			throw new RuntimeException("Method putToChien() can only be called during phase 'Chien'.");
		else if (card.getPlayer() != game.getContract().getPlayer())
			throw new IllegalMoveException(card);
		
		
		if (card.getSuit() == TarotCard.Suit.ATOUT && 
			(card.getRank() == 0 || card.getRank() == 1 || card.getRank() == 21) )
			throw new IllegalMoveException(card);
		
		game.getContract().getPlayer().getCards().remove(card.getCard());
		game.getContract().getPlayer().addToStash(card.getCard());
		
		
		if (game.getContract().getPlayer().getStash().size() == 6){
			game.nextPhase();
		}
	}
	
	
/*	public void redistribute(){
		
		Player p1 = game.getP1();
		Player p2 = game.getP2();
		Player p3 = game.getP3();
		Player p4 = game.getP4();
		
		

		game = new TarotGameImpl(p1, p2, p3, p4);
		view = new TarotView(game);
		
		( (TarotGameImpl) game).addObserver(view);
		
		game.update(this, game);
		
	}*/
	
	public void newGame(){
		System.out.println("Still working on");
	}


	@Override
	public void update(Object o) {
		try{
			
		
		if (o instanceof Contract)
			attemptedContract( (Contract) o);
		
		else if (o instanceof PlayedCard)
			if (game.getStatus() == TarotGame.Status.CHIEN) 
				putToChien( (PlayedCard) o);
			else if (game.getStatus() == TarotGame.Status.PLAY)
				attemptedMove( (PlayedCard) o);
			else 
				throw new IllegalMoveException( ((PlayedCard) o) );
		else if (o instanceof TarotView)
			game.redistribute();
			// newGame();
		
		else System.out.println("Object not recognized. Cannot Update controller");
		
		game.update(this, o);
		
		} catch (TarotException e){
			game.update(this, e);
		} 
	}

}

