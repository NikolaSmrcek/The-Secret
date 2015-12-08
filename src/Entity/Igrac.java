package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.Skatulica;
import TileMap.TileMap;

public class Igrac extends MapObject {
	
	//referenca za neprijatelje - DODAT
	private ArrayList<Enemy> protivnici;
	private ArrayList<Novcic> pare;
	private Novcic slovo;
	
	
	//atributi igraca
	private int hp;
	private int maxHP;
	private int stetaMetka;
	
	private boolean flinching;
	private long flinchBrojac;
	
	private int brojZombi;
	
	private boolean dupliSkok;
	private boolean vecDupliSkok;
	private double dupliSkokPocetak;
	private int slovaBrojac;
	//polje za metke - DODAT
	private ArrayList<Metak> metci;
	
	private int pareBrojac;
	private long time;
	
	//akcije
	private int metakMana;
	private int mana;
	private boolean napadPucanj;
	private boolean napadPucanjSetnja;
	
	//animacije
	private ArrayList<BufferedImage[]> sprites;
	private final int[] NUMFRAMES = {
			8,6,6,1,2,2,2,2
	};
	
	private final int[] FRAMESIRINE = {
			40,40,40,40,40,40,50,40
	};
	
	private final int[] FRAMEVISINA = {
			47,47,47,47,47,47,47,47
	};
	
	private final int[] FRAMEVISINAMINUS ={
			0,0,0,0,47,94,188,188
	};
	
	private final int[] FRAMEPOMAK = {
			0,0,0,0,40,120,200,0
	};
	
	private final int[] SPRITEODGODE= {
		40,4,10,7,10,20,20,3
	};
	

	
	//animacije i njehiove akcije
	
	private static final int IDLE = 0;
	private static final int WALK = 1;
	private static final int WALKSHOOT = 2;
	private static final int SHOOT = 3;
	private static final int JUMP = 4;
	private static final int KDIE = 5;
	private static final int LDIE = 6;
	private static final int CROUCH = 7;
	private static final int FALLING = JUMP;
	
