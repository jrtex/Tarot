package v05;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
//  java.awt.Color;
// import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class TarotView extends JPanel implements ActionListener, java.util.Observer, MouseListener{

	private TarotGame game;
	private JPanel tarotGrid;
	private JLabel topPanel;
	private JLabel warningPanel;
	
	private List<TarotListener> listeners;

	
	public TarotView(TarotGame game){
		this.game = game;
		
		this.listeners = new ArrayList<TarotListener>();
		
		this.tarotGrid = drawGame();
		this.topPanel = drawTopPanel();
		this.warningPanel = new JLabel(" ");;
		
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(tarotGrid, BorderLayout.CENTER);
		add(warningPanel, BorderLayout.SOUTH);
	}
	
	
	// Constructing View
	public JPanel drawGame(){
		JPanel panel = new JPanel(new GridLayout(3,3));
		
		PlayerPanel p1Panel = new PlayerPanel(this, game.getP1());
		PlayerPanel p2Panel = new PlayerPanel(this, game.getP2());
		PlayerPanel p3Panel = new PlayerPanel(this, game.getP3());
		PlayerPanel p4Panel = new PlayerPanel(this, game.getP4());

		panel.add(new JLabel(" "));
		panel.add(p4Panel);
		panel.add(new JLabel(" "));
		panel.add(p1Panel);
		panel.add(middlePanel()); // centerPanel
		panel.add(p3Panel);
		panel.add(new JLabel(" "));
		panel.add(p2Panel);
		panel.add(new JLabel(" "));
		
		return panel;
	}
	
	
	public JLabel drawTopPanel(){
		if (game.getStatus() == TarotGame.Status.CONTRACT){
			return new JLabel("Pick Contract: " + game.getWhoseTurn());
		} else if (game.getStatus() == TarotGame.Status.CHIEN){
			return new JLabel("Make Chien: " + game.getContract().getPlayer());
		} else if (game.getStatus() == TarotGame.Status.PLAY){
			return new JLabel(game.getContract() + ", " + game.getContract().pointsNeeded() + " points needed: " + game.getWhoseTurn() + "'s turn");
		} else
			return new JLabel();
		
	}
	
	
	private JPanel middlePanel(){
		if(game.getStatus() == TarotGame.Status.CONTRACT){
			return new SelectContractPanel(this ,game.getWhoseTurn()); // Contract Panels
		} else if (game.getStatus() == TarotGame.Status.CHIEN){
			GridBagConstraints c = new GridBagConstraints();
			JPanel panel = new JPanel(new GridBagLayout());
			c.anchor = GridBagConstraints.CENTER;
			

			for (TarotCard card: game.getChien() )
				panel.add(new CardLabel(card, game.getContract().getPlayer()), c );
			
			return panel; // chienPanel
		} else if (game.getStatus() == TarotGame.Status.PLAY){
			return new FoldPanel(game); // foldPanel
		} else{
			return endPanel(); // End Panel
		}
	}
	
	
	private JPanel endPanel(){
		
		JPanel panel = new JPanel(new GridLayout(2,1));
		
		int diff = (game.getContract().pointsNeeded() - game.getContract().getPlayer().getPoints());
		
		if (diff >= 0)
			panel.add(new JLabel(game.getContract().getPlayer() + " won the game by " + diff + " points."));
		else
			panel.add(new JLabel(game.getContract().getPlayer() + " lost the game by " + diff + " points."));
		
		JLabel restartButton = new JLabel("Restart");
		restartButton.addMouseListener(this);
		panel.add(restartButton);
		
		return panel;
	}
	
	
	
	// Getters
	public TarotGame getGame(){
		return game;
	}
	
	
	// Listener Methods
	public void notifyObservers(Object o){
		for (TarotListener l: listeners)
			l.update(o);
	}
	public void addTarotListener(TarotListener l){
		listeners.add(l);
	}
	public void removeTarotListener(TarotListener l){
		listeners.remove(l);
	}
	
	
	// Mouse Events
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		
		JLabel bottomPanel;
		if (arg1 instanceof TarotException)
			bottomPanel = new JLabel( ((TarotException) arg1).getMessage() );
		else
			bottomPanel = new JLabel(" ");
		
		removeAll();
		
		add(drawTopPanel(), BorderLayout.NORTH);
		add(drawGame(), BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		revalidate();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Object o;
		
		if (e.getSource() instanceof ContractPanel)
			o = ( (ContractPanel) e.getSource()).getContract();
		else if (e.getSource() instanceof CardLabel)
			o = new PlayedCard( (CardLabel) e.getSource() );
		else if (e.getSource() instanceof JLabel){
			o = this;
		}
		else o = null;
		
		notifyObservers(o);
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	
	

}
