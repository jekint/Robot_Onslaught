package robot_onslaught;

public class MyRobot {
	
	private int row;
	private int col;
	private int health;
	private Arena arena;
	
	public MyRobot (Arena ap, int r, int c) {
		if (ap == null) {
			System.out.println("***** A robot must be in some Arena!");
			System.exit(0);
		}
		if (r < 1 || c < 1 || r > ap.getRows() || c > ap.getCols()) {
			System.out.println("***** Robot created with invalid coordinates (" + r + "," + c + ")!");
			System.exit(0);
		}
		row = r;
		col = c;
		health = Onslaught.INITIAL_ROBOT_HEALTH;
		arena = ap;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public int getHealth() {
		return health;
	}
	
	// Try to move robot either up (0), down (1), left (2), or right (3)
	public void move() {
		int dir = Onslaught.rand.nextInt(0,4);
		arena.nextRobotPosition(this, row, col, dir);
	}
	
	// Robot gets attacked and if it dies return true
	public boolean getAttacked(int dir) {
		health--;
		if (health == 0) {
			return true;
		}
		if (!arena.nextRobotPosition(this, row, col, dir)) {
			health = 0;
			return true;
		}
		return false;
	}
}
