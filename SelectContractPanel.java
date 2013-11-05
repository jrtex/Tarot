package v05;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SelectContractPanel extends JPanel{
	
	private TarotView view;
	private Player player;

	
	public SelectContractPanel(TarotView view, Player player){
		this.view = view;
		this.player = player;
		
		add(drawPanel());
	}
	
	
	// Construct Panel
	private JPanel drawPanel(){
		JPanel p = new JPanel( new GridLayout(3,2) );
		
		Contract c0 = new Contract(player, 0, view.getGame());
		Contract c1 = new Contract(player, 1, view.getGame());
		Contract c2 = new Contract(player, 2, view.getGame());
		Contract c3 = new Contract(player, 4, view.getGame());
		Contract c4 = new Contract(player, 6, view.getGame());
		
		ContractPanel p0 = new ContractPanel(c0);
		ContractPanel p1 = new ContractPanel(c1);
		ContractPanel p2 = new ContractPanel(c2);
		ContractPanel p3 = new ContractPanel(c3);
		ContractPanel p4 = new ContractPanel(c4);
		
		p0.addMouseListener(view);
		p1.addMouseListener(view);
		p2.addMouseListener(view);
		p3.addMouseListener(view);
		p4.addMouseListener(view);
		
		p.add(new JLabel( player.getName() + " pick contract:  "));
		p.add(p0);
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		
		return p;
	}
	

}
