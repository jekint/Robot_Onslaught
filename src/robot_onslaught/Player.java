package robot_onslaught;

public class Player {

	private int row;
	private int col;
	private int age;
	private boolean dead;
	private Arena arena;

	public Player(Arena ap, int r, int c) {
		if (ap == null) {
			System.out.println("***** The player must be in some Arena!");
			System.exit(0);
		}
		if (r < 1 || c < 1 || r > Onslaught.MAXROWS || c > Onslaught.MAXCOLS) {
			System.out.println("**** Player created with invalid coordinates (" + r + "," + c + ")!");
			System.exit(0);
		}
		row = r;
		col = c;
		age = 0;
		dead = false;
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

	public boolean isDead() {
		return dead;
	}

	public void setDead() {
		dead = true;
	}

	public int getAge() {
		return age;
	}

	public void stand() {
		age++;
	}

	// Moves the player or attacks a robot depending on what is around the player
	public void moveOrAttack(int dir) {
		age++;
		int temp_row = row;
		int temp_col = col;
		switch (dir) {
		case Onslaught.UP:
			temp_row--;
			break;
		case Onslaught.DOWN:
			temp_row++;
			break;
		case Onslaught.LEFT:
			temp_col--;
			break;
		case Onslaught.RIGHT:
			temp_col++;
			break;
		default:
			break;
		}
		if(arena.numRobotsAt(temp_row,temp_col) > 0) {
			arena.attackRobotAt(temp_row, temp_col, dir);
		} else {
			arena.nextPlayerPosition(row, col, dir);
		}
	}
}
