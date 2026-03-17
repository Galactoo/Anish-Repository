package test;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

public class InstructionPanel extends JFrame implements ActionListener{
	//	Button backToMenu;
	JButton backToMenu;
	JLabel instruction_text;

	public InstructionPanel() {
		setSize( 1000,1000 );
		setBackground(Color.black);
		setVisible(true);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		instruction_text = new JLabel("<html>"
				+ "Welcome to Sans Undertale game! Thanks for being here.<br>"
				+ "To play the game, you need to use your arrow keys.<br>"
				+ "You will also usee enter or z to select an option. <br>"
				+ " Delete or x will cancel and go back from an option if you can."
				+ "There will be bones and Gaster Blasters (The skull things) you need to dodge.<br>"
				+ "Your goal is to survive the turns. <br>"
				+ "in items, you will have 5 one use items that heal you.<br>"
				+ "Good luck! It'll be tough for the first few turns. </html>"); //JLabel with all the text
		instruction_text.setFont(new Font("Serif", Font.PLAIN,27));
		add(instruction_text);
		instruction_text.setBounds(200, 300, 600, 350);
		instruction_text.setForeground(Color.white);
		instruction_text.setVisible(true);

		backToMenu = new JButton("Return to Menu"); //Button to return to menu
		backToMenu.setVisible(true);
		add(backToMenu);
		backToMenu.addActionListener(this);
		backToMenu.setBounds(300,100,400,100);
	}
	/*
	 * Pre: ActionEvent e
	 * Post: None
	 * Purpose: To check if user pressed back to menu button after they read the instructions
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == backToMenu) {
			Main.switchToMenuPanel();
			this.dispose(); //Closes the Panel
			//setDefaultCloseOperation( JFrame.EXIT );
		}
	}
}