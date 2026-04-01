package test;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class MenuPanel extends JFrame implements KeyListener, Runnable, ActionListener {
	static Image soul;
	int playerx =400;
	int playery=300; //player coordinates
	JLabel nameShow;
	String name = ""; //name that is used to keep track of what user presses and to send to main's name string
	protected Namer canvas; //JPanel that handles the soul and letter part
	Thread thread; 
	JButton start, instruction; //buttons to fightpanel and instructionpanel
	Rectangle soulhitbox = new Rectangle(); 
	static Rectangle [] letters = new Rectangle [26]; //array of all letter's hitbox/rectangles
	static private final Set<Integer> pressedKeys = new HashSet<>(); //hashset of player movement
	public MenuPanel() {
		super("Welcome to Anish CPT");

		setLocationRelativeTo(null);
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		soul = kit.getImage("soul.jpeg"); //soul that the player moves
		setSize(1000, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		requestFocusInWindow();

		JLabel welcome = new JLabel("Welcome To Anish's CPT"); //JLabel that gives welcome
		welcome.setBounds(1000/2 -90,50,185,100);
		welcome.setFont(new Font("Arial", Font.BOLD, 37));
		add(welcome);
		welcome.setForeground(Color.WHITE);

		JLabel nametemplate = new JLabel("Choose your name"); //JLabel for asking name
		nametemplate.setBounds(1000/2 -125,150,300,100);
		nametemplate.setFont(new Font("Arial", Font.BOLD, 28));
		add(nametemplate);
		nametemplate.setForeground(Color.WHITE);

		nameShow = new JLabel(name); //JLabel for displaying what user is entering for name
		nameShow.setBounds(1000/2 -125,200,500,100);
		nameShow.setFont(new Font("Arial", Font.BOLD, 28));
		add(nameShow);
		nameShow.setForeground(Color.WHITE);

		start = new JButton("Start"); //Start button
		start.setBounds(1000/3 - 150,800,300,100);
		start.setFont(new Font("Arial", Font.BOLD, 35));
		add(start);
		start.setBackground(Color.RED);
		start.setForeground(Color.BLACK);
		start.setOpaque(true);
		start.addActionListener(this);
		
		instruction = new JButton("Instructions"); //Instruction button
		instruction.setBounds(2000/3 - 150,800,300,100);
		instruction.setFont(new Font("Arial", Font.BOLD, 35));
		add(instruction);
		instruction.setBackground(Color.RED);
		instruction.setForeground(Color.BLACK);
		instruction.setOpaque(true);
		instruction.addActionListener(this);

		setVisible(true);
		canvas = new Namer(this);
		canvas.setBounds(50,300,900,500);
		canvas.setOpaque(false);
		add(canvas); //JPanel of the box and everything inside of it

		int temp=20;
		int height=50;
		for(int loop=0;loop<26;loop++) {
			letters[loop] = new Rectangle(temp,height,80,50); //Declarition of all letters in correct spots
			if((temp)>canvas.getWidth()-50) {
				height+=100;
				temp=20;
			}
			temp+=100;	

		}


		//createLetters();

		thread = new Thread(this); thread.start(); //Starts thread to repeatly reprint what is happening
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	/*
	 * Pre: KeyEvent e
	 * Post: None
	 * Purpose: To add what is being pressed and if enter is pressed than to check if player soul is above or intersecting a letter
	 */
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			for(int loop=0;loop<26;loop++) {
				if(letters[loop].intersects(soulhitbox)) {
					if(name.length()<7) {
						name=name+Namer.alphabet[loop];
						nameShow.setText(name);
					}
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { //Deletes the last letter if backspace is pressed
			int length = name.length();
			if(length>0) {
				name = name.substring(0,length-1);
				nameShow.setText(name);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}

	public void run() {
		while (true) {
			if (pressedKeys.contains(KeyEvent.VK_RIGHT)) //Movement system
				playerx += 5;
			if (pressedKeys.contains(KeyEvent.VK_LEFT)) 
				playerx -= 5;
			if (pressedKeys.contains(KeyEvent.VK_UP)) 
				playery -= 5;
			if (pressedKeys.contains(KeyEvent.VK_DOWN)) 
				playery += 5;

			if (playerx < 0) // Boundary and max/min coordinates of player inside Namer JPanel
				playerx = 1;
			if (playerx > 870) 
				playerx = 869;
			if (playery < 0) 
				playery = 1;
			if (playery > 470) 
				playery = 469;
			soulhitbox.setBounds(playerx-15,playery-15,30,30); //Sets player hitbox and repaints it 
			canvas.repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
	@Override
	/*
	 * Pre: ActionEvent e
	 * Post: None
	 * Purpose: To check if either of the two buttons are pressed and to call main's method to move between panels and close this
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == start) { //If start button is pressed
			Main.name=name; //Gives main class the name which will be used for fightpanel and deathpanel
			dispose(); //Stops this panel if pressed button
			Main.switchToFightPanel();
		}
		if(e.getSource() == instruction) { //If instruction button is pressed.
			dispose();
			Main.switchToInstructionPanel();
		}
	}
}
