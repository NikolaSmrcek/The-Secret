package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import GameState.GameStateManager;
import Handlers.Keys;
import Main.GamePanel;

public class PauseState extends GameState {
	
private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		// fonts
		font = new Font("SansSerif", Font.BOLD, 17);
		
	}
	
	public void inicijalizacija() {}
	
	public void azuriranje() {
		rukovanjeUnosom();
	}
	
	public void crtanje(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.SIRINA*2, GamePanel.VISINA);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Igra pauzirana", GamePanel.SIRINA/2 - 50, 30);
		g.drawString("SPACE - skok", GamePanel.SIRINA/2 - 110,100);
		g.drawString("E - pucanje", GamePanel.SIRINA/2 - 110,130);
		g.drawString("Lijeva strelica - kretnja lijevo", GamePanel.SIRINA/2 - 110,160);
		g.drawString("Desna strelica - kretnja desno", GamePanel.SIRINA/2 - 110,190);
		
	}
	
	public void rukovanjeUnosom() {
		if(Keys.pritisnut(Keys.ESCAPE)) gsm.postaviPauzu(false);
	}

	
}
