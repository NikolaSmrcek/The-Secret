package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JTextField;

import Audio.Skatulica;
import Entity.Eksplozija;
import Entity.Enemy;
import Entity.HUD;
import Entity.Igrac;
import Entity.Novcic;
import Entity.Zombie;
import Handlers.Highscore;
import Handlers.Keys;
import Main.GamePanel;
//import Main.GamePanel;
import TileMap.Pozadina;
import TileMap.TileMap;

public class Level1Stanje extends GameState {
	
	private Pozadina pozadina;
	
	private TileMap tileMapa;
	
	private Igrac igrac;
	
	private HUD hud;
	
	private ArrayList<Enemy> protivnici;
	private ArrayList<Eksplozija> ekspo;
	private ArrayList<Novcic> pare;
	private Novcic slovo;
	
	private boolean blokirajUnos;
	private boolean gotovNivo;
	private int eventBrojac = 0;
	private ArrayList<Rectangle> tb;
	
	public Level1Stanje(GameStateManager gsm){
		super(gsm);
		inicijalizacija();
	}
	
	
	public void inicijalizacija(){
		
	    pozadina = new Pozadina("/Pozadina/pozadina.png",1);
		
		//Mapa tilove-a
		tileMapa = new TileMap(30);
		tileMapa.ucitavanjeTile("/Tileset/ruinstileset.gif");
		tileMapa.ucitavanjeMape("/Mape/ts_stage1.map");	

		tileMapa.setBounds(tileMapa.getSirina() - 1 * tileMapa.getVelicinaTile(),
				tileMapa.getVisina(),//- 2 * tileMapa.getVelicinaTile(), 
				0,
				0);
		tileMapa.setUgladenje(1);
		
		tb = new ArrayList<Rectangle>();
		
		Skatulica.ucitaj("/Muzika/pozadina_level1.mp3", "level1");
		Skatulica.loop("level1", 600, Skatulica.getFrames("level1") - 2200);
		Skatulica.ucitaj("/SFX/finito.mp3", "finito");
		//IGrac
		
		igrac = new Igrac(tileMapa);
		igrac.setPoziciju(125, 155);
		igrac.postaviVrime(0);
		
		//hud
		hud = new HUD(igrac);
		
		//protivnici
		protivnici = new ArrayList<Enemy>();
		pare = new ArrayList<Novcic>();
		slovo = new Novcic(tileMapa, igrac, 1);
		
		popuniProtivnicima();
		popuniNovcicima();
		dodajNovcic();
		
		//inicijalizacija igraca
		igrac.inicijalizacija(protivnici,pare,slovo);
		
		//eksplozije
		ekspo = new ArrayList<Eksplozija>();
		
	}
	
	public void popuniProtivnicima(){
		protivnici.clear();
		Zombie z;

		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(140, 70);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(340, 140);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(390, 150);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(800, 70);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(850, 190);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(700, 190);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(775, 285);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(1000, 275);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(1100, 570);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(950, 385);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(730, 430);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(145, 570);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(270, 590);
		protivnici.add(z);
		
		z = new Zombie(tileMapa,igrac);
		z.setPoziciju(450, 430);
		protivnici.add(z);

	}
	
	public void popuniNovcicima(){
		pare.clear();
		
		Novcic n;
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(84, 36);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(462, 216);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(460, 70);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(525, 43);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(735, 11);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(706, 183);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(1171, 265);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(1070, 550);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(967, 387);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(780, 440);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(375, 380);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(277, 587);
		pare.add(n);
		
		n = new Novcic(tileMapa, igrac, 0);
		n.setPoziciju(60, 303);
		pare.add(n);
		
	}
	
	public void dodajNovcic(){

		if(igrac.getSlova() == 0 || igrac.getSlova() == 8){
			slovo.setPoziciju(312, 39);
		}
		
		if(igrac.getSlova() == 1){
			slovo.setPoziciju(764, 160);
		}
		
		if(igrac.getSlova() == 2){
			slovo.setPoziciju(696, 190);
		}
		if(igrac.getSlova() == 3){
			slovo.setPoziciju(100, 492);
		}
		if(igrac.getSlova() == 4 || igrac.getSlova() == 7){
			slovo.setPoziciju(370, 260);
		}
		if(igrac.getSlova() == 5){
			slovo.setPoziciju(733, 534);
		}
		
		if(igrac.getSlova() == 6){
			slovo.setPoziciju(860, 590);
		}
		

		
	}
	
