package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;

public abstract class MapObject {

	//Tile
	protected TileMap tileMapa;
	protected int velicinaTile;
	protected double xmap;
	protected double ymap;
	
	//pozicija i vektor
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimenzije
	protected int sirina;
	protected int visina;
	
	//"kutija" za koliciju odnosno obicni pravokutnik
	
	protected int csirina;
	protected int cvisina;
	
	//kolizija
	
	protected int trenutniRed;
	protected int trenutniStupac;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomRight;
	protected boolean bottomLeft;
	protected boolean insideTile;
	
	//animacija
	protected Animacija animacija;
	protected int trenutnaAkcija;
	protected int proslaAkacija;
	protected boolean gledaDesno;
	
	//kretnje
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean skace;
	protected boolean pada;
	
	//atributi kretnje
	protected double brzinaKretnje;
	protected double maxBrzinaKretnje;
	protected double brzinaUsporavanja;
	protected double brzinaPadanja;
	protected double maxBrzinaPadanja;
	protected double pocetakSkakanja;
	protected double krajSkakanja; //usporavanje od skakanja
	
	//konstruktor
	
	public MapObject(TileMap tm){
		tileMapa = tm;
		velicinaTile = tileMapa.getVelicinaTile();
		animacija = new Animacija();
		gledaDesno = true;
	}
	
	 public boolean intersects(MapObject o){
		 Rectangle r1 = getPravokutnik();
		 Rectangle r2 = o.getPravokutnik();
		 return r1.intersects(r2);
	 }
	 
	 public boolean intersects(Rectangle r){
		 return getPravokutnik().intersects(r);
	 }
	 
	 public boolean sadrzi(MapObject o){
		 Rectangle r1 = getPravokutnik();
		 Rectangle r2 = o.getPravokutnik();
		 return r1.contains(r2);
	 }
	 
	 public boolean sadrzi(Rectangle r){
		 return getPravokutnik().contains(r);
	 }
	 
	 public Rectangle getPravokutnik(){
		 return new Rectangle((int)x - csirina / 2, (int)y - cvisina/2,csirina,cvisina);
	 }
	 
	 
	 
	 
	 public void izracunajKuteve(double x,double y){
		 
		 int leftTile = (int) (x - csirina / 2) / velicinaTile;
		 int rightTile = (int) (x + csirina  / 2 - 1) / velicinaTile;
		 int topTile = (int) (y - cvisina / 2) / velicinaTile;
		 int bottomTile = (int) (y + cvisina / 2 - 1) / velicinaTile;

		 
		 if(topTile < 0 || bottomTile >= tileMapa.getBrojRedova() ||
			leftTile < 0 || rightTile >= tileMapa.getBrojStupaca() )
			 {
				insideTile = topLeft = topRight = bottomLeft = bottomRight = false;
				 return;
			 }

		 int tl = tileMapa.getTip(topTile, leftTile);
		 int tr = tileMapa.getTip(topTile, rightTile);
		 int bl = tileMapa.getTip(bottomTile, leftTile);
		 int br = tileMapa.getTip(bottomTile, rightTile);
		 int nt = tileMapa.getTip(trenutniRed, trenutniStupac);

		 
		 topLeft = tl == Tile.BLOKIRANI;
		 topRight = tr == Tile.BLOKIRANI;
		 bottomLeft = bl == Tile.BLOKIRANI;
		 bottomRight = br == Tile.BLOKIRANI;
		 insideTile = nt == Tile.BLOKIRANI;
		 
	 }
	 

	 
	 public void provjeriTileMapKoliziju(){
		 trenutniRed = (int) y / velicinaTile;
		 trenutniStupac = (int)x /velicinaTile;
		 
		 xdest = x + dx;
		 ydest = y + dy;
		 
		 xtemp = x;
		 ytemp = y;
		 
		 
		 
		
		 izracunajKuteve(x, ydest);
		 if(dy < 0){
			 if(topLeft || topRight){
				 dy = 0;
				 ytemp = trenutniRed * velicinaTile + cvisina /2;
			 }
			 else{
				 ytemp += dy;
			 }
		 }
		 if(dy > 0){
			 if(bottomLeft || bottomRight){
				 dy = 0;
				 pada = false;
				 ytemp = (trenutniRed +1) * velicinaTile - cvisina / 2;
			 }
			 else{
				 ytemp += dy;
			 }
		 }
		 
		 izracunajKuteve(xdest, y);
		 if(dx < 0){
			 if(insideTile){
				 dx = 0;
				 xtemp = ((trenutniStupac+1) * velicinaTile) +4 ;
				 insideTile = false;
			 }
			 else{
				 if(topLeft || bottomLeft){
					 dx = 0;
					 xtemp = trenutniStupac * velicinaTile + csirina / 2;
				 }
				 else{
					 xtemp += dx;
				 } 
			 }
		 }
		 if(dx > 0){
			 if(insideTile){
				 dx = 0;
				 xtemp = ((trenutniStupac) * velicinaTile - csirina /2)-2;
				 insideTile = false;
			 }
			 else{
				 if(topRight || bottomRight){
					 dx = 0;
					 xtemp = (trenutniStupac+1) * velicinaTile - csirina /2;
				 }
				 else{
					 xtemp += dx;
				 }
			 }
			 
		 }
		 
		 
		 
		 
		 
		 if(!pada){
			 izracunajKuteve(x, ydest +1);
			 if(!bottomLeft && !bottomRight){
				 pada = true;
			 }
		 }
		 
	 } //provjera TILEMAP kolizije
	 