	//konstruktor
	public Igrac(TileMap tm){
		super(tm);
		
		
		sirina = 40;
		visina = 47;
		csirina = 21;
		cvisina = 38;
		
		brzinaKretnje = 2.2;
		maxBrzinaKretnje = 2.2;
		brzinaUsporavanja = 1.6;
		brzinaPadanja = 0.15;
		maxBrzinaPadanja = 4.0;
		pocetakSkakanja = -4.7;
		krajSkakanja = 0.34;
		dupliSkokPocetak = -3;
		
		pareBrojac = 0;
		slovaBrojac = 0;
		stetaMetka = 5;
		brojZombi = 0;
		
		mana = 2000;
		metakMana = 1100;
		
		metci = new ArrayList<Metak>();
		
		gledaDesno = true;
		
		hp = maxHP = 4;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Objekti/igrac.gif"));
			
			int brojac = 0;
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length;i++){
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++){
					bi[j] = spritesheet.getSubimage
							(j * FRAMESIRINE[i] + FRAMEPOMAK[i],
							brojac - FRAMEVISINAMINUS[i],
							FRAMESIRINE[i],
							FRAMEVISINA[i]);
				}
				sprites.add(bi);
				brojac += FRAMEVISINA[i];
			} //punimo polje slikama za animiranje
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		//animacija = new Animacija();
		
		postaviAnimaciju(IDLE);
		
		Skatulica.ucitaj("/SFX/pokupljeni_novcic.mp3","novcic");
		Skatulica.ucitaj("/SFX/pistol.mp3", "pucanj");
		Skatulica.ucitaj("/SFX/uprazno.mp3", "uprazno");
		Skatulica.ucitaj("/SFX/doskok.mp3", "doskok");
		Skatulica.ucitaj("/SFX/skok.mp3", "skok");
	} //konstuktor
	
		

	
	public void inicijalizacija(ArrayList<Enemy> neprijatelji,ArrayList<Novcic> novci,Novcic slovo){
		this.protivnici = neprijatelji;
		this.pare = novci;
		this.slovo = slovo;
	}
	
	public int getZivot() {return hp;}
	public int maxZivot() {return maxHP;}
	
	public int getPare() {return pareBrojac;}
	public int getSlova() {return slovaBrojac;}
	
	public void setSkakanje(boolean b) {
		
		if(b && !skace && pada && !vecDupliSkok){
			dupliSkok = true;
		}
		skace = b;
	}
	

	
	public void setPucanj(){
		
		napadPucanj = true;
	}
	
	public void setKretnjaPucanj(){
		//if(pada || skace) return;
		 napadPucanjSetnja = true;
	}
	
	public void postaviSmrt(){
		hp = 0;
		stop();
	}
	
	public void stop(){
		left = right = up = down  = skace = napadPucanj = napadPucanjSetnja  = false;
		flinching = true;
	}
	
	public String getVrimeUString(){
		int minute = (int) (time / 3600);
		int sekunde = (int) ((time % 3600) / 60);
		return sekunde < 10 ? minute + ":0" + sekunde : minute + ":" + sekunde;
	}
	
	public int getSekunde(){
		int sekunde = (int) ((time % 3600)/60);
		return sekunde;
	}
	
	public int getMinute(){
		int minute = (int) (time / 3600);
		return minute;
	}
	
	public long getVrime(){ return time;}
	public void postaviVrime(long t) {time = t;}
	public void postaviHP(int i) {hp = i;}
	public int getHP() {return hp;}
	public void setZombi() {brojZombi++;}
	public int getBrojacZombi() { return brojZombi; }
	//HIT - kad napravimo PROTIVNIKE DODAT
	
	public void setPrestanakSetnjePucnja(){
		napadPucanjSetnja = false;
	}
	
	public void reset() {
		hp = maxHP;
		pareBrojac = 0;
		gledaDesno = true;
		trenutnaAkcija = -1;
		stop();
	}
	
	public void dohvatiSljedecuPoziciju(){
	
		double maxBrzina = this.maxBrzinaKretnje;
		
		//kretnje
		if(left){
			dx -= brzinaKretnje;
			if(dx < -maxBrzina){
				dx = -maxBrzina;
			}
		}
		else if(right){
			dx += brzinaKretnje;
			if(dx > maxBrzina){
				dx = maxBrzina;
			}
		}
		else{
			if(dx > 0){
				dx -= brzinaUsporavanja;
				if(dx < 0){
					dx = 0;
				}
			}
			else if(dx < 0){
				dx += brzinaUsporavanja;
				if(dx > 0){
					dx = 0;
				}
			}	
		}
		
		//ne moze se kretat dok napad kundakom
		
		
		//skakanje
		if(skace && !pada){
			dy = pocetakSkakanja;
			pada = true;
			//zvukovi
			Skatulica.play("skok");
		}
		
		if(dupliSkok){
			dy = dupliSkokPocetak;
			vecDupliSkok = true;
			dupliSkok = false;
			//zvuk
			Skatulica.play("skok");
		}
		
		if(!pada) vecDupliSkok = false;
		
		if(pada){
			dy += brzinaPadanja;
			if(dy < 0 && !skace) dy += krajSkakanja;
			if(dy > maxBrzinaPadanja) dy = maxBrzinaPadanja;
		}
		
		
		
	}
	
	public void postaviAnimaciju(int i){
		trenutnaAkcija = i;
		animacija.setFrames(sprites.get(trenutnaAkcija));
		animacija.setOdgoda(SPRITEODGODE[trenutnaAkcija]);
		sirina = FRAMESIRINE[trenutnaAkcija];
		visina = FRAMEVISINA[trenutnaAkcija];
	}
	
	public void hit(int damage){
		if(flinching) return;
		if(slovaBrojac == 9) return;
		//zvuk
		stop();
		hp -= damage;
		if(hp < 0) hp = 0;
		flinching = true;
		flinchBrojac = 0;
		if(gledaDesno) dx = -1;
		else dx = 1;
		dy = -3;
		pada = true;
		skace = false;
	}
	
	public void azuriranje(){
		
		time++;
		//System.out.println("X :" +x);
		//System.out.println("Y :"+y);
		//azuirarnje pozicije
		boolean isFalling = pada;
		dohvatiSljedecuPoziciju();
		provjeriTileMapKoliziju();
		setPoziciju(xtemp, ytemp);
		
		if(isFalling && !pada &&!flinching){
			Skatulica.play("doskok");
		}
		
		//doskok zvuk
		if(dx == 0) x = (int)x;
		
		
		if(flinching){
			flinchBrojac++;
			if(flinchBrojac > 120){
				flinching = false;
			}
		}
		
		//napad
				if(trenutnaAkcija == SHOOT ){
					if(animacija.pustenOdredeno(3)){
						napadPucanj = false;
					}
				}
				
				if(trenutnaAkcija == SHOOT ){
					if(animacija.pusten()){
						napadPucanj = false;
						napadPucanjSetnja = false;
					}
				}
				
				
				mana += 17;
				if(mana > 2000) mana = 2000;
				if(napadPucanj && trenutnaAkcija != SHOOT){
					if(mana > metakMana){
						mana -= metakMana;
						Metak m = new Metak(tileMapa, gledaDesno);
						m.setPoziciju(x, y);
						metci.add(m);
						Skatulica.play("pucanj");
					}
					else{
						
							Skatulica.play("uprazno");
						}
						
					}
				

		//dalje se odnosni na neprijatelje udarac metka itd. itd.
		//GRESKA GRESKA
		for(int i = 0; i < protivnici.size(); i++){
			Enemy e = protivnici.get(i);
			
			//metci
			
			for(int j = 0; j < metci.size();j++){
				if(metci.get(j).intersects(e)){
					e.udari(stetaMetka);
					metci.get(j).postaviUdarac();
					break;
				}
			}
			
			if(intersects(e)){
				hit(e.getStetu());
			}
		}
		
		for(int i = 0; i < pare.size(); i++){
			Novcic n = pare.get(i);
			if(intersects(n)){
				n.pokupljen();
				Skatulica.play("novcic");
				if(n.getVrsta() == 0){
					pareBrojac++;
				}
			}
		}
		
		if(intersects(slovo)){

			Skatulica.play("novcic");
			if(slovo.getVrsta() == 1){
				if(slovaBrojac == 9) slovaBrojac=9;
				else{
					slovaBrojac++;
				}
				slovo.pokupljen();
			}
		}
		
		//azuriranje metka
		for(int i = 0; i < metci.size();i++){
			metci.get(i).azuriranje();
			//metci.get(i).ispucan();
			if(metci.get(i).trebaUklonit()){
				if(metci.get(i).getTrenutniRedMetka() == 18 && metci.get(i).getTrenutniStupacMetka() == 21){
					setPoziciju(732, 586);
				}
				if(metci.get(i).getTrenutniRedMetka() == 18 && metci.get(i).getTrenutniStupacMetka() == 24){
					setPoziciju(620, 540);
				}
				metci.remove(i);
				i--;
			}
		}
		
		
				
		
		if(hp == 0){
			if(trenutnaAkcija != KDIE || trenutnaAkcija != LDIE){
				postaviAnimaciju(KDIE);
				postaviAnimaciju(LDIE);
			}
		}

		
		else if(dy < 0){
			if(trenutnaAkcija != JUMP){
				postaviAnimaciju(JUMP);
			}
		}
		
		else if(dy > 0){
			if(trenutnaAkcija != FALLING){
				postaviAnimaciju(FALLING);
			}
		}
		
		else if(napadPucanjSetnja){
			if(trenutnaAkcija != WALKSHOOT){
				postaviAnimaciju(WALKSHOOT);
			}
		}
		
		else if(napadPucanj){
			if(trenutnaAkcija != SHOOT){
				postaviAnimaciju(SHOOT);
			}
		}
		
		else if(left || right){
			if(trenutnaAkcija != WALK){
					postaviAnimaciju(WALK);
			}
		}	
		else if(trenutnaAkcija != IDLE){
			postaviAnimaciju(IDLE);
		}
		
		animacija.azuriranje();
		
		//postavljanje smjera ( gledanja)
		if(!napadPucanj){
			if(right) gledaDesno = true;
			if(left) gledaDesno = false;
		}
		
		
	} //azuriranje
	
	public void crtanje(Graphics2D g){
		
		//ccrtanje metaka
		for(int i = 0;i < metci.size();i++){
			metci.get(i).crtanje(g);
		}
		
		//flinch
		if(flinching){
			if(flinchBrojac % 10 < 5)return;
		}
		super.crtanje(g);
	}
	
	
	
}
