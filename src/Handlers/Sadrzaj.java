package Handlers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Sadrzaj {

	public static BufferedImage[][] Zombie = load("/Objekti/zombi.gif",32,40);
	
	public static BufferedImage[][] Novcic = load("/Objekti/novcic.gif",20,24);
	public static BufferedImage[][] Eks = load("/Objekti/eks.gif",29,29);
	public static BufferedImage[][] Novcic_slova = load("/Objekti/novcic_slova.gif",22,20);
	
	public static BufferedImage [][] load(String s,int sirina,int visina){
		BufferedImage [][] ret;
		try{
			BufferedImage spritesheet = ImageIO.read(Sadrzaj.class.getResourceAsStream(s));
			int sir = spritesheet.getWidth()/sirina;
			int vis = spritesheet.getHeight()/visina;
			ret = new BufferedImage[vis][sir];
			for(int i = 0; i < vis;i++){
				for(int j = 0; j < sir; j++){
					ret[i][j] = spritesheet.getSubimage(j * sirina, i * visina, sirina, visina);
				}
			}
			return ret;
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
}
