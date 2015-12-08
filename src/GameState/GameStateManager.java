package GameState;

import java.awt.Graphics2D;

import Audio.Skatulica;
import Main.GamePanel;

public class GameStateManager {

	private GameState[] stanjaIgre;
	private int trenutnoStanje;
	
	private PauseState stanjePauze;
	private Rekordi rekordi;
	private boolean pauza;
	private boolean rekord;
	
	public static final int BROJSTANJAIGRE = 16;
	public static final int MENISTANJE = 0;
	public static final int LEVEL1STANJE = 2;
	public static final int REKORDI = 4;
	
	public GameStateManager(){
		
		Skatulica.init();
		
		stanjaIgre = new GameState[BROJSTANJAIGRE];
		
		stanjePauze = new PauseState(this);
		pauza = false;
		rekord = false;
		trenutnoStanje = MENISTANJE;
		ucitajStanje(trenutnoStanje);

	}
	
	private void ucitajStanje(int stanje){
		
		if(stanje == MENISTANJE){
			stanjaIgre[stanje] = new MeniStanje(this);
		}
			
		else if(stanje == LEVEL1STANJE)
			stanjaIgre[stanje] = new Level1Stanje(this);

	}
	
	private void isprazniStanje(int stanje){
		stanjaIgre[stanje] = null;
	}
	
	public void postaviStanje(int stanje){
		
		isprazniStanje(trenutnoStanje);
		trenutnoStanje = stanje;
		ucitajStanje(trenutnoStanje);
		
	}
	public void nastaviMuziku(){
		Skatulica.nastavi("meni_pozadina");
	}
	public void postaviPauzu(boolean b) {pauza = b;}
	public void postaviRekorde(boolean b){
		if(b == false){
			if(trenutnoStanje == MENISTANJE){
				nastaviMuziku();
			}
		}
		else if(b == true){
			rekordi = new Rekordi(this);
		}
		rekord = b;
		}
	
	public void azuriraj(){
		  
		  if(pauza){
		   stanjePauze.azuriranje();
		   return;
		  }
		 
		  if(rekord){
			  rekordi.azuriranje();
			  return;
		  }
		  
		if(stanjaIgre[trenutnoStanje] != null) stanjaIgre[trenutnoStanje].azuriranje();
	}
	
	public void crtaj(Graphics2D g){
		
		  if(pauza){
		   stanjePauze.crtanje(g);
		   return;
		   }
		  
		  if(rekord){
			   rekordi.crtanje(g);
			   return;
		   }
		 
		if(stanjaIgre[trenutnoStanje] != null) stanjaIgre[trenutnoStanje].crtanje(g);
		else{
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.SIRINA, GamePanel.VISINA);
		}
		
	}
}
