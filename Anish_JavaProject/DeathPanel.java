package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeathPanel extends JFrame implements KeyListener {
	private JLabel gifLabel, soulLabel;
	private JTextArea textLabel;
	private Timer textTimer;
	private String deathText = Main.name+" stay determined!"; //Text for textTimer
	private int charIndex = 0; //The index of deathText
	int option=1; 
	JLabel option1,option2, text;
	private boolean lock; //Makes user unable to move option if gif is not done

	public DeathPanel() {
		super("Game Over");
		lock=true;
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.BLACK);
		setFocusable(true);
		requestFocusInWindow();
		
		gifLabel = new JLabel(new ImageIcon("soulshatter.gif")); // soul breaking gif
		gifLabel.setBounds(0,0,600,600);
		add(gifLabel);
		
		ImageIcon soulpic = new ImageIcon("soul.jpeg"); // soul image used to choose which two options user picks
		Image img1 = soulpic.getImage();
		Image newimg1 = img1.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH ) ; 
		soulpic = new ImageIcon(newimg1);
		soulLabel = new JLabel(soulpic);
		add(soulLabel);
		soulLabel.setVisible(false);
		
		textLabel = new JTextArea(""); //The textarea that prints the name stay determined
		textLabel.setFont(new Font("Arial", Font.BOLD, 28));
		textLabel.setBounds(100,200,300,200);
		textLabel.setLineWrap(true);
		textLabel.setWrapStyleWord(true);
		textLabel.setEditable(false);
		textLabel.setBackground(Color.black);
		textLabel.setForeground(Color.white);
		add(textLabel);
		
		text = new JLabel("Game Over"); //Game over JLabel
		text.setFont(new Font("Arial", Font.BOLD, 40));
		text.setBounds(100,25,300,100);
		text.setLocation(600 / 2 - 250 / 2, 15);
		add(text);
		text.setVisible(false);
		text.setForeground(Color.white);

		option1 = new JLabel(" *  Try Again"); //Try again JLabel
		option1.setFont(new Font("Arial", Font.BOLD, 20));
		option1.setBounds(75,450,150,100);
		add(option1);
		option1.setVisible(false);
		option1.setForeground(Color.white);

		option2 = new JLabel(" *  Exit to Menu"); //Exit to Menu JLabel
		option2.setFont(new Font("Arial", Font.BOLD, 20));
		option2.setBounds(400,450,150,100);
		add(option2);
		option2.setVisible(false);
		option2.setForeground(Color.white);

		Timer delayTimer = new Timer(2500, new ActionListener() { //Timer to wait for soulshatter gif to fully play for the two options and player selection be shown 
			public void actionPerformed(ActionEvent e) {
				((Timer) e.getSource()).stop();
				startTypewriterEffect();
				lock=false;
				gifLabel.setVisible(false);
				text.setVisible(true);
			}
		});
		delayTimer.setRepeats(false); // TImer is once and starts
		delayTimer.start();
		setVisible(true);
		addKeyListener(this);
	}
	//Typewriter effect for "You died" that happens 1 letter at a time
	private void startTypewriterEffect() {
		textTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (charIndex < deathText.length()) { //Does timer again and again until char index is greater than the length of the text being printed
					textLabel.setText(textLabel.getText() + deathText.charAt(charIndex));
					charIndex++;
					textLabel.setLocation(600 / 2 - textLabel.getWidth() / 2, 200);
				} else { //If at the end of the deathText, then stop timer and repeats.
					textTimer.stop();
					option1.setVisible(true); // Show buttons when done
					option2.setVisible(true);
					soulLabel.setBounds(65,480,30,30);
					soulLabel.setVisible(true);
				}
			}
		});
		textTimer.start();
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	/*
	 * Pre: KeyEvent e
	 * Post: None
	 * Purpose: To check what keys are pressed
	 */
	public void keyPressed(KeyEvent e) {
		int event = e.getKeyCode(); // For hashset's movement
		if(!lock) { //Checks if key pressed is after the gif played
			if (event == KeyEvent.VK_RIGHT) {
				option++; //Moves option to the right
			}
			else if (event == KeyEvent.VK_LEFT) {
				option--; //Moves to left
			}
			if(option>2) //Limits
				option=2;
			if(option<1)
				option=1;
			if(event == KeyEvent.VK_ENTER) {
				if(option==1) {
					Main.switchToFightPanel(); //If user presses enter when on one of two option than it goes to either FightPanel or MenuPanel depending on choice
				}
				if(option==2) {
					Main.switchToMenuPanel();
				}
			}
			if(option==1) { //Draws soul
				soulLabel.setBounds(65,480,30,30);
			}
			if(option==2) {
				soulLabel.setBounds(385,480,30,30);
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
