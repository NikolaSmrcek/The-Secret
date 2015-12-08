package Handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




public class Highscore {
	
	public static String [][] procitano = procitajHighScore();
	private static int promjeni;
	
	public static String[][] procitajHighScore(){
		String [][] a;
		a = new String[5][4];
		try{
			
			FileReader file = new FileReader("hg.txt");
			BufferedReader reader = new BufferedReader(file);
			
			
			int i = -1;
			String line = reader.readLine();
			
			while(line != null){
				i++;
				a[i][0] = line;
				line = reader.readLine();
				a[i][1] = line;
				line = reader.readLine();
				a[i][2] = line;
				line = reader.readLine();
				a[i][3] = line;
				line = reader.readLine();
			}
			
			reader.close();
			file.close();
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return a;
	}
	
	
	public static boolean usporediHighScore(String vrime){
		sort();
		String [] vr = vrime.split(":"); //trenutno
		for(int i = 0;i < procitano.length;i++){
			String [] vri = procitano[i][3].split(":"); //procitano iz fajla
			if(Integer.parseInt(vr[0]) < Integer.parseInt(vri[0])){
				promjeni = i;
				return true;
			}
			else if(Integer.parseInt(vr[0]) == Integer.parseInt(vri[0])){
				if(Integer.parseInt(vr[1]) < Integer.parseInt(vri[1])){
					promjeni = i;
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void sort(){
		
		int j;
		boolean flag = true;
		String[] temp;
		
		while(flag){
			
			flag = false;
			for(j = 0;j < procitano.length - 1;j++){
				String [] vr = procitano[j][3].split(":");
				String [] vri = procitano[j+1][3].split(":");
				if(Integer.parseInt(vr[0]) > Integer.parseInt(vri[0])){
					
					temp = procitano[j];
					procitano[j] = procitano[j+1];
					procitano[j+1] = temp;
					flag = true;
					
				}//ako je veci
				else if(Integer.parseInt(vr[0]) == Integer.parseInt(vri[0])){
					if(Integer.parseInt(vr[1]) > Integer.parseInt(vri[1])){
						temp = procitano[j];
						procitano[j] = procitano[j+1];
						procitano[j+1] = temp;
						flag = true;
					}
				}
				
			}//prvi for
			
		}//while
		
	}
	
	public static void ispis(){
		sort();
		for(int i = 0; i < procitano.length;i++){
			System.out.println(procitano[i][0]);
			System.out.println(procitano[i][1]);
			System.out.println(procitano[i][2]);
			System.out.println(procitano[i][3]);
		}
		
		
	} //ispis
	
	public static void zapisi(int nov,String ime,int zombi,String vrime){
		procitano = procitajHighScore();
		
		try{
			
			
			FileWriter fw = new FileWriter("hg.txt");
			PrintWriter pw = new PrintWriter(fw);
			int j = 0;
			for(int i = 0; i < procitano.length;i++){
				if(i == promjeni){
					j = -1;
					pw.println(nov);
					pw.println(ime);
					pw.println(zombi);
					pw.println(vrime);
					
				}
				else{
						pw.println(procitano[i+j][0]);
						pw.println(procitano[i+j][1]);
						pw.println(procitano[i+j][2]);
						pw.println(procitano[i+j][3]);
					
					
				}
				
			}
			pw.close();
			fw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
