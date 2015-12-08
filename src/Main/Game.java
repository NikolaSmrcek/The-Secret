package Main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args){
		
		JFrame prozor = new JFrame("The Secret");
		prozor.add(new GamePanel());
		prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		prozor.setResizable(false);
		prozor.pack();
		prozor.setLocationRelativeTo(null);
		prozor.setVisible(true);
		
	}
	
}
