package Handlers;

import java.awt.event.KeyEvent;

public class Keys {
	
	public static final int BROJ_KLJUCEVA = 16;
	
	public static boolean stanjeKljuca[] = new boolean[BROJ_KLJUCEVA];
	public static boolean prethodnoStanjeKljuca[] = new boolean[BROJ_KLJUCEVA];
	
	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int BUTTON1 = 4;
	public static int BUTTON2 = 5;
	public static int BUTTON3 = 6;
	public static int BUTTON4 = 7;
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	
	public static void postaviKljuc(int i, boolean b) {
		if(i == KeyEvent.VK_UP) stanjeKljuca[UP] = b;
		else if(i == KeyEvent.VK_LEFT) stanjeKljuca[LEFT] = b;
		else if(i == KeyEvent.VK_DOWN) stanjeKljuca[DOWN] = b;
		else if(i == KeyEvent.VK_RIGHT) stanjeKljuca[RIGHT] = b;
		else if(i == KeyEvent.VK_SPACE) stanjeKljuca[BUTTON1] = b;
		else if(i == KeyEvent.VK_C) stanjeKljuca[BUTTON2] = b;
		else if(i == KeyEvent.VK_E) stanjeKljuca[BUTTON3] = b;
		else if(i == KeyEvent.VK_F) stanjeKljuca[BUTTON4] = b;
		else if(i == KeyEvent.VK_ENTER) stanjeKljuca[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) stanjeKljuca[ESCAPE] = b;
	}
	
	public static void azuriraj() {
		for(int i = 0; i < BROJ_KLJUCEVA; i++) {
			prethodnoStanjeKljuca[i] = stanjeKljuca[i];
		}
	}
	
	public static boolean pritisnut(int i) {
		return stanjeKljuca[i] && !prethodnoStanjeKljuca[i];
	} //odredni
	
	public static boolean kljucPritisnut() {
		for(int i = 0; i < BROJ_KLJUCEVA; i++) {
			if(stanjeKljuca[i]) return true;
		}
		return false;
	} //ako je bilo koji pritisnut
	
}
