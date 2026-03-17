package test;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;

public class BattleBox extends JPanel{
	private java.util.List<Attacks> attacks = new ArrayList<>();
	private boolean showMenu = false; //If true then shows menuType's submenu options
	String menuType = ""; //"Fight", "Act", "Item" or "Mercy.
	int submenuIndex = 0; // Controls what option is the user selecting in submenu
	boolean activation, secondactivation = false; //Activation controls if submenu is selected while secondactivation is when enter is pressed when item is selected, going to next turn.
	private boolean attacksSpawned = false; // To control attacks being spawned
	Timer  fightbarTimer; //To control fightbarx's value when fightbar is selected in menuType
	int fightbarx=0; //Controls the rectangle's x value in fightbar
	FightPanel fightpanel; //Gets instance class of fightpanel to get variable information
	public BattleBox(FightPanel fightPanel) {
		fightpanel = fightPanel;
		fightbarTimer = new Timer(16, e -> { // ~60 FPS
			fightbarx += 5;
			if (fightbarx > getWidth() - 40 - 25) {  // Reset if reached end of menu
				fightbarx = 0; // Reset to start
			}
			repaint();
		});
	}
	/*
	 * Pre: String type
	 * Post: None
	 * Purpose: To 
	 */
	public void triggerMenu(String type) {
		showMenu = true; //To show submenu options of type
		menuType = type; //Sets menuType of type so it can keep track in paintcomponent
		submenuIndex=0; //Keeps track of where the user is selecting in submenu
		activation=false;
		secondactivation=false; //Activations false since submenu options are opened, not interacted yet
		repaint(); 
	}
	
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To be called so it can hide the submenu and return to the 4 buttons
	 */
	public void hideMenu() {
		showMenu = false;
		repaint();
	}
	/*
	 * Pre: Graphics g
	 * Post: None
	 * Purpose: To paint EVERYTHING in the BattleBox in turn 1 and 2.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); 
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.black);
		g2.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
		g2.setColor(Color.white);
		g2.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
		if(showMenu) { //Opens submenu options if button is pressed in FightPanel
			g2.setFont(new Font("Comic Sans MS", Font.PLAIN,25));
			g2.setColor(Color.white);
			if(submenuIndex<0) {
				submenuIndex = 0; //So user can't go negative submenu options and stay at top left instead.
			}
			if(menuType.equals("Fight")) { //When button fight is selected
				g2.drawString("*   Sans", 50, 60);
				g2.drawImage(FightPanel.getSoulImage(),40,32,30,30,this); //Hard - coded so soul is stuck with the 1 option
			}
			if(menuType.equals("Act")) { //When act button is selected
				g2.drawString("*    Check", 50, 60);
				g2.drawString("*    Taunt", 400, 60);
				g2.drawString("*    Option 3", 50, 160);
				g2.drawString("*    Option 4", 400, 160); //All four options
				if(submenuIndex>3) {
					submenuIndex=3; //Limit so player can't go above the 4 submenu options
				}
				//For draw soul on option when user is over a option.
				if(submenuIndex==0) 
					g2.drawImage(FightPanel.getSoulImage(),40,32,30,30,this);
				if(submenuIndex==1)
					g2.drawImage(FightPanel.getSoulImage(),390,32,30,30,this);
				if(submenuIndex==2) 
					g2.drawImage(FightPanel.getSoulImage(),40,132,30,30,this);
				if(submenuIndex==3)
					g2.drawImage(FightPanel.getSoulImage(),390,132,30,30,this);
			}
			if(menuType.equals("Item")) { //When user presses on item button
				for(int loop=0;loop<fightpanel.items.size(); loop++) { //Loop to print out the arraylist's contents in two rows
					if(loop%2==0) {
						g2.drawString("*    "+fightpanel.items.get(loop), 50, 50+100*(loop/2));
					}
					else {
						g2.drawString("*    "+fightpanel.items.get(loop), 400, 50+100*((loop-1)/2));
					}
				}
				if(submenuIndex>fightpanel.items.size()-1) {
					submenuIndex=fightpanel.items.size()-1; //Sets submenuIndex to how much items there are so user can't go above
				}
				//Draws all soul locations when hovering over option.
				if(submenuIndex==0) 
					g2.drawImage(FightPanel.getSoulImage(),40,32,30,30,this);
				if(submenuIndex==1)
					g2.drawImage(FightPanel.getSoulImage(),390,32,30,30,this);
				if(submenuIndex==2) 
					g2.drawImage(FightPanel.getSoulImage(),40,132,30,30,this);
				if(submenuIndex==3)
					g2.drawImage(FightPanel.getSoulImage(),390,132,30,30,this);
				if(submenuIndex==4) 
					g2.drawImage(FightPanel.getSoulImage(),40,232,30,30,this);
			}
			if(menuType.equals("Mercy")) { //For when button mercy is pressed
				g2.drawString("*    Spare", 50, 60); 
				g2.drawString("*    Flee", 400, 60);
				if(submenuIndex>1) {
					submenuIndex=1; //Upper limits
				}
				//All soul locations for submenu options
				if(submenuIndex==0) 
					g2.drawImage(FightPanel.getSoulImage(),40,32,30,30,this);
				if(submenuIndex==1)
					g2.drawImage(FightPanel.getSoulImage(),390,32,30,30,this);
			}
			if(menuType.equals("Fightbar")) { //When user presses fight - > sans to show fightbar
				g2.drawImage(FightPanel.fightbar,20,15,getWidth()-40,getHeight()-40,this); //Draw the fightbar
				g2.drawRect(fightbarx,23,25,getHeight()-50); //Rectangle controling where you attack (Middle = more damage but sans dodges regardless)
				//fightbarx+=20;
				if (!fightbarTimer.isRunning()) { //Checks if fightbartimer is running to start
					fightbarTimer.start();
				}
			}
			if(menuType.equals("Check")) { //Shows dialogue of sans when you press act -> check
				g2.drawString("*   Sans    1 ATK     1 DEF.", 50, 60);
				g2.drawString("*   The easiest enemy", 50, 110);
				g2.drawString("*   Can only deal 1 damage.", 50, 160);
			}
			if(menuType.equals("Taunt")) { //Shows dialogue of sans when you press act -> taunt
				g2.drawString("*   You taunted Sans.", 50, 60);
				g2.drawString("*   He got gave you a deadpanned face", 50, 110);
				g2.drawString("*   Nothing changed bruh", 50, 160);
			}
			//Shows all dialogue when you ate each different items
			if(menuType.equals("Butterscotch Pie")) {
				g2.drawString("*   You ate the Butterscotch Pie", 50, 60);
				g2.drawString("*   You recovered 92 HP", 50, 110);
			}
			if(menuType.equals("Instant Noodles")) {
				g2.drawString("*   You ate the Instant Noodles", 50, 60);
				g2.drawString("*   You recovered 90 HP", 50, 110);
			}
			if(menuType.equals("Snowman Piece")) {
				g2.drawString("*   You ate the Snowman Piece", 50, 60);
				g2.drawString("*   You recovered 45 HP", 50, 110);
			}
			if(menuType.equals("Legandary Hero")) {
				g2.drawString("*   You ate the Legandary Hero", 50, 60);
				g2.drawString("*   You recovered 40 HP", 50, 110);
			}

			if (activation && fightpanel.turn == 1) {
				if(secondactivation==false) {
					if(menuType.equals("Fight")) {
						menuType="Fightbar"; //Switches type to fightbar
						fightbarTimer.start(); //starts fightbar timer when fightbar is selected
					}
					if(menuType.equals("Act")) {
						if(submenuIndex==0) 
							menuType ="Check";
						if(submenuIndex==1)
							menuType="Taunt";
					}
				}
				else {
					showMenu = false; //Hides submenu
					fightpanel.turn = 2;
					activation = false;
					repaint();
					return; //Starts turn 2 (or dodge attacks phase)
				}
			}
			repaint();
		}
		if (fightpanel.turn == 2) { //When dodge attacks phase
			Graphics2D comp2D = (Graphics2D) g;
			// Draw player soul
			comp2D.drawImage(FightPanel.getSoulImage(), fightpanel.playerx, fightpanel.playery, 30, 30, this); 
			if (!attacksSpawned) { 
				Queue<Waves> waves = new LinkedList<>(); //Makes queue of the turns attacks to be shown
				if(fightpanel.trueturn==1) { //List of what is going to happen in trueturn 1
					waves.add(new Waves (0, () ->fightpanel.dialogue("It's a very beautiful day outside")));
					waves.add(new Waves(1000,() -> spawnGBAttack(30,50,270,"large",1000,2000)));
					waves.add(new Waves(0,() -> spawnGBAttack(420,10,0,"",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(280,10,0,"",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(350,280,180,"",2000,1000)));

					waves.add(new Waves(3500, () -> {}));
					attacks.clear();
					for(int loop=0;loop<160;loop=loop+20) {
						final int temp = loop;
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, (140+temp), -7,0, 30, 15))));
					}
					waves.add(new Waves (0, () ->fightpanel.dialogue("Birds are singing, flowers are blooming")));
					waves.add(new Waves(1500,() -> spawnGBAttack(30,50,270,"large",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(670,100,90,"large",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(420,10,0,"",1000,2000)));
					waves.add(new Waves(0,() -> spawnGBAttack(280,10,0,"",1000,2000)));
					waves.add(new Waves(0,() -> spawnGBAttack(350,280,180,"",1000,2000)));
					waves.add(new Waves(3500, () -> {}));
					
					waves.add(new Waves (1000, () ->fightpanel.dialogue("on days like these, kids like you")));
					for(int loop1=0;loop1<4;loop1++) {
						final int temp1 = loop1;
						for(int loop=0;loop<160;loop=loop+20) {
							final int temp = loop;
							waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, (temp), -4-temp1,0,30, 15))));
							waves.add(new Waves(0, () -> attacks.add(new BoneAir(0, (140+temp), 4+temp1,0,30, 15))));
						}
						waves.add(new Waves(1500, () -> {}));
					}
					waves.add(new Waves(0,() -> spawnGBAttack(10,20,270,"large",1000,2000)));
					waves.add(new Waves(0,() -> spawnGBAttack(670,200,90,"large",1000,2000)));
					waves.add(new Waves(0,() -> spawnGBAttack(420,10,0,"",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(280,10,0,"",2000,1000)));
					waves.add(new Waves(0,() -> spawnGBAttack(350,280,180,"",2000,1000)));
					waves.add(new Waves (0, () ->fightpanel.dialogue("should be burning in hell")));
					waves.add(new Waves (0, () ->fightpanel.setSans("serious")));

				}
				if(fightpanel.trueturn==2) { //List of actions during trueturn =2
					fightpanel.setSans("shrug");
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(100, 200, 2,0, 30, 15))));
					waves.add(new Waves(100, () -> attacks.add(new BoneAir(100, 170, 5,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(100, 140, 5,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(100, 110, 5,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(100, 80, 5,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(100, 50, 5,0, 30, 15))));

					waves.add(new Waves(3000, () -> attacks.add(new BoneAir(100, 50,5,0, 30, 15))));
					waves.add(new Waves(1000, () -> attacks.add(new BoneAir(800, 200, -6,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 170, -4,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 140, -5,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 110, -8,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 80, -6,0, 30, 15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 50, -9,0, 30, 15))));
					waves.add(new Waves(2000, () -> attacks.add(new BoneAir(100, 150,5,0, 30, 15))));

					waves.add(new Waves(1000,() -> spawnGBAttack(50,50,270,"",1000,500)));

					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(50, 150, 2,Math.toRadians(270),"small");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(650, 150, 2,Math.toRadians(130),"large");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));

				}
				else if(fightpanel.trueturn==3) {
					for(int l=0; l<5;l++) {
						waves.add(new Waves(2000, () -> {GasterBlaster gb =new GasterBlaster(25, fightpanel.playery-30, 2,Math.toRadians(270),"");
						attacks.add(gb);
						new Timer(500, e -> gb.startFiring()).start();
						}));
					}
					waves.add(new Waves(750, () -> attacks.add(new BoneAir(0, 0,0,5,400,15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(400, getHeight(),1,0,-5,400,15))));


					waves.add(new Waves(2500, () -> {GasterBlaster gb =new GasterBlaster(700, 150, 2,Math.toRadians(90),"large");
					attacks.add(gb);
					new Timer(2000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(50, 50, 2,Math.toRadians(270),"large");
					attacks.add(gb);
					new Timer(2000, e -> gb.startFiring()).start();
					}));

					waves.add(new Waves(1000, () -> {GasterBlaster gb =new GasterBlaster(650, 25, 2,Math.toRadians(0),"");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(550, 25, 2,Math.toRadians(0),"");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(450, 25, 2,Math.toRadians(0),"");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(0, () -> {GasterBlaster gb =new GasterBlaster(350, 25, 2,Math.toRadians(0),"");
					attacks.add(gb);
					new Timer(1000, e -> gb.startFiring()).start();
					}));
					waves.add(new Waves(1000, () -> {}));

				}
				else if(fightpanel.trueturn==4) {
					for(int loop=0;loop<3;loop++) {
						fightpanel.setSans("serious");
						waves.add(new Waves(1600,() -> spawnGBAttack(650, fightpanel.playery-30,90,"",1000,1000)));
						waves.add(new Waves(0,() -> spawnGBAttack(fightpanel.playerx-30, 0, 0,"",1000,1000)));
						for(int loop1=0;loop1<10;loop1++) {
							waves.add(new Waves(0, () -> attacks.add(new BoneAir(0, (int)(Math.random()*(getHeight()-10+1)+10),5,0, 30, 15))));
						}
						waves.add(new Waves(1900,() -> spawnGBAttack(20, fightpanel.playery-30, 270,"",1000,1000)));
						waves.add(new Waves(0,() -> spawnGBAttack(fightpanel.playerx-30, 270, 180,"",1000,1000)));
						for(int loop1=0;loop1<5;loop1++) {
							waves.add(new Waves(25, () -> attacks.add(new BoneAir(0, (int)(Math.random()*(getHeight()-10+1)+10),5,0, 30, 15))));
						}
					}
					waves.add(new Waves(1500, () -> {}));

					for(int loop=0;loop<10;loop++) {
						waves.add(new Waves(2000, () -> attacks.add(new BoneAir(-300, 85,5,0,400,20))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 190,-5,0,400,20))));
						int random = (int)(Math.random()*(3-1+1)+1);
						if(random==1) 
							waves.add(new Waves(0,() -> spawnGBAttack(0, 0,270,"",1200,1800)));
						else if(random==2)
							waves.add(new Waves(0,() -> spawnGBAttack(0, 70,270,"large",1200,1800)));
						else
							waves.add(new Waves(0,() -> spawnGBAttack(0, 190,270,"large",1200,1800)));
					}
				}
				else if(fightpanel.trueturn==5) {
					for(int loop=0;loop<20;loop++) {
						waves.add(new Waves(1000, () -> attacks.add(new BoneAir(-300, 85,5,0,200,20))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(800, 190,-5,0,200,20))));
						int random = (int)(Math.random()*(3-1+1)+1);
						if(random==1) 
							waves.add(new Waves(0,() -> spawnGBAttack(0, 0,270,"",1200,900)));
						else if(random==2)
							waves.add(new Waves(0,() -> spawnGBAttack(0, 70,270,"large",1200,900)));
						else
							waves.add(new Waves(0,() -> spawnGBAttack(0, 190,270,"large",1200,900)));
					}
					waves.add(new Waves(2500, () -> attacks.add(new BoneAir(0, 0,0,4,400,15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(400, getHeight(),0,-4,400,15))));

					waves.add(new Waves(1000, () -> {}));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(400, 0,0,4,400,15))));
					waves.add(new Waves(0, () -> attacks.add(new BoneAir(0, getHeight(),0,-4,400,15))));

				}
				else if(fightpanel.trueturn==6) {
					for(int loop=0;loop<5;loop++) {
						waves.add(new Waves(1500,() -> spawnGBAttack(0, 100,(Math.random()*(360-180+1)+180),"large",1000,1000)));
						waves.add(new Waves(0,() -> spawnGBAttack(650, 100,(Math.random()*(180+1)),"large",1000,1000)));
					}
					waves.add(new Waves(3000, () -> {}));

					for(int loop=0;loop<10;loop++) {
						waves.add(new Waves(0, () -> attacks.add(new BlueBone(0,70,5,0,150,30,90))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(0,220,5,0,150,30,90))));
						waves.add(new Waves(750, () -> attacks.add(new BlueBone(0,220,5,0,150,30,90))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(0,70,5,0,150,30,90))));
						waves.add(new Waves(750, () -> {}));
						if(loop>5) {
							waves.add(new Waves(0,() -> spawnGBAttack(0, 100,(Math.random()*(330-210+1)+180),"large",1000,1000)));
						}
					}
				}
				else if(fightpanel.trueturn==7) {
					for(int loop=0;loop<21;loop++) {
						final int modify = loop*10;
						waves.add(new Waves(100, () -> attacks.add(new BoneAir(0,getHeight()-30-modify/2,8,0,20+modify,30,90))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(0,90-modify/2,8,0,230-modify,30,90))));
					}
					for(int loop=20;loop>0;loop--) {
						final int modify = loop*10;
						waves.add(new Waves(100, () -> attacks.add(new BoneAir(0,getHeight()-30-modify/2,8,0,20+modify,30,90))));
						waves.add(new Waves(0, () -> attacks.add(new BoneAir(0,90-modify/2,8,0,230-modify,30,90))));
					}
					//waves.add(new Waves(0, () -> attacks.add(new BoneAir(getWidth()/2,25,0,0,getHeight()-50,30))));
					//waves.add(new Waves(0, () -> attacks.add(new BoneAir(25,getHeight()/3,0,0,30,30,90))));
					//waves.add(new Waves(0, () -> attacks.add(new BoneAir(25,(2*getHeight())/3,0,0,30,30,90))));
					for(int loop=0;loop<11;loop++) {
						waves.add(new Waves(350, () -> attacks.add(new BoneAir(0,(int)(Math.random()*((getHeight()-30)-15+1)+15),5,0,30,15))));
						waves.add(new Waves(50, () -> attacks.add(new BoneAir(825,(int)(Math.random()*((getHeight()-30)-15+1)+15),-5,0,30,15))));
						waves.add(new Waves(50, () -> attacks.add(new BoneAir(0,(int)(Math.random()*((getHeight()-30)-15+1)+15),7,0,30,15))));
						waves.add(new Waves(50, () -> attacks.add(new BoneAir(825,(int)(Math.random()*((getHeight()-30)-15+1)+15),-7,0,30,15))));
						waves.add(new Waves(50, () -> attacks.add(new BlueBone(0,(int)(Math.random()*((getHeight()-30)-15+1)+15),5,0,30,15,0))));
						waves.add(new Waves(50, () -> attacks.add(new BlueBone(825,(int)(Math.random()*((getHeight()-30)-15+1)+15),-5,0,30,15,0))));
					}
				}
				else if(fightpanel.trueturn==8) {
					fightpanel.setSans("shrug");
					//waves.add(new Waves(0, () -> fightpanel.setSans("idle")));
					for(int loop=0;loop<11;loop++) {
						waves.add(new Waves(800, () -> attacks.add(new BoneAir(-220,75,3,0,300,40,30))));
						waves.add(new Waves(800, () -> attacks.add(new BoneAir(820,250,-3,0,300,40,210))));
					}
				}
				else if(fightpanel.trueturn==9) { 
					for(int loop=0;loop<21;loop++) {
						final int temp = loop*30;
						waves.add(new Waves (750-loop, () -> attacks.add(new BoneAir(100+temp,50,2,4,30,15,60))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(100+temp,50,-2,4,30,15,120))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(100+temp,50,-4,0,30,15,180))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(100+temp,50,-2,-4,30,15,240))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(100+temp,50,2,-4,30,15,300))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(100+temp,50,4,0,30,15,0))));
						
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,2,4,30,15,60))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,-2,4,30,15,120))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,-4,0,30,15,180))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,-2,-4,30,15,240))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,2,-4,30,15,300))));
						waves.add(new Waves (0, () -> attacks.add(new BoneAir(700-temp,250,4,0,30,15,0))));
						if(loop%6==0) {
							waves.add(new Waves(0, () -> attacks.add(new BlueBone(-150,getHeight()/2-30,3,0,getHeight()-15,40,90))));
						}
						else if(loop%3==0) {
							waves.add(new Waves(0, () -> attacks.add(new BlueBone(800,getHeight()/2-30,-3,0,getHeight()-15,40,90))));
						}
					}
				}
				else if(fightpanel.trueturn==10) {
					waves.add(new Waves (0, () ->fightpanel.dialogue("err err errr errr")));
					waves.add(new Waves (0, () ->fightpanel.setSans("serious")));
					waves.add(new Waves(0,() -> spawnGBAttack(0, 100,270,"large",1200,900)));
					waves.add(new Waves (1000, () ->fightpanel.setSans("shrug")));
					

				}
				waves.add(new Waves(3000, () -> {})); //For all attacks to run it's course before being stopped
				startqueue(waves); //starts the queue
			}

			// Update and draw all active attacks
			Rectangle hitbox = new Rectangle(fightpanel.playerx, fightpanel.playery, 30, 30); //Makes player hitbox
			for (Attacks gb : attacks) { //Checks if any attacks are touching the player's hitbox to do damage
				gb.update();
				gb.draw(comp2D);
				if (gb.checkCollision(hitbox)) {
					// Player takes damage
					fightpanel.takeDamage(gb.damage);
				}
			}
			attacks.removeIf(a -> !a.isActive()); // Remove finished attacks
		}
	}
	//Default duration
	public void spawnGBAttack(int x, int y, double angle, String size, int time) {
		spawnGBAttack(x,y,angle,size,time,500);
	}
	public void spawnGBAttack(int x, int y, double angle, String size, int time, int duration) {
		GasterBlaster gb =new GasterBlaster(x, y, 2,Math.toRadians(angle),size,duration);
		attacks.add(gb);
		Timer t = new Timer(time, e -> {
			gb.startFiring();
			((Timer) e.getSource()).stop();  // stop after the first call
		});
		t.setRepeats(false); //Just so blaster doesn't fire again and to start it
		t.start();
	}

	private void startqueue(Queue<Waves> waveQueue) {
		attacksSpawned = true;

		new SwingWorker<Void, Void>() { //Uses swingworker to do background task (all the attacks and action)
			@Override
			protected Void doInBackground() throws Exception { //In built method to do the calculations in background
				while (!waveQueue.isEmpty()) { 
					Waves wave = waveQueue.poll();  //Gets the current action/attack of the wave and loads it before removing it
					Thread.sleep(wave.delayMillis);  
					SwingUtilities.invokeLater(wave.action); // updates UI
				}
				return null;
			}
			@Override
			protected void done() { //Reset all variables in fightpanel and battlebox back to normal and increase trueturn to next
				attacks.clear();
				attacksSpawned = false;
				fightpanel.turn = 1;
				repaint();
				fightpanel.trueturn++;
				fightpanel.buttonManager(fightpanel.option);
				fightpanel.lock=false;
				fightpanel.playerx = 395;
				fightpanel.playery=140;
				fightbarTimer.stop();
				fightpanel.truelock=false;
				fightbarx = 0; // Reset the rectangle on fightbar to starting position
				fightbarTimer.stop();
				fightpanel.setSans("idle");
				menuType="";
				secondactivation=false;
				activation=false;
			}
		}.execute();
	}
}