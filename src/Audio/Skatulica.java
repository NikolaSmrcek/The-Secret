package Audio;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Skatulica {
	
	private static HashMap<String, Clip> klipovi;
	private static int gap;
	private static boolean mute = false;
	
	public static void init(){
		
		klipovi = new HashMap<String, Clip>();
		gap = 0;
	}
	
	public static void ucitaj(String s,String n){
		
		if(klipovi.get(n) != null) return;
		Clip klip;
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(Skatulica.class.getResourceAsStream(s));
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels()*2,
					baseFormat.getSampleRate(),
					false); //dekodiramo od pocetnog formata koji je ucitan preko stringa s (prvog argumenta)
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
			klip = AudioSystem.getClip();
			klip.open(dais);
			klipovi.put(n,klip); //dohvaæeni klip stavljamo u Kljuc-vrijednost (rjeènik) mapu
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}//uèitavanje zvuka
	
	
	public static void play(String s){
		play(s,gap);
	}
	
	public static void play(String s,int i){
		if(mute) return; //ako je mtue-bezzvuka necemo pustat zvuk
		Clip c = klipovi.get(s);
		if(c == null)return;
		if(c.isRunning())c.stop();
		c.setFramePosition(i);
		//FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		//gainControl.setValue(-25.0f);  -- kontrole zvuka, da se izvest funkcija od toga
		while(!c.isRunning()) c.start();
		
	}
	
	public static void stop(String s){
		if(klipovi.get(s) == null)return;
		if(klipovi.get(s).isRunning()) klipovi.get(s).stop();
	} //zaustavljamo pustanje odredenog klipa
	
	public static void nastavi(String s){
		if(mute) return;
		if(klipovi.get(s).isRunning())return;
		klipovi.get(s).start();
	} //ako smozaustavili da mozemo nastaviti pustat glazbu
	
	public static void loop(String s){
		loop(s,gap,gap,klipovi.get(s).getFrameLength()-1);
	}
	
	public static void loop(String s, int frame) {
		loop(s, frame, gap, klipovi.get(s).getFrameLength() - 1);
	}
	
	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}
	public static void loop(String s,int frame,int start,int end){
		stop(s);
		if(mute) return;
		klipovi.get(s).setLoopPoints(start, end);
		klipovi.get(s).setFramePosition(frame);
		klipovi.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}
	public static void postaviPoziciju(String s, int frame){
		klipovi.get(s).setFramePosition(frame);
	}
	
	public static int getFrames(String s){return klipovi.get(s).getFrameLength();}
	public static int getPosition(String s){return klipovi.get(s).getFramePosition();}
	
	public static void zatvori(String s){
		stop(s);
		klipovi.get(s).close();
		klipovi.remove(s);
	}
}
