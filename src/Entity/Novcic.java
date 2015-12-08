package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Handlers.Sadrzaj;
import Main.GamePanel;
import TileMap.TileMap;

public class Novcic extends MapObject {
	
	private BufferedImage[] sprites;
	private Igrac igrac;
	private boolean aktivan;
	private int vrsta;
	
	private boolean ukloni;
	
	public Novcic(TileMap tm,Igrac p,int vrsta){
		
		super(tm);
		
		sirina = 20;
		visina = 20;
		this.vrsta = vrsta;
		csirina = 18;
		cvisina = 20;
		brzinaPadanja = 0.7;
		maxBrzinaPadanja = 4.0;
		aktivan = true;
		ukloni = false;
		
		
		if(vrsta == 0){
			sprites = Sadrzaj.Novcic[0];
		}
		else{
			sprites = Sadrzaj.Novcic_slova[0];
		}
		
		animacija.setFrames(sprites);
		animacija.setOdgoda(9);

	}

	public boolean trebaUklonit(){return ukloni;}
	
	public void pokupljen(){
		
		ukloni = true;
		
	}
	
	public int getVrsta() {return vrsta;}
	
	public void azuriranje(){
		
		if(!aktivan){
			if(Math.abs(igrac.getX() - x) < GamePanel.SIRINA) aktivan = true;
			return;
		}
		

		provjeriTileMapKoliziju();

		setPoziciju(xtemp, ytemp);
		animacija.azuriranje();
		
	}
	
	public void crtanje(Graphics2D g){
	
		
		super.crtanje(g);
	}
	
}
