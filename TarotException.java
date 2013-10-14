package v05;


public abstract class TarotException extends Exception {
	private static final long serialVersionUID = 1L;

	protected TarotException(String message){
		super(message);
	}
}


class IllegalMoveException extends TarotException {
	private static final long serialVersionUID = 1L;

	public IllegalMoveException(PlayedCard card){
		super(card.getPlayer().getName() + " cannot play card " + card.getCard() );
	}
}


class WrongPlayerException extends TarotException {
	private static final long serialVersionUID = 1L;

	public WrongPlayerException(Player p){
		super(p.getName() + " must wait his turn to play");
	}
}


class IllegalContractException extends TarotException {
	private static final long serialVersionUID = 1L;

	public IllegalContractException(Contract c){
		super(c.getPlayer().getName() + " cannot start contract: " + c.name());
	}
}


class ExcuseException extends TarotException {
	
	private static final long serialVersionUID = 1L;

	public ExcuseException(){
		super("The Excuse cannot be switched at the moment");
	}
}