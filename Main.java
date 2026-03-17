/*
 * Anish Viththiyatharan
 * ICS4U
 * June 11, 2025
 * Purpose: To make an undertale style game for CPT project
 */
package test;

import javax.sound.sampled.*;
import java.io.*;

public class Main {
	static FightPanel fightPanel;
	static MenuPanel menuPanel;
	static String name = "";
	static int value=0;
	public static void main(String[] args) {
		/*if(value==0) {
			new MenuPanel();
		}
		if(value==1) {
			//if(MenuPanel()==!null) {

			//}
			fightPanel=new FightPanel();
		}
		if(value==2) {
			fightPanel.dispose();
			deathPanel = new DeathPanel();
		}*/
		menuPanel = new MenuPanel();
		try {
			AudioInputStream ais= AudioSystem.getAudioInputStream(new File("rev.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.setMicrosecondPosition(30000000);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To change to deathpanel
	 */
	public static void switchToDeathPanel() {
		if(fightPanel!=null) { //Checks if thread of fightpanel is ongoing and if so to dipose/stop it
			fightPanel.dispose();
		}
		new DeathPanel();
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To change to fightpanel
	 */
	public static void switchToFightPanel() {
		if(menuPanel!=null) { //Checks if thread of menupanel is ongoing and if so to dipose/stop it
			menuPanel.dispose();
		}
		fightPanel = new FightPanel();
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To change to menupanel
	 */
	public static void switchToMenuPanel() {
		menuPanel = new MenuPanel(); 
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To change to instructionpanel
	 */
	public static void switchToInstructionPanel() {
		menuPanel.dispose();
		new InstructionPanel();
	}
}
