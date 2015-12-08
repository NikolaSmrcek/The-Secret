package GameState;

import java.awt.Graphics2D;


public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void inicijalizacija();
	public abstract void azuriranje();
	public abstract void crtanje(Graphics2D g);
	public abstract void rukovanjeUnosom();
	
}
