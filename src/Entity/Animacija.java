package Entity;

import java.awt.image.BufferedImage;

public class Animacija {
	
	private BufferedImage[] frames;
	private int trenutniFrame;
	private int brojFrame;
	
	private int brojac;
	private int odgoda;
	
	private int brojPonavljanja;
	
	public Animacija(){
		brojPonavljanja = 0;
	}
	
	public void setFrames(BufferedImage[] frames){
		
		this.frames = frames;
		trenutniFrame = 0;
		brojac = 0;
		brojPonavljanja = 0;
		odgoda = 2;
		brojFrame = frames.length;
	}
	
	public void setOdgoda(int a){odgoda = a;}
	public void setTrenutniFrame(int a) {trenutniFrame = a;}
	public void setBrojFrame(int a) {brojFrame = a;}
	
	public void azuriranje(){
		
		if(odgoda == -1) return;
		brojac++;
		
		if(brojac == odgoda){
			trenutniFrame++;
			brojac  = 0;
		}
		if(trenutniFrame == brojFrame){
			trenutniFrame = 0;
			brojPonavljanja++;
		}
	}
	
	public int getFrame() {return trenutniFrame;}
	public int getBrojac() {return brojac;}
	public BufferedImage getSlika() {return frames[trenutniFrame];}
	public boolean pusten() {return brojPonavljanja > 0;}
	public boolean pustenOdredeno(int a) {return brojPonavljanja == a;}
	
}
