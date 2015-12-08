package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import Audio.Skatulica;
import Handlers.Highscore;
import Handlers.Keys;
import Main.GamePanel;

public class Rekordi extends GameState{
	
	private final Font FONT = new JLabel().getFont();
	
	private String [][] rekordi;
	
	public Rekordi(GameStateManager gsm){
		super(gsm);
		rekordi = Highscore.procitajHighScore();
	}
	
	public void inicijalizacija() {}
	
	public void azuriranje(){
		rukovanjeUnosom();
	}
	
	public void crtanje(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.SIRINA*2, GamePanel.VISINA);
		g.setColor(Color.WHITE);
		g.setFont(FONT);
		g.drawString("Rekordi", GamePanel.SIRINA/2 - 20, 30);
		g.drawString("Novcici",30,55);
		g.drawString("Korisnik",105,55);
		g.drawString("Zombi",180,55);
		g.drawString("Vrijeme",250,55);

		for(int i = 0;i< rekordi.length;i++){
			if(i == 0){
				g.setColor(Color.YELLOW);
			}
			if(i == 1){
				g.setColor(Color.LIGHT_GRAY);
			}
			if(i == 2){
				g.setColor(Color.ORANGE);
			}
			if( i>2){
				g.setColor(Color.WHITE);
			}
			g.drawString(rekordi[i][0],50,80 + (i * 30));
			g.drawString(rekordi[i][1],105,80 + (i * 30));
			g.drawString(rekordi[i][2],190,80 + (i * 30));
			g.drawString(rekordi[i][3],260,80 + (i * 30));
		}
	}
	
	public void rukovanjeUnosom(){
		
		if(Keys.pritisnut(Keys.ESCAPE)) {
			Skatulica.stop("rekordi");
			gsm.postaviRekorde(false);
		}
		
	}
	
	
}
