package test;

import java.awt.*;

import javax.swing.*;

public class Namer extends JPanel {
	MenuPanel menu;
	
	static String [] alphabet= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; //Array of all letters
	/*
	 * Pre: MenuPanel menu
	 * Post: None
	 * Purpose: To get instance of menupanel so can use variables from that class
	 */
	public Namer(MenuPanel menu) {
		this.menu=menu;
	}
	/*
	 * Pre: Graphics g
	 * Post: None
	 * Purpose: To paint everything inside JPanel like the player soul or letters on MenuPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.drawImage(MenuPanel.soul, menu.playerx, menu.playery, 30, 30, this); //Draws player soul in the box
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, getWidth(), getHeight());
        int temp=-20; //letter starting x value
    	int height=100; //letter starting y value
        
		g2.setFont(new Font("Serif", Font.BOLD,75));
		for(int loop=0;loop<26;loop++) {
			if((temp)>getWidth()-50) { //Checks if letter (rectangle) goes beyond the JPanel and if so to go down a row
				height+=100; //Goes down a row
				temp=-20; //Goes to first column.
			}	
			MenuPanel.letters[loop].setBounds(temp+40,height-60,75,75); //Sets the bounds of the rectangle to be used for collosion check
			g2.drawString(alphabet[loop], 50+temp, height); //Draws the letters 
			g2.draw(MenuPanel.letters[loop]); //Draws the rectangles so player can see where to select
			temp+=100; //Pushes everying to next column.
		}
	}
	
}
