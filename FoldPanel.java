package v05;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FoldPanel extends JPanel {

	private Fold fold;
	
	public FoldPanel(TarotGame game){
		this.fold = game.getCurrentFold();
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints e = new GridBagConstraints();
		GridBagConstraints s = new GridBagConstraints();
		GridBagConstraints w = new GridBagConstraints();
		GridBagConstraints n = new GridBagConstraints();
		
		e.anchor = GridBagConstraints.LINE_START;
		e.weighty = 0.0;
		e.weightx = 1.0;
		s.anchor = GridBagConstraints.PAGE_END;
		s.weighty = 1.0;
		s.weightx = 0.0;
		w.anchor = GridBagConstraints.LINE_END;
		w.weighty = 0.0;
		w.weightx = 1.0;
		n.anchor = GridBagConstraints.PAGE_START;
		n.weighty = 1.0;
		n.weightx = 0.0;
		
		for (PlayedCard card: fold.getFold()){
			CardLabel label = new CardLabel(card);
			
			if (card.getPlayer() == game.getP4())
				add(label, n);
			else if (card.getPlayer() == game.getP1())
				add(label, w);
			else if (card.getPlayer() == game.getP3())
				add(label, e);
			else
				add(label, s);
		}

	}
	
}
