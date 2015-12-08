package TileMap;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Pozadina {

	private BufferedImage slika;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private int visina;
	private int sirina;
	
	private double xskala;
	private double yskala;
	
	public Pozadina(String s){
		this(s,0.1);
	}
	
	public Pozadina(String s, double d){
		this(s,d,d);
	}
	
	public Pozadina(String s,double d1,double d2){
		try{
			
			slika = ImageIO.read(getClass().getResourceAsStream(s));
			
			sirina = slika.getWidth();
			visina = slika.getHeight();
			xskala = d1;
			yskala = d2;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public Pozadina(String s,double ms,int x,int y,int w,int h){
		try{
			slika = ImageIO.read(getClass().getResourceAsStream(s));
			slika = slika.getSubimage(x, y, w, h);
			sirina = slika.getWidth();
			visina = slika.getHeight();
			xskala = ms;
			yskala = ms;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void postaviPoziciju(double x,double y){
		this.x = (x * xskala) % sirina;
		this.y = (y * yskala) % visina;
	}
	
	public void postaviVektor(double dx,double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void postaviSkalu(double xskala,double yskala){
		this.xskala = xskala;
		this.yskala = yskala;
	}
	
	public void postaviDimenzije(int sirina1,int visina1){
		sirina = sirina1;
		visina = visina1;
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
	
	public void azuriranje() {
		x += dx;
		while(x <= -sirina) x += sirina;
		while(x >= sirina) x -= sirina;
		y += dy;
		while(y <= -visina) y += visina;
		while(y >= visina) y -= visina;
	}
	
	public void crtanje(Graphics2D g){
		
		g.drawImage(slika, (int)x, (int)y, null);
		
		if(x < 0){
			g.drawImage(slika, (int)x + GamePanel.SIRINA, (int)y, null);
		}
		if(x > 0){
			g.drawImage(slika, (int)x - GamePanel.SIRINA, (int)y, null);
		}
		if(y < 0){
			g.drawImage(slika, (int)x, (int)y + GamePanel.VISINA, null);
		}
		if(y > 0){
			g.drawImage(slika, (int)x, (int)y - GamePanel.VISINA, null);
		}
	}
}
