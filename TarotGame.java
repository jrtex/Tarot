package v05;

import java.util.List;

public interface TarotGame extends java.util.Observer{
	
	public enum Status {CONTRACT, CHIEN, PLAY, DONE};

	public Status getStatus();
	
	public Player getP1();
	public Player getP2();
	public Player getP3();
	public Player getP4();
	public Player getDealer();
	public Player getWhoseTurn();
	
	public List<TarotCard> getChien();
	public Fold getCurrentFold();
	
	public Contract getContract();
	public void setContract(Contract c);

	public void playCard(PlayedCard c) throws ExcuseException;
	public void redistribute();
	public void switchTurn();
	public void nextPhase();

}
