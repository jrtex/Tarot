package v05;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlayerPanel extends JPanel{

	private TarotView view;
	private Player player;

	public PlayerPanel(TarotView view, Player player){
		this.view = view;
		this.player = player;

		setLayout(new BorderLayout());
		add(new JLabel(player.getName() + "  |  score: " + player.getScore()), BorderLayout.NORTH );
		add(cardsPanel(), BorderLayout.CENTER);
		add(new JLabel("Cards won: " + player.getStash().size()), BorderLayout.SOUTH);
	}


	// Getters
	public TarotView getView(){
		return view;
	}
	public Player getPlayer(){
		return player;
	}


	// Panel building
	private JPanel cardsPanel(){
		JPanel panel = new JPanel(new GridLayout(3, 6));
		
		for (TarotCard card: player.getCards()){
			CardLabel label = new CardLabel(card, player);
			label.addMouseListener(view);
			panel.add(label);
		}

		return panel;
	}

}
