package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import Audio.Skatulica;
import Handlers.Keys;
import Main.GamePanel;

public class MeniStanje extends GameState {
	
	private BufferedImage strelica;
	private int trenutniIzbor = 0;
	
	private String[] opcije = {
		"Start",
		"Rekordi",
		"Izlaz"
	};
	
	private Color bojaNaslova;
	private Font  fontNaslova;
	
	private Font tekst1;
	private Font tekst2;
	
	
	
	public MeniStanje(GameStateManager gsm){
		
		super(gsm);
		
		try{
			//uèitavanje strelice za meni
			strelica = ImageIO.read(getClass().getResourceAsStream("/Meni_strelica/arrow_menu.gif"));
			//tekst i font
			bojaNaslova = Color.BLUE;
			fontNaslova = new Font("Papyrus", Font.BOLD, 26);
			tekst1 = new Font("Gill Sans MT", Font.PLAIN, 14);
			tekst2 = new Font("Comic Sans MS", Font.PLAIN, 9);
			
			//uèitavanje zvuka
			Skatulica.ucitaj("/SFX/meni_opcija.mp3", "meni_opcija");
			Skatulica.ucitaj("/Muzika/menu_pozadina.mp3", "meni_pozadina");
			Skatulica.ucitaj("/SFX/meni_odabir.mp3", "meni_odabir");
			Skatulica.ucitaj("/Muzika/rekordi.mp3", "rekordi");
			Skatulica.play("meni_pozadina");
			
			kreirajHS();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void inicijalizacija(){
		
	}
	
	public void azuriranje(){
		rukovanjeUnosom();
	}
	
	public void crtanje(Graphics2D g){
		//crtanje pozadine
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.SIRINA, GamePanel.VISINA- 20);
		//crtamo naslov
		g.setColor(bojaNaslova);
		g.setFont(fontNaslova);
		g.drawString("The SECRET", 60, 90);
		
		//crtamo opcije za odabir (igra i izlaz)
		g.setFont(tekst1);
		g.setColor(Color.white);
		g.drawString("Start", 145, 140);
		g.drawString("Rekordi", 145, 160);
		g.drawString("Izlaz", 145, 180);
		
		//crtamo strelicu
		if(trenutniIzbor == 0)g.drawImage(strelica, 125, 127, null);
		else if(trenutniIzbor == 1)g.drawImage(strelica, 125, 147, null);
		else if(trenutniIzbor == 2)g.drawImage(strelica, 125, 167, null);
		//ja i mentor
		g.setFont(tekst2);
		g.drawString("Izradio: Nikola Smrèek", 2, GamePanel.VISINA-5);
	}
	

	
	private void odabir(){
		if(trenutniIzbor == 0){
			Skatulica.play("meni_odabir");
			Skatulica.stop("meni_pozadina");
		 	Skatulica.zatvori("meni_pozadina"); //sredit napravit lvl1
		 	gsm.postaviStanje(GameStateManager.LEVEL1STANJE);
		}
		
		if(trenutniIzbor == 1){
			Skatulica.play("meni_odabir");
			Skatulica.stop("meni_pozadina");
			Skatulica.play("rekordi");
		 	gsm.postaviRekorde(true);
		}
		 if(trenutniIzbor == 2) {
			    Skatulica.play("meni_odabir");
			 	Skatulica.stop("meni_pozadina");
			 	Skatulica.zatvori("meni_pozadina");
				System.exit(0);
			}
	}
	
	private void kreirajHS(){
		File file = new File("hg.txt");
		if(!file.exists()){
			try{
				file.createNewFile();
				
				FileOutputStream oFile = new FileOutputStream(file, true);
				oFile.close();
				
				FileWriter fw = new FileWriter("hg.txt");
				PrintWriter pw = new PrintWriter(fw);
				
				pw.println("20");
				pw.println("George");
				pw.println("6");
				pw.println("1:50");
				pw.println("18");
				pw.println("Edward");
				pw.println("5");
				pw.println("2:30");
				pw.println("1");
				pw.println("Unknown");
				pw.println("3");
				pw.println("3:10");
				pw.println("5");
				pw.println("Valan");
				pw.println("5");
				pw.println("4:40");
				pw.println("1");
				pw.println("Destiny");
				pw.println("1");
				pw.println("10:10");
				
				
				pw.close();
				fw.close();
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void rukovanjeUnosom(){
		
		if(Keys.pritisnut(Keys.ENTER)) odabir();
		if(Keys.pritisnut(Keys.UP)){
			if(trenutniIzbor > 0){
				Skatulica.play("meni_opcija");
				trenutniIzbor--;
			}
		}//gori tipka
		if(Keys.pritisnut(Keys.DOWN)){
			if(trenutniIzbor < opcije.length - 1){
				Skatulica.play("meni_opcija");
				trenutniIzbor++;
			}
		}
		
	}
}
