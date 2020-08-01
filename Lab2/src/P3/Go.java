package P3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 一个mutable类型。代表围棋游戏
 */
public class Go implements Game {
	private final Player white;
	private final Player black;
	private Action action;

	// Rep invariant:
	//   white不为空
	//   black不为空
	//   action不为空
	// Abstraction function:
	//   AF(white,black,action) = 一个到现实中的围棋游戏的映射
	//  Safety from rep exposure:
	//   所有的数据域都是私有的

	public Go(String player1, String player2) {
		white = new Player(player1);
		black = new Player(player2);
		action = new Action(18, white, black);
	}

	/**
	 * 围棋游戏
	 * 
	 * @param input 控制台输入
	 * @throws IOException 文件操作异常
	 */
	public void game(Scanner input) throws IOException {
		game(input, white, black);
	}

	@Override
	public void menu(Player player) {
		System.out.println("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；");
		System.out.println("2.提子；");
		System.out.println("3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；");
		System.out.println("4.计算两个玩家分别在棋盘上的棋子总数；");
		System.out.println("5.跳过；");
		System.out.print(player.getName() + "，输入操作的序号（输入“end”结束游戏）：");
	}

	@Override
	public void play(int actNumber, Player player, Scanner input, PrintWriter output) {
		int x, y;
		boolean isInvalid = true;
		switch (actNumber) {
		case 1:
			System.out.print(player.getName() + "，请输入放置位置：");
			while (isInvalid) {
				x = input.nextInt();
				y = input.nextInt();
				if (action.putPiece(player, new Piece(null, player), x, y, output))
					isInvalid = false;
				else
					System.out.print("请重新输入：");
			}
			input.nextLine();
			break;
		case 2:
			System.out.print(player.getName() + "，请输入提子的位置：");
			while (isInvalid) {
				x = input.nextInt();
				y = input.nextInt();
				if (action.takePiece(player, x, y, output))
					isInvalid = false;
				else
					System.out.print("请重新输入：");
			}
			input.nextLine();
			break;
		case 3:
			System.out.print(player.getName() + "，请输入要查询的位置：");
			while (isInvalid) {
				x = input.nextInt();
				y = input.nextInt();
				if (action.query(player, x, y, output))
					isInvalid = false;
				else
					System.out.print("请重新输入：");
			}
			input.nextLine();
			break;
		case 4:
			action.count(player, output);
			break;
		case 5:
			output.println(player.getName() + ":\tskip");
			break;
		}
	}
}
