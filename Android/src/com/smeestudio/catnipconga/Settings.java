package com.smeestudio.catnipconga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.smeestudio.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static String fileName = ".catnipconga";
	public static int[] highscores = new int[] {100,90,80,70,60,50,40,30,20,10};
	public static int controls = 0;
	
	public static void load(FileIO files){
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(fileName)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			controls = Integer.parseInt(in.readLine());	
			for(int i = 0; i < 10; i++){
				highscores[i] = Integer.parseInt(in.readLine());
			}
		}catch (IOException e){
		} catch (NumberFormatException e){
		}finally{
			try{
				if(in != null)
					in.close();
			} catch(IOException e){
			}
		}
	}
	
	public static void save(FileIO files){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(fileName)));

			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			out.write(Integer.toString(controls));
			out.write("\n");
			for(int i = 0; i < 10; i++){
				out.write(Integer.toString(highscores[i]));
				out.write("\n");
			}
		}catch (IOException e){
		} finally{
			try{
				if(out != null)
					out.close();
			} catch (IOException e){
			}
		}
	}
	
	public static void addScore(int score){
		for(int i = 0; i < 10; i++){
			if(highscores[i] < score){
				for(int j = 9; j > i; j--){
					highscores[j] = highscores[j-1];
				}					
				highscores[i] = score;
				break;
			}
		}
	}
}
