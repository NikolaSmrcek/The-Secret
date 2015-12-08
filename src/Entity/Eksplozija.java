package Entity;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Handlers.Sadrzaj;
import TileMap.TileMap;

public class Eksplozija extends MapObject {

	private BufferedImage[] sprites;
	
	private boolean ukloni;
	
	private Point[] tocke;
	private int brzina;
	private double diagBrzina;
	
	public Eksplozija(TileMap tm,int x, int y){
		
		super(tm);
		this.x = x;
		this.y = y;
		
		sirina = 10;
		visina = 10;
		
		brzina = 2;
		diagBrzina = 1.21;
			
		sprites = Sadrzaj.Eks[0];
		
		animacija.setFrames(sprites);
		animacija.setOdgoda(7);
		
		tocke = new Point[8];
		
		for(int i = 0; i < tocke.length; i++){
			tocke[i] = new Point(x, y);
		}
		
	}
	
	public void azuriranje(){
		
		animacija.azuriranje();
		if(animacija.pusten()){
			ukloni = true;
		}
		
		tocke[0].x += brzina;
		tocke[1].x += diagBrzina;
		tocke[1].y += diagBrzina;
		tocke[2].y += brzina;
		tocke[3].x -= diagBrzina;
		tocke[3].y += diagBrzina;
		tocke[4].x -= brzina;
		tocke[5].x -= diagBrzina;
		tocke[5].y += diagBrzina;
		tocke[6].y -= brzina;
		tocke[7].x += diagBrzina;
		tocke[7].y -= diagBrzina;
		
	}
	
	public boolean trebaUklonit() {return ukloni;}
	
	public void crtanje(Graphics2D g){
		setPozicijuMape();
		for(int i = 0; i < tocke.length; i++){
			
			g.drawImage(animacija.getSlika(), (int) (tocke[i].x + xmap - sirina / 2), (int) (tocke[i].y + ymap - visina/2), null );
			
		}
		
	}
	
}
