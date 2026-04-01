package test;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.*;

public class BlueBone extends Attacks {
	int speedX, speedY, duration, time;
	double angle;
	Shape boneHitbox;
	AffineTransform turning;
	/*
	 * Pre: int x, int y,  int speedX, int speedY,int width, int height, double angle
	 * Post: None
	 * Purpose: To create a new blue bone's integer to create an object so it can be used later
	 */
	public BlueBone(int x, int y,  int speedX, int speedY,int width, int height, double angle) {
        super(x, y, width, height, 1);
        this.speedX = speedX;
        this.speedY = speedY;
        //this.duration = duration;
        time = (int)System.currentTimeMillis();
        this.angle=Math.toRadians(angle);
    }
    
    public void update() {
        x += speedX;
        y += speedY;
        //if(((int)System.currentTimeMillis()-time)> duration) {
        	//active=false;
        //}
        hitbox.setBounds(x, y, width, height);
		turning = AffineTransform.getRotateInstance(this.angle,x + width/2, y+ height/2);
        boneHitbox= turning.createTransformedShape(hitbox);
    }

    public void draw(Graphics2D g2) {
        if (!active) 
        	return;
        AffineTransform original = g2.getTransform();
		AffineTransform rotation = AffineTransform.getRotateInstance(angle, x + width/2, y + height/2);
		g2.transform(rotation);
        g2.drawImage(FightPanel.getBlueBone(), x, y, width, height, null);
		g2.setTransform(original);
    }
    /*
	 * Pre: Rectangle soulHitbox
	 * Post: Boolean intersect
	 * Purpose: To check if this attack is intersecting the player and if so, check if player is moving
	 */
    public boolean checkCollision(Rectangle soulHitbox) {
        if (active && boneHitbox.intersects(soulHitbox) && !FightPanel.pressedKeys.isEmpty()) {
            //active = false;
            return true;
        }
        return false;
    }
}