	public void azuriranje(){
		rukovanjeUnosom();
		
		if(igrac.getSlova() == 9){
			gotovNivo = blokirajUnos  = true;
		}
		
		if(gotovNivo){
			eventGotov();
		}
		
		if(igrac.getHP() == 0 || igrac.getY() > tileMapa.getVisina()){
			gsm.postaviStanje(GameStateManager.LEVEL1STANJE);
		}
		

		igrac.azuriranje();
		
		//ponovno punimo protivnicima
		if(igrac.getSekunde() == 30){
			popuniProtivnicima();
		}
		//azuiranje tile mape
		tileMapa.setPosition(GamePanel.SIRINA / 2 - igrac.getX(),(GamePanel.VISINA/2 - igrac.getY())+11);
		tileMapa.azuziranje();
		tileMapa.popraviOgranicenja();
		

		//azuriranje protivnika
		for(int i = 0; i < protivnici.size();i++){
			Enemy e = protivnici.get(i);
			e.azuriranje();
			if(e.jeMrtav()){
				protivnici.remove(i);
				i--;
				ekspo.add(new Eksplozija(tileMapa, e.getX(), e.getY()));
				igrac.setZombi();
				Novcic n = new Novcic(tileMapa, igrac,0);
				n.setPoziciju(e.getX(),e.getY());
				pare.add(n);
				
			}
		}
		//azuriranje novcica
		for(int i = 0; i < pare.size();i++){
			Novcic n = pare.get(i);
			n.azuriranje();
			if(n.trebaUklonit()){
				pare.remove(i);
				i--;
			}
		}
		
		//azuriranje slova

			slovo.azuriranje();
			if(slovo.trebaUklonit()){
				dodajNovcic();
			}
		
	
		//azuriranje eksplozije
		for(int i = 0; i < ekspo.size(); i++){
			ekspo.get(i).azuriranje();
			if(ekspo.get(i).trebaUklonit()){
				ekspo.remove(i);
				i--;
			}
		}
		
	}
	
	public void crtanje(Graphics2D g){
		
		
		
		//crtanje pozadine
		pozadina.crtanje(g);
		
		//crtanje tileMape
		tileMapa.crtanje(g);
		
		//crtanje protivnika
		for(int i = 0;i < protivnici.size();i++){
			protivnici.get(i).crtanje(g);
		}
				
		//crtanje novcica
		for(int i = 0;i < pare.size();i++){
			pare.get(i).crtanje(g);
		}
		
		//crtanje slova
		slovo.crtanje(g);
		
		//crtanje eksplozija
		for(int i = 0;i < ekspo.size();i++){
			ekspo.get(i).crtanje(g);
		}
		
		//crtanje igraca
		igrac.crtanje(g);
		
		//crtanej huda
		hud.crtanje(g);
		
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
	}
	
	public void rukovanjeUnosom(){
		if(Keys.pritisnut(Keys.ESCAPE)) gsm.postaviPauzu(true);
		if(igrac.getHP() == 0 || blokirajUnos) return;
		igrac.setUp(Keys.stanjeKljuca[Keys.UP]);
		igrac.setDown(Keys.stanjeKljuca[Keys.DOWN]);
		igrac.setRight(Keys.stanjeKljuca[Keys.RIGHT]);
		igrac.setLeft(Keys.stanjeKljuca[Keys.LEFT]);
		igrac.setSkakanje(Keys.stanjeKljuca[Keys.BUTTON1]);
		if(Keys.pritisnut(Keys.BUTTON3)) igrac.setPucanj();
		if((Keys.stanjeKljuca[Keys.BUTTON3] && Keys.stanjeKljuca[Keys.RIGHT]) ||
			(Keys.stanjeKljuca[Keys.BUTTON3] && Keys.stanjeKljuca[Keys.LEFT])){
			igrac.setKretnjaPucanj();
		}
		else{
			igrac.setPrestanakSetnjePucnja();
		}
			
	}
	
	public void eventGotov(){
		eventBrojac++;
		if(eventBrojac == 1){
			igrac.stop();
			Skatulica.play("finito");
		}
		else if(eventBrojac == 120){
			tb.clear();
			tb.add(new Rectangle(GamePanel.SIRINA / 2, GamePanel.VISINA / 2,0,0));
			Skatulica.stop("level1");
			Skatulica.zatvori("level1");
		}
		
		else if(eventBrojac > 120) {
			Skatulica.play("finito");
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventBrojac == 180){
			
			Highscore.procitajHighScore();
			if(Highscore.usporediHighScore(igrac.getVrimeUString())){
				String hostname = "Unknown";

				try
				{
				    InetAddress addr;
				    addr = InetAddress.getLocalHost();
				    hostname = addr.getHostName();
				}
				catch (UnknownHostException ex)
				{
				    System.out.println("Hostname can not be resolved");
				}
				Highscore.zapisi(igrac.getPare(), hostname, igrac.getBrojacZombi(), igrac.getVrimeUString());
			}
			gsm.postaviStanje(GameStateManager.MENISTANJE);
		}
	}
	
}
