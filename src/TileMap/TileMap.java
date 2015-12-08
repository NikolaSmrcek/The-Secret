package TileMap;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	
	//pozicija
	private double x;
	private double y;
	
	//ogranièenja
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double ugladenje; //za ugladit kretnje kamere
	
	//varijable potrebne za mapu
	private int[][] mapa;
	private int velicinaTile;
	private int brojRedova;
	private int brojStupaca;
	private int sirina;
	private int visina;
	
	//Tileset (tileovi od kojih se sastoji mapa)
	private BufferedImage tileset;
	private int brojTilePop;
	private Tile[][] tilovi;
	
	//za crtanje same mape
	private int pomakReda;
	private int pomakStupca;
	private int brojRedazaCrtanje;
	private int brojStupacazaCrtanje;
	
	//efekti
	private boolean shaking;
	private int intensity;
	
	public TileMap(int velicinaTile){
		this.velicinaTile = velicinaTile;
		//+ dva zato jer ce nam Tile bit velicine 30x30 pa da dobijemo brojeve djeljive s velicinom ekrana
		brojRedazaCrtanje = GamePanel.VISINA  / velicinaTile + 2; 
		brojStupacazaCrtanje = GamePanel.SIRINA  / velicinaTile + 2;
		ugladenje = 0.07;
	}
	
	public void ucitavanjeTile(String s){
		
		try{
			
			tileset = ImageIO.read(getClass().getResourceAsStream(s)); //ucitavamo sliku koju smo prenijeli kao argument
			
			brojTilePop = tileset.getWidth() / velicinaTile;
			tilovi = new Tile[2][brojTilePop];
			
			BufferedImage dioslike;
			
			for(int stupac = 0; stupac < brojTilePop;stupac++){
				dioslike = tileset.getSubimage(stupac * velicinaTile, 0, velicinaTile, velicinaTile);
				//visina i sirina podslike ( izrezane slike je varijabla velicina Tile-a jer je svaki Tile 30x30
				
				tilovi [0][stupac] = new Tile(dioslike, Tile.NORMALNI); //prvi red u TileSetu je uvik NORMALNI (nas nacin rada)
				
				dioslike = tileset.getSubimage(stupac * velicinaTile, velicinaTile, velicinaTile, velicinaTile);
				//drugi argument nam govori odakle ce poceti rezati tj koliko da se spusti od originalne slike po visini
				tilovi [1][stupac] = new Tile(dioslike, Tile.BLOKIRANI);
				//svi Tile-ovi drugog reda su Blokiranog tipa kroz koje nas igrac nece moci proci

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void ucitavanjeMape(String s){
		
		try{
			
			InputStream in  = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			brojStupaca = Integer.parseInt(br.readLine());
			brojRedova = Integer.parseInt(br.readLine());
			//jer u samoj datoteci .map piše u prvom redu koliko je stupaca na mapi, a u drugom koliko je redova, dalje su same vrijednosti polja koje predstavljaju mapu
			
			mapa = new int[brojRedova][brojStupaca];
			sirina = brojStupaca * velicinaTile; // | | | * velicina = 90 pixela
			visina = brojRedova * velicinaTile; // - - - - * velicina = 120 pixela
			
			
			
			xmin = GamePanel.SIRINA - sirina;
			xmax = 0;
			ymin = GamePanel.VISINA - visina;
			ymax = 0;
			
			String delims = "\\s+";
			for(int red = 0; red < brojRedova; red++){
				
				String linija = br.readLine();
				String[] tokeni = linija.split(delims);
				for(int stupac = 0; stupac < brojStupaca; stupac++){
					mapa[red][stupac] = Integer.parseInt(tokeni[stupac]);
				}
				//ccitamo prvi red iz .map
				//odvajamo procitano u polje tokeni gdje svaki element sadrzi vrijednsot TIle-a
				//prolazimo po mapi u prvoj iteraci prvi red popunjavamo vrijednosti iz tokena odnosno iz prvog reda .map
				//na kraju dobivamo dvodimenzionalno polje redaka i stupaca koliko ih je u .map sa identifikatora svakog Tile-a
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getVelicinaTile() {return velicinaTile;}
	public double getX() {return x;}
	public double getY() {return y;}
	public int getSirina() {return sirina;}
	public int getVisina() {return visina;}
	public int getBrojRedova() {return brojRedova;}
	public int getBrojStupaca() {return brojStupaca;}
	
	public int getTip(int red,int stupac){
		
		int rs = mapa[red][stupac];
		int r = rs / brojTilePop;
		int s = rs % brojTilePop;
		
		return tilovi[r][s].getTip();
	}
	
	
	
	public boolean isShaking() {return shaking;}
	
	public void setUgladenje(double d){ugladenje = d;}
	public void setShaking(boolean a,int c) {shaking=a;intensity=c;}
	
	public void setBounds(int a,int b,int c,int d){
		
		xmin = GamePanel.SIRINA - a;
		ymin = GamePanel.VISINA - b;
		xmax = c;
		ymax = d;
	}
	
	public void setPosition(double x,double y){
		this.x += (x - this.x) * ugladenje;
		this.y += (y - this.y) * ugladenje;

		
		
		popraviOgranicenja();
		
		pomakStupca = (int) - this.x / velicinaTile;
		pomakReda = (int) - this.y / velicinaTile;
	}
	
	public int getPomakRed() {return pomakReda;}
	public int getPomakStupac() {return pomakStupca;}
	
	public void makniTile(int tr,int ts){
		mapa[tr][ts] = 0;
	}
	
	public void dodajTile(int tr,int ts){
		mapa[tr][ts] = 32;
	}
	
	public void popraviOgranicenja(){
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void azuziranje(){
		if(shaking){
			this.x += Math.random() * intensity - intensity / 2;
			this.y += Math.random() * intensity - intensity / 2;
 		}
	}
	
	public void crtanje(Graphics2D g){
		
		for(int red = pomakReda; red < pomakReda + brojRedazaCrtanje; red++){
			if(red >= brojRedova) break;
			
			for(int stupac = pomakStupca;stupac < pomakStupca + brojStupacazaCrtanje; stupac++){
				if(stupac >= brojStupaca) break;
				if(mapa[red][stupac] == 0) continue;
				
				int rs = mapa[red][stupac];
				int r = rs / brojTilePop;
				int s = rs % brojTilePop;
				
				g.drawImage(tilovi[r][s].getSlika() , (int)x + stupac * velicinaTile, (int)y + red * velicinaTile, null);
			}
		}
		
	}
	
}
