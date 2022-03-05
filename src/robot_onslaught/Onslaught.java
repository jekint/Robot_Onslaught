package robot_onslaught;

import java.util.Random;

public class Onslaught {
	
	public final static int MAXROWS = 20;
	public final static int MAXCOLS = 30;
	public final static int MAXROBOTS = 100;
	public final static int INITIAL_ROBOT_HEALTH = 2;
	
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	public final static Random rand = new Random();

	public static void main(String[] args) {
		
		Game g = new Game(10, 10, 15);
		g.play();
		
	}

}
