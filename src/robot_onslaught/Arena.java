package robot_onslaught;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Arena {

	private int rows;
	private int cols;
	private int numRobots;
	private ArrayList<MyRobot> robots = new ArrayList<>(Onslaught.MAXROBOTS);
	private Player player;

	public Arena(int nRows, int nCols) {
		if (nRows <= 0 || nCols <= 0 || nRows > Onslaught.MAXROWS || nCols > Onslaught.MAXCOLS) {
			System.out.println("***** Arena created with invalid size " + nRows + " by " + nCols + "!");
			System.exit(0);
		}
		rows = nRows;
		cols = nCols;
		numRobots = 0;
		player = null;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getRobots() {
		return numRobots;
	}

	public Player getPlayer() {
		return player;
	}

	// Add a new player to the arena at location (r,c), but only if there isn't already a player
	public boolean addPlayer(int r, int c) {
		if (player != null) {
			return false;
		}
		player = new Player(this, r, c);
		return true;
	}

	// Add a robot to the arena at location (r,c), but only if there are less than the max number of robots
	public boolean addRobot(int r, int c) {
		if (numRobots == Onslaught.MAXROBOTS) {
			return false;
		}
		robots.add(new MyRobot(this, r, c));
		numRobots++;
		return true;
	}

	// Return how many robots are at location (r,c)
	public int numRobotsAt(int r, int c) {
		int count = 0;
		for (MyRobot robot : robots) {
			if (robot.getRow() == r && robot.getCol() == c) {
				count++;
			}
		}
		return count;
	}

	// Return true if the proposed next position is in the arena and false if outside the arena
	public void nextPlayerPosition(int r, int c, int dir) {
		switch (dir) {
		case Onslaught.UP:
			if (r <= 1) {
				return;
			} else {
				r--;
				player.setRow(r);
			}
			break;
		case Onslaught.DOWN:
			if (r >= getRows()) {
				return;
			} else {
				r++;
				player.setRow(r);
			}
			break;
		case Onslaught.LEFT:
			if (c <= 1) {
				return;
			} else {
				c--;
				player.setCol(c);
			}
			break;
		case Onslaught.RIGHT:
			if (c >= getCols()) {
				return;
			} else {
				c++;
				player.setCol(c);
			}
			break;
		default:
			return;
		}
	}

	// Return true if the proposed next position is in the arena and false if outside the arena
	public boolean nextRobotPosition(MyRobot robot, int r, int c, int dir) {
		switch (dir) {
		case Onslaught.UP:
			if (r <= 1) {
				return false;
			} else {
				r--;
				robot.setRow(r);
			}
			break;
		case Onslaught.DOWN:
			if (r >= getRows()) {
				return false;
			} else {
				r++;
				robot.setRow(r);
			}
			break;
		case Onslaught.LEFT:
			if (c <= 1) {
				return false;
			} else {
				c--;
				robot.setCol(c);
			}
			break;
		case Onslaught.RIGHT:
			if (c >= getCols()) {
				return false;
			} else {
				c++;
				robot.setCol(c);
			}
			break;
		default:
			return false;
		}
		return true;
	}

	// Return true if the robot that was attacked dies and false if the robot survives the attack
	public boolean attackRobotAt(int r, int c, int dir) {
		for (MyRobot robot : robots) {
			if (robot.getRow() == r && robot.getCol() == c) {
				if (robot.getAttacked(dir)) {
					robots.remove(robot);
					numRobots--;
					return true;
				}
			}
		}
		return false;
	}

	// Move the robots in a random direction and if the robot ends up in the same location as the player, then kill the player
	public void moveRobots() {
		for (MyRobot robot : robots) {
			robot.move();
			if (robot.getRow() == player.getRow() && robot.getCol() == player.getCol()) {
				player.setDead();
			}
		}
	}

	// Clear the console
	public static void clearConsole() throws AWTException {
		Robot rob = new Robot();
        rob.keyPress(KeyEvent.VK_ALT);
        rob.keyPress(KeyEvent.VK_SHIFT);
        rob.keyPress(KeyEvent.VK_Q);
        rob.keyRelease(KeyEvent.VK_ALT);
        rob.keyPress(KeyEvent.VK_SHIFT);
        rob.keyPress(KeyEvent.VK_Q);
        rob.keyPress(KeyEvent.VK_C);
        rob.keyRelease(KeyEvent.VK_C);
		rob.keyPress(KeyEvent.VK_F10);
		rob.keyRelease(KeyEvent.VK_SHIFT);
		rob.keyRelease(KeyEvent.VK_F10);
		rob.keyPress(KeyEvent.VK_R);
		rob.keyRelease(KeyEvent.VK_R);
	}
	
	// Display the arena to the console
	public void display() {

		// Create the grid for the game
		char[][] grid = new char[Onslaught.MAXROWS][Onslaught.MAXCOLS];
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				grid[i][j] = '.';
			}
		}

		// Put the robots into the grid
		for (MyRobot robot : robots) {
			switch (grid[robot.getRow() - 1][robot.getCol() - 1]) {
			case '.':
				// If a robot only has 1 health, show a lower case 'r'. Otherwise, show an 'R'. 
				if (robot.getHealth() == 1) {
					grid[robot.getRow() - 1][robot.getCol() - 1] = 'r';
				} else {
					grid[robot.getRow() - 1][robot.getCol() - 1] = 'R';
				}
				break;
			case 'r':
			case 'R':
				grid[robot.getRow() - 1][robot.getCol() - 1] = '2';
				break;
			case '9':
				break;
			default:
				grid[robot.getRow() - 1][robot.getCol() - 1]++;
			}
		}

		// Put the player into the grid and if a robot is in the same location, indicate that the player has died
		if (player != null) {
			if (grid[player.getRow() - 1][player.getCol() - 1] == '.') {
				grid[player.getRow() - 1][player.getCol() - 1] = '@';
			} else {
				grid[player.getRow() - 1][player.getCol() - 1] = '*';
			}
		}

		// Draw out the grid
		try {
			clearConsole();
		} catch (AWTException e) {
			System.out.println("Could not clear console");
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("Could not sleep");
		}
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
		System.out.println();

		// Print out robot count, player info, and user input message
		System.out.println();
		System.out.println("There are " + getRobots() + " robots remaining.");
		if (player == null) {
			System.out.println("There is no player.");
		} else {
			if (player.getAge() > 0) {
				System.out.print("The player has lasted " + player.getAge());
				if (player.getAge() == 1) {
					System.out.println(" step.");
				} else {
					System.out.println(" steps.");
				}
			}
			if (player.isDead()) {
				System.out.println("The player is dead.");
				System.out.println();
				System.out.println("**********GAME OVER!!**********");
			}
			if (numRobots == 0) {
				System.out.println();
				System.out.println("-----------WINNER!!!-----------");
			}
		}
	}
}
