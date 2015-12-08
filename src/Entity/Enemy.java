package Entity;

import java.awt.image.BufferedImage;

import Handlers.Sadrzaj;
import TileMap.TileMap;

public class Enemy extends MapObject {

	
	protected int hp;
	protected int maxHP;
	
	private boolean mrtav;
	protected int steta;
	protected boolean ukloni;
	
	
	protected boolean flinching;
	protected long flinchBrojac;
	
	public Enemy(TileMap tm){
		super(tm);
		ukloni = false;
		

	}
	
	public boolean jeMrtav() {return mrtav;}
	public boolean trebaUklonit() {return ukloni;}
	
	public int getStetu() {return steta;}
	
	public void udari(int steta){
		
		if(mrtav || flinching) return;
		//zvuk
		this.hp -= steta;
		if(hp < 0) hp = 0;
		if(hp == 0) {
			mrtav = true;
		}
		if(mrtav) ukloni = true;
		flinching = true;
		flinchBrojac = 0;
		
	}
	
	public void azuriranje(){}
}
