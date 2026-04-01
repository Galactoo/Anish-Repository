package test; 

public class Waves {
	int delayMillis;
    Runnable action;
    /*
     * Pre: int delay, Runnable action
     * Post: None
     * Purpose: To get information (to make object) of each action's delay and what they do
     */
    public Waves(int delay, Runnable action) {
        this.delayMillis = delay;
        this.action = action;
    }
}
