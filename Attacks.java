package test;
import java.awt.*;
 //Abstract class for all attacks
public abstract class Attacks {
	protected int x, y, width, height;
    protected int damage;
    protected boolean active = true; //attacks are automatically true for convience.
    protected Rectangle hitbox; //Default hitbox of attacks

    /*
     * Pre: int x, int y, int width, int height, int damage
     * Post: None
     * Purpose: To set up basic information of attack object like their coordinates, size and their damage (given by type of attack)
     */
    public Attacks(int x, int y, int width, int height, int damage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.damage = 1;
        this.hitbox = new Rectangle(x, y, width, height); //creates hitbox which is used by checkCollision method
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);

    /*
     * Pre: Rectangle soulHitbox
     * Post: boolean insecting
     * Purpose: To check if object is intersecting with soul
     */
    public boolean checkCollision(Rectangle soulHitbox) {
        if (active && hitbox.intersects(soulHitbox)) {
            //active = false;
            return true;
        }
        return false;
    }

    /*
     * Pre: None
     * Post: boolean active
     * Purpose: When called in BattleBox/FightPanel, to return if attack is true or not. Will be used to remove attacks
     */
    public boolean isActive() {
        return active;
    }
}
