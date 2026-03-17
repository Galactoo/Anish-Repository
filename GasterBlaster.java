package test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class GasterBlaster extends Attacks {
	double angle;
	String size;
	int duration=500; //Duration of how long the blasters fire for
	long starttime; //To keep track of blaster's time when starting firing
	boolean firing = false; //To keep the blaster from not firing early
	int beamLength=0; //the length of the beam of the gaster blaster
	Rectangle blasterHitbox; //Hitbox of the gasterblaster itself (won't be used)
	Shape beamShape; //Shape of beam (since turned by again)
	AffineTransform turning; //Turns the gaster blaster and the beam by the angle given

	//Temporarily, will delete once spawnGBAttacks is used fully 
	public GasterBlaster(int x, int y, int damage, double angle, String size) {
		super(x, y, getSize(size), getSize(size), damage);
		this.angle = angle;
		this.size = size;
		blasterHitbox = new Rectangle(x, y, width, height);
		beamShape = new Rectangle2D.Double(x + width/2 - width/4,y + height,width/2,0);

		turning = AffineTransform.getRotateInstance(angle,x + width/2,y + height/2);
	}
	/*
	 * Pre: int x, int y, int damage, double angle, String size, int duration
	 * Post: None
	 * Purpose: To create a new gaster blaster's integer so it can be drawned later
	 */

	public GasterBlaster(int x, int y, int damage, double angle, String size, int duration) {
		super(x, y, getSize(size), getSize(size), damage);
		this.angle = angle;
		this.size = size;
		this.duration=duration;
		blasterHitbox = new Rectangle(x, y, width, height);
		beamShape = new Rectangle2D.Double(x + width/2 - width/4,y + height,width/2,0);
		turning = AffineTransform.getRotateInstance(angle,x + width/2,y + height/2);
	}
	/*
	 * Pre: String size
	 * Post: int dimension
	 * Purpose: To return the size of the gaster blaster for super constructor as you can not do calculations before super
	 */
	public static int getSize(String size) {
		if(size.equals("small")) {
			return 60;
		}
		else if(size.equals("large")) {
			return 170;
		}
		else {
			return 120;
		}
	}
	public void update() {
		if(firing) {
			beamLength+=60;
			Shape straightBeam = new Rectangle2D.Double(x + width/4,y + height, width/2, beamLength);
			beamShape = turning.createTransformedShape(straightBeam);
		}
		if (firing && System.currentTimeMillis() - starttime > duration) {
			firing = false;
			beamLength = 0;
			active=false;
		}
	}
	/*
	 * Pre: None
	 * Post: None
	 * Purpose: To keep track of when the gaster blaster was fired so it can stop after duration past
	 */
	public void startFiring() {  
		firing = true;
		starttime = System.currentTimeMillis();
	}
	/*
	 * Pre: Graphics g2
	 * Post: None
	 * Purpose: To draw the gaster blaster at the angle given in the constructor. Also to draw what type of blaster (1 is for non firing and 2 is for firing)
	 */
	public void draw(Graphics2D g2) {
		if (!active) 
			return; 
		AffineTransform original = g2.getTransform();
		AffineTransform rotation = AffineTransform.getRotateInstance(angle, x + width/2, y + height/2);
		g2.transform(rotation);
		if(!firing)
			g2.drawImage(FightPanel.getGasterBlaster1(), x, y, width, height, null);
		else {
			g2.drawImage(FightPanel.getGasterBlaster2(), x, y, width, height, null);
		}
		g2.setTransform(original);
		drawBeam(g2);
	}
	/*
	 * Pre: Graphics2D g2
	 * Post: None
	 * Purpose: To draw beam of gaster blaster
	 */
	public void drawBeam(Graphics2D g2) {
		if (!firing || !active) 
			return;
		Composite originalComp = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.setColor(new Color(0, 255, 255, 200));
		g2.fill(beamShape);
		g2.setComposite(originalComp);
	}
	/*
	 * Pre: Rectangle soulHitbox
	 * Post: boolean intersect
	 * Purpose: To check if the player is touching the beam
	 */
	public boolean checkCollision(Rectangle soulHitbox) {
		if (!active) 
			return false;
		return beamShape.intersects(soulHitbox) /*|| blasterHitbox.intersects(soulHitbox)*/;
	}
}