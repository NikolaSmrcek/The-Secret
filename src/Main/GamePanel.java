package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Handlers.Keys;


@SuppressWarnings("serial") //da suzbijemo upozorenja koja nam baca Eclipse
public class GamePanel extends JPanel implements Runnable,KeyListener {
	
	//dimenzije igre
	public static final int SIRINA = 320;
	public static final int VISINA = 240;
	public static final int SKALA = 2;
	
	//Dretva igre
	
	private Thread dretva;
	private boolean radi;
	private int FPS = 60;
	private long ciljanoVrijeme = 1000 / FPS;
	
	//slika - da bismo prikazali sliku moramo napraviti objekt
	private BufferedImage slika;
	
	//za snimanje
	private boolean recording = false;
	private int recordingCount = 0;
	
	private Graphics2D g;
	
	//GSM
	
	private GameStateManager gsm;
	
	//ostale varijable
	private boolean slikaj;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(SIRINA * SKALA,VISINA * SKALA));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify(){
		super.addNotify();
		if(dretva == null){
			dretva = new Thread(this);
			addKeyListener(this);
			dretva.start();
		}
	}
	
	
	private void init(){
		slika = new BufferedImage(SIRINA,VISINA, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) slika.getGraphics();
		radi = true;
		
		gsm = new GameStateManager();
	}
	
	public void run() {
		init();
		
		long start;
		long proslo;
		long cekaj;
		
		while(radi){
			
			start = System.nanoTime();
			
			azuriraj();
			crtaj();
			crtajNaEkran();
			
			
			
			proslo = System.nanoTime() - start;
			cekaj = ciljanoVrijeme - proslo / 1000000; //jer je proslo vrijeme u nanosekundama
			
			if(cekaj < 0) cekaj = 5;
			
			try{
				Thread.sleep(cekaj);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		} //petlja igre, ubiti nonstop ce se vrtit jer je radi = true
		
	} // funkcija za pokretanje igre
	
	private void azuriraj(){
		gsm.azuriraj();
		Keys.azuriraj();
	}
	
	private void crtaj(){
		gsm.crtaj(g);
	}
	
	private void crtajNaEkran(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(slika, 0, 0, SIRINA * SKALA, VISINA * SKALA, null);
		g2.dispose();
		if(slikaj) {
			slikaj = false;
			try{
				java.io.File van = new java.io.File("uslikano " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(slika, "gif", van);
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(!recording) return;
		try {
			java.io.File out = new java.io.File("C:\\out\\frame" + recordingCount + ".gif");
			javax.imageio.ImageIO.write(slika, "gif", out);
			recordingCount++;
		}
		catch(Exception e) {}
		
	} //za uzimanje screeshota igre, CTRL + S
	
	public void keyTyped(KeyEvent kljuc) {}
	public void keyPressed(KeyEvent kljuc){
		if(kljuc.isControlDown()){
			if(kljuc.getKeyCode() == KeyEvent.VK_R) {
				recording = !recording;
				return;
			}
			if(kljuc.getKeyCode() == KeyEvent.VK_S){
				slikaj = true;
				return;
				
			}
		}
		 Keys.postaviKljuc(kljuc.getKeyCode(),true);
	} //ako je pritisnuta tipka na tipkovnici
	
	public void keyReleased(KeyEvent kljuc){
		Keys.postaviKljuc(kljuc.getKeyCode(),false);
	}
	
}
