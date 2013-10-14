package v05;

public interface TarotCard extends Comparable<TarotCard> {
	
	public enum Suit {PIQUE, COEUR, CARREAU, TREFLE, ATOUT};
	
	public static final int EXCUSE = 0;
	public static final int VALET = 11;
	public static final int CAVALIER = 12;
	public static final int DAME = 13;
	public static final int ROI = 14;

	public Suit getSuit();
	public String toString();
	// public String toLabel();
	public double getValue();
	public int getRank();

}
