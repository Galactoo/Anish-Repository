/*
 * To do list:
 * Sans animation (dodge, idle, shrug) - done
 * Fight bar inside battlebox - done
 * Resize the battlebox so that gaster blasters will be out of the rectangle
 * Finish MenuPanel's heal -done
 * Make items inside item button heal user. -done
 * Blue bones - done
 * Blue soul -dont
 */

package test;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class FightPanel extends JFrame implements KeyListener, Runnable{
	protected int trueturn, option, turn, playerx,playery,hp; //Player variables
	private BattleBox canvas; //JPanel to have the battle take place
	static final Set<Integer> pressedKeys = new HashSet<>(); //Hashset responsible for movement
	protected boolean lock = false; //Locks so player can't exit out of menu or mess up sequence of events
	protected boolean truelock = false;
	static JLabel fight, fightselected,act,actselected,item,itemselected,mercy,mercyselected,health, sans_idle, sans_shrug, sans_dodge, sans_serious = new JLabel(); //Image Labels
	JPanel sansCurrent = new JPanel(); 
	protected Thread battle; //Thread responsible movement in turn =2
	static protected Image soul,bone_airborne, gaster_blaster1 ,gaster_blaster2, bone,fightbar, blue_bone; // Images
	static protected JProgressBar healthbar; //JProgressBar that shows a bar of hp to user
	//protected String [] items = {"Butterscotch Pie", "Instant Noodles", "Snowman Piece", "Legandary Hero", "Legandary Hero"};
	public ArrayList <String> items = new ArrayList<String>(Arrays.asList("Butterscotch Pie", "Instant Noodles", "Snowman Piece", "Legandary Hero", "Legandary Hero")); // Creates a list of healing items 
	private JTextArea text = new JTextArea(""); //For dialogue method

	public FightPanel() {
		super("Fight Panel");
		option=1; //Default starting variables
		turn=1;
		trueturn=1;
		hp=92;
		playerx = 395;
		playery = 140;
		setSize( 1000,1000 );
		setBackground(Color.black);
		setVisible(true);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable(true);
		setFocusable(true);
		requestFocusInWindow();
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(Color.BLACK);
		addKeyListener(this);

		ImageIcon fightpic = new ImageIcon("fight.jpeg"); //Image of fight button when unselected
		Image img1 = fightpic.getImage();
		Image newimg1 = img1.getScaledInstance(125, 75, java.awt.Image.SCALE_SMOOTH ) ; 
		fightpic = new ImageIcon(newimg1);
		fight = new JLabel(fightpic);
		fight.setBounds(100, 800, 125, 75);
		add(fight);
		fight.setVisible(true);

		ImageIcon fightpic2 = new ImageIcon("fight2.jpg"); //Image of fight button when selected
		Image img2 = fightpic2.getImage();
		Image newimg2 = img2.getScaledInstance(125, 75, java.awt.Image.SCALE_SMOOTH ) ; 
		fightpic2 = new ImageIcon(newimg2);
		fightselected = new JLabel(fightpic2);
		fightselected.setBounds(100, 800, 125, 75);
		add(fightselected);
		fightselected.setVisible(false);

		ImageIcon actpic = new ImageIcon("act.png"); //Image of act button when unselected
		Image img3 = actpic.getImage();
		Image newimg3 = img3.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		actpic = new ImageIcon(newimg3);
		act = new JLabel(actpic);
		act.setBounds(300, 800, 125 , 75);
		add(act);
		act.setVisible(true);

		ImageIcon actpic2 = new ImageIcon("act2.png"); //Image of act button when selected
		Image img4 = actpic2.getImage();
		Image newimg4 = img4.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		actpic2 = new ImageIcon(newimg4);
		actselected = new JLabel(actpic2);
		actselected.setBounds(300, 800, 125 , 75);
		add(actselected);
		actselected.setVisible(false);

		ImageIcon itempic = new ImageIcon("item.png"); //Image of item button when unselected
		Image img5 = itempic.getImage();
		Image newimg5 = img5.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		itempic = new ImageIcon(newimg5);
		item = new JLabel(itempic);
		item.setBounds(500, 800, 125 , 75);
		add(item);
		item.setVisible(true);

		ImageIcon itempic2 = new ImageIcon("item2.png"); //Image of item button when selected
		Image img6 = itempic2.getImage();
		Image newimg6 = img6.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		itempic2 = new ImageIcon(newimg6);
		itemselected = new JLabel(itempic2);
		itemselected.setBounds(500, 800, 125 , 75);
		add(itemselected);
		itemselected.setVisible(false);

		ImageIcon mercypic = new ImageIcon("mercy.png"); //Image of mercy button when unselected
		Image img7 = mercypic.getImage();
		Image newimg7 = img7.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		mercypic = new ImageIcon(newimg7);
		mercy = new JLabel(mercypic);
		mercy.setBounds(700, 800, 125 , 75);
		add(mercy);
		mercy.setVisible(true);

		ImageIcon mercypic2 = new ImageIcon("mercy2.png"); //Image of mercy button when selected
		Image img8 = mercypic2.getImage();
		Image newimg8 = img8.getScaledInstance(125 , 75, java.awt.Image.SCALE_SMOOTH ) ; 
		mercypic2 = new ImageIcon(newimg8);
		mercyselected = new JLabel(mercypic2);
		mercyselected.setBounds(700, 800, 125, 75);
		add(mercyselected);
		mercyselected.setVisible(false);

		Toolkit kit = Toolkit.getDefaultToolkit(); //Images of all attacks, soul the player uses and the fightbar inside fight button.
		soul = kit.getImage("soul.jpeg");
		bone_airborne = kit.getImage("bone_throw.png");
		gaster_blaster1 = kit.getImage("gaster_blaster.png");
		gaster_blaster2= kit.getImage("gaster_blaster2.png");
		fightbar = kit.getImage("fightbar.png");
		blue_bone = kit.getImage("BlueBone.png");

		sans_dodge = new JLabel(new ImageIcon("dodge.gif")); //Creation of gifs for Sans's animation. Currently only 3 with different sizes
		sans_dodge.setBounds(0,0,555,361);
		sans_idle = new JLabel(new ImageIcon("idle.gif"));
		sans_idle.setBounds(100,125,300,300);
		sans_shrug = new JLabel(new ImageIcon("shrug.gif"));
		sans_shrug.setBounds(100,0,321,360);
		sans_serious = new JLabel(new ImageIcon("serious.gif"));
		sans_serious.setBounds(100,125,300,300);
		sansCurrent.add(sans_dodge);  //Add all three gif JLabels into a JLabel called sansCurrent which controls what is shown  with setSans method
		sansCurrent.add(sans_idle);
		sansCurrent.add(sans_shrug);
		sansCurrent.add(sans_serious);
		setSans("idle");
		sansCurrent.setVisible(true);
		sansCurrent.setOpaque(false); //So background is black like the JFrame
		sansCurrent.setBounds(200,25, 555,373);
		add(sansCurrent);

		canvas = new BattleBox(this);  //Makes BattleBox JPanel which handles the box where you dodge attacks in turn 2 or submenu options in turn 1
		canvas.setBounds(50,425,825,320);
		canvas.setOpaque(false);
		add(canvas);
		buttonManager(option);

		JLabel name = new JLabel(Main.name+"     LV 19"); //JLabel to show name
		name.setFont(new Font("Serif", Font.BOLD, 27));
		name.setForeground(Color.white);
		name.setBounds(100,750,300,50);
		name.setVisible(true);
		add(name);

		JLabel HP = new JLabel("HP "); HP.setFont(new Font("Serif", Font.PLAIN,23));
		HP.setForeground(Color.white); HP.setBounds(500,750,50,40); 
		HP.setVisible(true);
		//add(HP);

		health = new JLabel(hp +"/92"); //JLabel to show how much hp the player has in numbers
		health.setFont(new Font("Serif", Font.PLAIN,22)); 
		health.setForeground(Color.white); health.setBounds(800,750,100,40); 
		health.setVisible(true);
		add(health);

		healthbar = new JProgressBar(0,92); //JProgress bar that shows how much hp the player has in yellow and red.
		healthbar.setValue(hp);
		healthbar.setBounds(570, 750, 200,40); 
		healthbar.setUI(new BasicProgressBarUI()); //stack overflow
		healthbar.setBackground(Color.RED);
		healthbar.setForeground(Color.YELLOW);
		healthbar.setOpaque(true);
		add(healthbar);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setBounds(650,75,250,250);
		add(text);
		text.setVisible(false);

		battle = new Thread(this); battle.start(); //starts the thread for constant soul movement which will be printed in BattleBox
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	/*
	 * Pre: KeyEvent e
	 * Post: None
	 * Purpose: To control options movement in turn 1 and player movement in turn 2 with hashset
	 */
	public void keyPressed(KeyEvent e) {
		int event = e.getKeyCode();
		if(turn==1 ) { //Player turn
			if(lock==true) { //This part controls movement of soul inside submenu so for example inside item button, this changes the int that controls location of selected (also the heart)
				if (event == KeyEvent.VK_RIGHT) {
					canvas.submenuIndex+=1;
				}
				if (event == KeyEvent.VK_LEFT) {
					canvas.submenuIndex-=1;
				}
				if(event==KeyEvent.VK_DOWN) {
					canvas.submenuIndex+=2;
				}
				if(event==KeyEvent.VK_UP) {
					canvas.submenuIndex-=2;
				}
				canvas.repaint();
				if(event==KeyEvent.VK_ENTER||event == KeyEvent.VK_Z) {
					if(!truelock) { //Just so player can't exit after choosing an option.
						if (canvas.menuType.equals("Fightbar")) { //For specially the fightbar
							// Check if fightbar is in target zone (300-400 is example)
							setSans("dodge");
							canvas.fightbarTimer.stop();
							canvas.menuType="";
							truelock=true;
							Timer timer =new Timer(2180, evt -> { //Timer to change back Sans's animation back to normal after gif is done and to start turn=2
								setSans("idle");
								canvas.secondactivation = true;
								repaint();
							});
							timer.setRepeats(false);
							timer.start();
						} 
						else if(canvas.menuType.equals("Item")) { //For specially item button when user presses enter to select an item
							canvas.menuType=items.get(canvas.submenuIndex); // arraylist 
							useitem(canvas.submenuIndex); //Calls method to heal and delete the item in the arraylist
							Timer timer = new Timer(2000, evt -> {
								canvas.secondactivation = true;
								repaint();
								((Timer)evt.getSource()).stop(); ///??????

							});
							timer.setRepeats(false);
							timer.start();
							truelock=true;
							canvas.activation=true;

						}
						else if(canvas.activation) { //For other two buttons and since they are only dialogue, there is no need for anything.
							canvas.secondactivation=true;
							truelock=true;
							//does the second activation where 
						}
						else
							canvas.activation=true; //To make 1 of the 2 activations true
						canvas.repaint();
					}
				}
			}
			if(lock==false) { 
				if(event==39) { //Handles goes right in the initial menu of four buttons.
					option++; //Variable controling what button is selected becomes increased.
					if(option>4) { //Just so it doesn't go over
						option=1;
					}
					buttonManager(option); //Call method that controls what button gets replaced by their selected variant.
				}
				if(event ==37) { //Handles going left in the initial menu of four buttons
					option-=1; 
					if(option<1) {
						option=4;
					}
					buttonManager(option);//Call method that controls what button gets replaced by their selected variant.
				}
				if(event == KeyEvent.VK_ENTER || event == KeyEvent.VK_Z) { //Controls what happens if you select
					buttonManager(0); //Makes all buttons unselected
					switch(option) { //Switch case where it changes BattleBox submenu text by calling a method inside it with the button name
					case 1 : canvas.triggerMenu("Fight"); break;
					case 2 : canvas.triggerMenu("Act"); break;
					case 3 : canvas.triggerMenu("Item");break;
					case 4 : canvas.triggerMenu("Mercy");break;
					}
					lock=true; //Just so you can not change button / int option selected
				}
			}
			if(event == KeyEvent.VK_X || event == KeyEvent.VK_SHIFT) { //To pull back a menu
				if(!truelock) { //Incase you are already inside the fightbar or item ate.
					buttonManager(option);
					canvas.hideMenu(); //Hide menu so BattleBox can be ready for turn=2
					lock=false;
					canvas.activation = false;
					canvas.secondactivation = false;
				}
			}
		}
		if(turn==2) {
			pressedKeys.add(e.getKeyCode()); // Track keys for movement, allows all keys pressed to be registered
		} 
	}

	@Override
	/*
	 * Pre: KeyEvent e
	 * Post: None
	 * Purpose: To keep track if any key is released and remove from hashset if so.
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		pressedKeys.remove(e.getKeyCode()); // Track keys if unselected.
	}

	/*
	 * Pre: int n
	 * Post: None
	 * Purpose: To create a method that controls what images becomes visible or switch out with the un/selected counterparts.
	 */
	public void buttonManager(int n) {
		fight.setVisible(true); 
		fightselected.setVisible(false);
		act.setVisible(true);
		actselected.setVisible(false);
		item.setVisible(true);
		itemselected.setVisible(false);
		mercyselected.setVisible(false);
		mercy.setVisible(true);

		if(n==1) {
			fight.setVisible(false);
			fightselected.setVisible(true);
		}
		if(n==2) {
			act.setVisible(false);
			actselected.setVisible(true);
		}
		if(n==3) {
			item.setVisible(false);
			itemselected.setVisible(true);
		}
		if(n==4) {
			mercy.setVisible(false);
			mercyselected.setVisible(true);
		}
	}
	/*
	 * Pre: None
	 * Post: Image sou
	 * Purpose: To return an image that BattleBox uses
	 */
	public static Image getSoulImage() {
		return soul;
	}
	/*
	 * Pre: None
	 * Post: Image bone_airborne
	 * Purpose: To return an image that BoneAir class uses
	 */
	public static Image getBoneThrow() {
		return bone_airborne;
	}
	/*
	 * Pre: None
	 * Post: Image gaster_blaster_2
	 * Purpose: To return an image that GasterBlaster class uses
	 */
	public static Image getGasterBlaster1() {
		return gaster_blaster1;
	}
	/*
	 * Pre: None
	 * Post: Image gaster_blaster_1
	 * Purpose: To return an image that GasterBlaster class uses
	 */
	public static Image getGasterBlaster2() {
		return gaster_blaster2;
	}
	/*
	 * Pre: None
	 * Post: Image blue_bone
	 * Purpose: To return an image that BlueBone class uses
	 */
	public static Image getBlueBone() {
		return blue_bone;
	}

	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To maintain thread in turn 2. To allow user movement and repaint BattleBox's attacks
	 */
	@Override
	public void run() {
		while (true) {
			if (turn == 2) {
				if (pressedKeys.contains(KeyEvent.VK_RIGHT)) //Movement speed of 5 in all directions
					playerx += 5;
				if (pressedKeys.contains(KeyEvent.VK_LEFT)) 
					playerx -= 5;
				if (pressedKeys.contains(KeyEvent.VK_UP)) 
					playery -= 5;
				if (pressedKeys.contains(KeyEvent.VK_DOWN)) 
					playery += 5;

				if (playerx < 15) //Boundaries of soul, so the soul does not go beyond the box. 
					playerx = 16;
				if (playerx > 780) 
					playerx = 779;
				if (playery < 15) 
					playery = 16;
				if (playery > 275) 
					playery = 274;

				canvas.repaint(); //Repeatedly calls BattleBox to repaint the whole JPanel

			}
			try {
				Thread.sleep(16); //60 fps
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	/*
	 * Pre: int amount
	 * Post: None
	 * Purpose: To track hp loss and to visually show it to user. Also controls when to change screens to deathPanel if dead.
	 */
	public void takeDamage(int amount) {
		hp-=amount;
		if(hp<=0) {
			pressedKeys.clear();
			Main.switchToDeathPanel();
			//running = false; // Set flag to stop the loop
		}
		healthbar.setValue(hp);
		health.setText(hp + "/92");
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To stop thread when user died
	 */
	public void dispose() { //From AI
		super.dispose();
	}

	/*
	 * Pre: String temp
	 * Post: None
	 * Purpose: To control the sansCurrent JPanel so that the current gif/animation is playing. Sets the rest to not visible
	 */
	public void setSans(String temp) {
		sans_dodge.setVisible(false); //Sets everything to not visible
		sans_shrug.setVisible(false);
		sans_idle.setVisible(false);
		sans_serious.setVisible(false);
		if(temp.equals("dodge"))  //If statements to control what should be visible. Similar to buttonManager Layout
			sans_dodge.setVisible(true);
		if(temp.equals("idle")) 
			sans_idle.setVisible(true);
		if(temp.equals("shrug")) 
			sans_shrug.setVisible(true);
		if(temp.equals("serious"))
			sans_serious.setVisible(true);
	}
	/*
	 * Pre: int itemname
	 * Post: None
	 * Purpose: To delete the item in the item arraylist when used in BattleBox and to heal player
	 */
	public void useitem (int itemname) {
		String name = items.get(itemname); //Gets name of item
		//System.out.println(itemname+ "  " +name); //Troubleshooting
		if(name.equals("Butterscotch Pie")) { //If statements of what item was used
			hp = 92;
		}
		else if(name.equals("Instant Noodles")) {
			hp = hp+90;
		}
		else if(name.equals("Snowman Piece")) {
			hp = hp+45;
		}
		else if(name.equals("Legandary Hero")) {
			hp = hp+40;
		}
		if(hp>92) { //Checks if hp is above 92 and if so sets it to 92
			hp=92;
		}
		healthbar.setValue(hp); //Visually force update of the health indications to player
		health.setText(hp + "/92");
		items.remove(itemname); //Remove item from arraylist
	}
	/*
	 * Pre: String dialogue
	 * Post: NOne
	 * Purpose: To make dialogue that inside the queue it calls so the enemy can talk in turns.
	 */
	int index =0;
	public void dialogue (String dialogue) {
		text.setVisible(true);
		text.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
		Timer textTimer = new Timer(100, e -> {
			if(index < dialogue.length()) {
				text.setText(text.getText() + dialogue.charAt(index));
				index++;
			}
			else {
				((Timer)e.getSource()).stop();
				Timer ihateTimer = new Timer(1000, r -> {
					text.setVisible(false);
					text.setText("");
				});
				ihateTimer.setRepeats(false);
				ihateTimer.start();
				index=0;
			}
		});
		textTimer.start();
	}
}