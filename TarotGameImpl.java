package v05;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class TarotGameImpl extends java.util.Observable implements TarotGame, java.util.Observer{

	private Status status;

	private Player east;
	private Player north;
	private Player west;
	private Player south;

	private Player dealer;
	private Player whoseTurn;

	private TarotDeck deck;
	private List<TarotCard> chien;

	private Contract contract;
	
	private Fold currentFold;


	public TarotGameImpl(Player east, Player south, Player west, Player north){

		this.east = east;
		this.north = north;
		this.west = west;
		this.south = south;
		this.dealer = this.east;

		this.deck = new TarotDeckImpl(this);
		this.chien = new ArrayList<TarotCard>(6);

		deck.shuffle();
		deck.distribute(dealer);

		east.orderHand();
		north.orderHand();
		west.orderHand();
		south.orderHand();

		status = TarotGame.Status.CONTRACT;
		switchTurn();
	}


	// Getters & Setters
	public List<Player> getPlayers(){ 
		return Arrays.asList( new Player[] {east, south, west, north} );
	}
	public Player getP1(){ return east; }
	public Player getP2(){ return south; }
	public Player getP3(){ return west; }
	public Player getP4(){ return north; }
	public List<TarotCard> getChien(){
		return chien;
	}
	public Player getDealer(){
		return dealer;
	}
	public Player getWhoseTurn(){
		return whoseTurn;
	}
	public TarotGame.Status getStatus(){
		return status;
	}
	public Contract getContract(){
		return contract;
	}
	public void setContract(Contract contract){
		this.contract = contract;
	}
	public Fold getCurrentFold(){
		return currentFold;
	}
	


	// TarotGame methods
	
	// Previous implementation
	/*public void playCard(PlayedCard card){
		if (currentFold == null)
			currentFold = new Fold();
		else if (currentFold.isFull())
			throw new RuntimeException("Fold is full, card cannot be played");
		
		
		currentFold.addCard(card);
		card.getPlayer().removeCard( card.getCard() );
		
		if (currentFold.isFull()){
			whoseTurn = currentFold.getWinner();


		for (PlayedCard c: currentFold.getFold()){

				if (c.getRank() == 0){
					TarotCard replacement = c.getPlayer().replaceExcuse(c.getCard() );
					if (replacement != null);
						whoseTurn.addToStash(replacement);
				}
				else{
					whoseTurn.addToStash(c.getCard());
				}
		}

		currentFold = new Fold();
		}
	}*/
	
	
	public void playCard(PlayedCard card) throws ExcuseException{
		if (currentFold == null)
			currentFold = new Fold();
		else if (currentFold.isFull())
			throw new RuntimeException("Fold is full, card cannot be played");


		currentFold.addCard(card);
		card.getPlayer().removeCard( card.getCard() );


		// Send cards to stash once fold is full
		if (currentFold.isFull()){
						
			whoseTurn = currentFold.getWinner();
			
			for (PlayedCard c: currentFold.getFold()){
				
				if (c.getRank() == 0){
					// Send excuse to its owner
					c.getPlayer().addToStash( c.getCard() );
					c.getPlayer().setOwed(whoseTurn);
					
				}
				else
					whoseTurn.addToStash( c.getCard() );
				
			}
			
			currentFold = new Fold();
			
			if (whoseTurn.owesTo() != null){
				TarotCard repl = whoseTurn.takeLowValueCard();
				whoseTurn.owesTo().addToStash(repl);
			}

		}
	}



	public void switchTurn(){
		whoseTurn = switchTurn(whoseTurn);
		
		/*if (whoseTurn == south)
			whoseTurn = west;
		else if (whoseTurn == west)
			whoseTurn = north;
		else if (whoseTurn == north)
			whoseTurn = east;
		else
			whoseTurn = south;*/
	}
	
	
	public Player switchTurn(Player p){
		
		if (p == south)
			p = west;
		else if (p == west)
			p = north;
		else if (p == north)
			p = east;
		else
			p = south;
		return p;
	}
	
	
	public void nextPhase(){
		if (status == TarotGame.Status.CONTRACT)
			status = TarotGame.Status.CHIEN;
		else if (status == TarotGame.Status.CHIEN){
			status = TarotGame.Status.PLAY;
			currentFold = new Fold();
		}
		else {
			status = TarotGame.Status.DONE;
			update(this, null);
		}
	}
	
	
	private int calcPoints(Player p){
		int score;
		
		if (p == contract.getPlayer()){
			if (p.countScore() > contract.pointsNeeded() )
				score = (25 + (p.countScore() - contract.pointsNeeded())) * contract.getMultiplier() * 3;
			else
				score = -(25 + (p.countScore() - contract.pointsNeeded())) * contract.getMultiplier() * 3;
		} else
			if (p.countScore() < contract.pointsNeeded() )
				score = (25 + (contract.getPlayer().countScore() - contract.pointsNeeded())) * contract.getMultiplier();
			else
				score = -(25 + (contract.getPlayer().countScore() - contract.pointsNeeded())) * contract.getMultiplier();
		
		return score;
	}
	
	
	public void redistribute() {
		
		if (status == TarotGame.Status.CONTRACT)
			deck.addCards(east.takeAllCards(), south.takeAllCards(), west.takeAllCards(), north.takeAllCards(), chien );
		else if (status == TarotGame.Status.DONE){
			
			east.setScore( calcPoints(east) );
			south.setScore( calcPoints(south) );
			west.setScore( calcPoints(west) );
			north.setScore( calcPoints(north) );
			
			deck.addCards(east.takeStash(), south.takeStash(), west.takeStash(), north.takeStash(), chien);
		}
		
		chien.removeAll(chien);
		
		deck.cut( 1 + (int)(Math.random() * ((78 - 1) + 1)) );

		dealer = switchTurn(dealer);
		whoseTurn = dealer;
		deck.distribute(dealer);
		
		// System.out.println(getWhoseTurn());
		
		east.orderHand();
		south.orderHand();
		west.orderHand();
		north.orderHand();
		
		status = TarotGame.Status.CONTRACT;
		switchTurn();
		
		update(this, this);
	}
	
	public void update(Observable o, Object obj){
		setChanged();
		notifyObservers(obj);
	}

}
