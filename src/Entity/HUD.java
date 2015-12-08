package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class HUD {
	private final Font FONT = new JLabel().getFont();
	private Igrac igrac;
	private BufferedImage zivot;
	private BufferedImage novcic;
	private BufferedImage slova;
	public HUD(Igrac p){
			igrac = p;
			
			try{
				
				BufferedImage slika = ImageIO.read(getClass().getResourceAsStream("/Objekti/Hud.gif"));
				zivot = slika.getSubimage(0, 0, 13, 12);
				novcic = slika.getSubimage(0, 12, 12, 11);
				slova = slika.getSubimage(0, 22, 12, 11);
			}	
			catch(Exception e){
				e.printStackTrace();
			}
			
	}
	
	public void crtanje(Graphics2D g){
		
		g.setFont(FONT);
		
		for(int i = 0;i < igrac.getHP();i++){
			g.drawImage(zivot, 7 + i * 15, 7, null);
		}
		g.setColor(Color.WHITE);
		if(igrac.getSekunde() >= 27 && igrac.getSekunde()<=30){
			g.setColor(Color.RED);
		}
		g.drawString(igrac.getVrimeUString(),290, 235);
		
		
		g.setColor(Color.WHITE);
		g.drawImage(novcic, 287, 7, null);
		g.drawString("x"+String.valueOf(igrac.getPare()), 299, 16);
		g.drawImage(slova, 287, 21, null);
		g.drawString(String.valueOf(igrac.getSlova())+"/9", 301, 32);
	}
}
