package v05;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		Player p1 = new Player("Al");
		Player p2 = new Player("Bo");
		Player p3 = new Player("Cat");
		Player p4 = new Player("Dan");
		
		JFrame frame = new JFrame();
		Controller c = new Controller(p1, p2, p3, p4);
		
		frame.setTitle("Yet Another Tarot Game");
		frame.setContentPane(c.getView());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
