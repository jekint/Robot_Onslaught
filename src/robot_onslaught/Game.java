package robot_onslaught;

import java.awt.Toolkit;
import java.util.Scanner;

public class Game {

	private Arena arena;

	public Game(int rows, int cols, int numRobots) {
		if (numRobots < 0) {
			System.out.println("***** Cannot create Game with negative number of robots!");
			System.exit(0);
		}
		if (numRobots > Onslaught.MAXROBOTS) {
			System.out.println("***** Trying to create Game with " + numRobots + " robots; only " + Onslaught.MAXROBOTS
					+ " are allowed!");
			System.exit(0);
		}
		if (rows == 1 && cols == 1 && numRobots > 0) {
			System.out.println("***** Cannot create Game with nowhere to place the robots!");
			System.exit(0);
		}

		// Create arena with "rows" number of rows and "cols" number of columns
		arena = new Arena(rows, cols);

		// Add player at a random location
		int player_r = Onslaught.rand.nextInt(1, rows + 1);
		int player_c = Onslaught.rand.nextInt(1, cols + 1);
		arena.addPlayer(player_r, player_c);

		// Add robots to the arena at random locations and make sure a robot doesn't
		// spawn at the player's location
		while (numRobots > 0) {
			int robot_r = Onslaught.rand.nextInt(1, rows + 1);
			int robot_c = Onslaught.rand.nextInt(1, cols + 1);
			if (robot_r == player_r && robot_c == player_c) {
				continue;
			}
			arena.addRobot(robot_r, robot_c);
			numRobots--;
		}
	}

	public void play() {
		Player player = arena.getPlayer();
		Scanner scanner = new Scanner(System.in);
		String input;
		if (player == null) {
			arena.display();
			scanner.close();
			return;
		}
		// Control player with user input
		do {
			arena.display();
			System.out.println();
			System.out.println("Move or attack (w/a/s/d)");
			System.out.println("Quit (q)");
			input = scanner.nextLine();
			if (input.isEmpty()) {
				player.stand();
			} else {
				switch (input.charAt(0)) {
				case 'Q':
				case 'q':
					scanner.close();
					return;
				case 'W':
				case 'w':
					player.moveOrAttack(Onslaught.UP);
					break;
				case 'S':
				case 's':
					player.moveOrAttack(Onslaught.DOWN);
					break;
				case 'A':
				case 'a':
					player.moveOrAttack(Onslaught.LEFT);
					break;
				case 'D':
				case 'd':
					player.moveOrAttack(Onslaught.RIGHT);
					break;
				default:
					Toolkit.getDefaultToolkit().beep();
					continue;
				}
			}
			arena.moveRobots();
		} while (!arena.getPlayer().isDead() && arena.getRobots() > 0);
		arena.display();
		scanner.close();
	}
}
