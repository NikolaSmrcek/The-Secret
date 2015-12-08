package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Handlers.Sadrzaj;
import Main.GamePanel;
import TileMap.TileMap;

public class Zombie extends Enemy {
	
	private BufferedImage[] sprites;
	private Igrac igrac;
	private boolean aktivan;
	
	public Zombie(TileMap tm,Igrac p){
		
		super(tm);
		igrac = p;
		
		hp = maxHP = 15;
		
		sirina = 32;
		visina = 40;
		
		csirina = 22;
		cvisina = 26;
		
		steta = 1;
		brzinaKretnje = 0.3;
		brzinaPadanja = 0.17;
		maxBrzinaPadanja = 4.0;
		pocetakSkakanja = -5;
		
		sprites = Sadrzaj.Zombie[0];

		
		animacija.setFrames(sprites);
		animacija.setOdgoda(30);
		
		left = true;
		gledaDesno = false;

	}
	
	private void dohvatiSljedecuPoziciju(){
		if(left) dx = -brzinaKretnje;
		else if(right) dx = brzinaKretnje;
		else dx=0;
		
		if(pada){
			dy += brzinaPadanja;
			if(dy > maxBrzinaPadanja) dy = maxBrzinaPadanja;
		}
		if(skace && !pada){
			dy = pocetakSkakanja;
		}
		
	}
	
	public void azuriranje(){
		
		if(!aktivan){
			if(Math.abs(igrac.getX() - x) < GamePanel.SIRINA) aktivan = true;
			return;
		}
		
		//provjera jeli se presta "treskat"
		if(flinching){
			flinchBrojac++;
			if(flinchBrojac == 12) flinching = false;
		}
		
		dohvatiSljedecuPoziciju();
		provjeriTileMapKoliziju();
		izracunajKuteve(x, ydest +1);
		if(!bottomLeft){
			left = false;
			right = gledaDesno = true;
		}
		if(!bottomRight){
			left = true;
			right = gledaDesno = false;
		}
		
		setPoziciju(xtemp, ytemp);
		
		if(dx == 0){
			left = !left;
			right = !right;
			gledaDesno = !gledaDesno;
		}
		
		animacija.azuriranje();
		
	}
	
	public void crtanje(Graphics2D g){
		if(flinching){
			if(flinchBrojac == 0 || flinchBrojac == 2) return;
		}
		
		super.crtanje(g);
	}
	
	
}
