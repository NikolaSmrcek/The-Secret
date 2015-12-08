package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Metak extends MapObject {

	private boolean hit;
	private boolean ukloni;
	private boolean ispucan;
	
	
	private ArrayList<BufferedImage[]> sprites;
	
	private static final int ISPUCAN = 0;
	private static final int LETI = 1;
	private static final int UDARIO = 2;
	
	private final int[] NUMFRAMES = {
			3,3,3
	};
	
	private final int[] FRAMESIRINE = {
			30,30,30
	};
	
	private final int[] FRAMEVISINA = {
			25,25,25
	};
	
	private final int[] SPRITEODGODE = {
		2,-1,3	
	};
	
	public Metak(TileMap tm, boolean desno){
		
		super(tm);
		
		gledaDesno = desno;
		
		brzinaKretnje = 3.8;
		if(desno) dx = brzinaKretnje;
		else dx = -brzinaKretnje;
		
		visina = 10;
		sirina = 2;
		csirina = 10;
		cvisina = 2;
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Objekti/metak.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			int brojac=0;
			for(int i = 0; i < NUMFRAMES.length;i++){
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0;j < NUMFRAMES[i]; j++){
					bi[j] = spritesheet.getSubimage(
							j * FRAMESIRINE[i], 
							brojac, 
							FRAMESIRINE[i], 
							FRAMEVISINA[i]);
				}
				sprites.add(bi);
				brojac += FRAMEVISINA[i];
			}
			
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		//postaviAnimaciju(ISPUCAN);
		
		postaviAnimaciju(LETI);
		
	} // konstruktor
	
	private void postaviAnimaciju(int i){
		animacija.setFrames(sprites.get(i));
		animacija.setOdgoda(SPRITEODGODE[i]);
		sirina = FRAMESIRINE[i];
		visina = FRAMEVISINA[i];
	}
	
	public void postaviUdarac(){
		
		if(hit) return;
		hit = true;
		postaviAnimaciju(UDARIO);
		dx = 0;
		provjeriMicanje();
	}
	
	private void provjeriMicanje(){
		if(trenutniRed == 1 && trenutniStupac == 22){
			tileMapa.makniTile(1, 23);
		}
		if(trenutniRed == 6 && trenutniStupac == 27){
			tileMapa.makniTile(5, 26);
			tileMapa.makniTile(6, 26);
			tileMapa.makniTile(7, 26);
			tileMapa.makniTile(6, 25);
			tileMapa.makniTile(5, 24);
			tileMapa.makniTile(6, 24);
			tileMapa.makniTile(7, 24);
			tileMapa.makniTile(5, 22);
			tileMapa.makniTile(6, 22);
			tileMapa.makniTile(7, 22);
			tileMapa.dodajTile(6, 21);
		}
		
		if(trenutniRed == 16 && trenutniStupac == 19){

			tileMapa.dodajTile(17, 15);
			tileMapa.dodajTile(17, 14);
			tileMapa.dodajTile(17, 13);
			tileMapa.dodajTile(17, 16);
			tileMapa.dodajTile(17, 17);
		}
		if(trenutniRed == 16 && trenutniStupac == 17){
			tileMapa.makniTile(16, 18);
			tileMapa.makniTile(15, 18);
		}
		
	}
	
	public boolean trebaUklonit() {return ukloni;}
	
	public int getTrenutniRedMetka() {return trenutniRed;}
	public int getTrenutniStupacMetka() {return trenutniStupac;}
	
	public void azuriranje() {
		
		provjeriTileMapKoliziju();
		setPoziciju(xtemp, ytemp);
		
		
		
		if(dx == 0 && !hit){
			postaviUdarac();
		}
		
		animacija.azuriranje();
		
		if(hit && animacija.pusten()){
			ukloni = true;
		}
		
	}
	
	public void crtanje(Graphics2D g){
		setPozicijuMape();
		
		super.crtanje(g);
	}
	
}
