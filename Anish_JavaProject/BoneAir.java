package test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BoneAir extends Attacks {
	private int speedX, speedY;
	double angle;
	Shape boneHitbox;
	AffineTransform turning;
	/*
	 * Pre: int x, int y, int speedX, int speedY, int width, int height,double angle
	 * Post: None
	 * Purpose: To create a new regular bone's integer to create an object so it can be used later
	 */
	public BoneAir(int x, int y, int speedX, int speedY, int width, int height,double angle) {
		super(x, y, width, height, 1);
		this.speedX = speedX;
		this.speedY = speedY;
		this.angle=Math.toRadians(angle); //Makes the number into radians which java uses
	}

	public void update() {
		x += speedX;
		y += speedY;
		hitbox.setBounds(x, y, width, height);
		turning = AffineTransform.getRotateInstance(this.angle,x + width/2, y+ height/2); //Turns the object by an angle
        boneHitbox= turning.createTransformedShape(hitbox); //Sets the shape hitbox of the bone to the turned rectangle
	}

	public void draw(Graphics2D g2) {
		if (!active) return;
		if(!(angle==0)) {
			AffineTransform original = g2.getTransform();
			AffineTransform rotation = AffineTransform.getRotateInstance(angle, x + width/2, y + height/2);
			g2.transform(rotation);
			g2.drawImage(FightPanel.getBoneThrow(), x, y, width, height, null);
			g2.setTransform(original);
		}
		else
			g2.drawImage(FightPanel.getBoneThrow(), x, y, width, height, null);
	}



	public BoneAir(int x, int y,int speedX, int speedY , int width , int height) {
		super(x, y, width, height, 1);
		this.speedX = speedX;
		this.speedY = speedY;
		angle=Math.toRadians(0);
		turning = AffineTransform.getRotateInstance(this.angle,x,y);
	}
	public boolean checkCollision(Rectangle soulHitbox) {
        if (active && boneHitbox.intersects(soulHitbox) ) {
            //active = false;
            return true;
        }
        return false;
    }
}