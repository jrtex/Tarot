package v05;

import java.awt.Color;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ContractPanel extends JLabel{
	
	private Contract contract;
	
	
	public ContractPanel(Contract contract){
		super(contract.name());
		
		this.contract = contract;
	}
	
	
	// Getter
	public Contract getContract(){
		return contract;
	}
	
	
	// Modify Panel
	public void colorPanel(Color color){
		this.setForeground(color);
	}

}