	 public int getX() {return (int)x;}
	 public int getY() {return (int)y;}
	 public int getSirina() {return sirina;}
	 public int getVisina() {return visina;}
	 public int getCSirina() {return csirina;}
	 public int getCVisina() {return cvisina;}
	 
	 public boolean gledaDesno() {return gledaDesno;}
	 
	 public void setPoziciju(double x,double y){
		 this.x = x;
		 this.y=y;
	 }
	 
	 public void setVektor(double dx,double dy){
		 this.dx = dx;
		 this.dy = dy;
	 }
	 
	 public void setPozicijuMape(){
		 xmap = tileMapa.getX();
		 ymap = tileMapa.getY();
	 }
	 
	 public void setLeft (boolean b) {left = b;}
	 public void setRight (boolean b) {right = b;}
	 public void setUp (boolean b) {up = b;}
	 public void setDown (boolean b) {down = b;}
	 public void setSkakanje (boolean b) { skace = b;}
	 
	 public boolean nieNaEkranu(){
		 return x + xmap + sirina < 0 ||
				 x + xmap - sirina > GamePanel.SIRINA ||
				 y + ymap + visina < 0 ||
				 y + ymap - visina > GamePanel.VISINA;
	 }
	 
	 public void crtanje(Graphics2D g){
		 setPozicijuMape();
		 if(gledaDesno){
			 g.drawImage(animacija.getSlika(),
					 (int) (x + xmap - sirina / 2),
					 (int) (y + ymap - visina /2), 
					 null);
		 }
		 else{
			 g.drawImage(animacija.getSlika(),
					 (int) (x + xmap - sirina / 2 + sirina),
					 (int) (y + ymap - visina /2),
					 -sirina,
					 visina,
					 null);
		 }
		 
		 //crtanje kutija za koliziju
		 	/*
		   Rectangle r = getPravokutnik();
		   r.x += xmap;
		   r.y += ymap;
		   g.draw(r);
		   
		   //Rectangle rz = new Rectangle(((trenutniStupac - tileMapa.getPomakStupac()) * 30)-(cvisina/2),(trenutniRed - tileMapa.getPomakRed())*30,30,30 );
		   Rectangle rz = new Rectangle((trenutniStupac) * 30,trenutniRed*30,30,30);
		   rz.x += xmap;
		   rz.y += ymap;
		   g.draw(rz);
		   */
		  
	 }
}
