package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage slika;
	private int tip;
	
	//tipovi tile-a NORMAL igrac prolazi, BLOKIRANI igrac nemoze proci
	
	public static final int NORMALNI = 0;
	public static final int BLOKIRANI = 1;
	
	public Tile(BufferedImage slika,int tip){
		this.slika = slika;
		this.tip = tip;
	}
	
	public BufferedImage getSlika(){return slika;}
	public int getTip(){ return tip;}
}
