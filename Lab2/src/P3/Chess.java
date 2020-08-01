package P3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 一个mutable类型。代表国际象棋游戏
 */
public class Chess implements Game {
	private final Player white;
	private final Player black;
	private Action action;

	// Rep invariant
	//   white不为空
	//   black不为空
	//   action不为空
	// Abstraction function
	//   AF(white,black,action) = 一个到现实中的国际象棋游戏的映射
	// Safety from rep exposure
	//   所有的数据域都是私有的

	/**
	 * 创建国际象棋游戏
	 * 
	 * @param player1 白方棋手名字
	 * @param player2 黑方棋手名字
	 */
	public Chess(String player1, String player2) {
		white = new Player(player1);
		black = new Player(player2);
		action = new Action(8, white, black);
	}

	/**
	 * 国际象棋游戏
	 * 
	 * @param input 控制台输入
	 * @throws IOException 文件操作异常
	 */
	public void game(Scanner input) throws IOException {
		game(input, white, black);
	}

	@Override
	public void menu(Player player) {
		System.out.println("1.移动棋盘上某个位置的棋子至新位置；");
		System.out.println("2.吃子；");
		System.out.println("3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；");
		System.out.println("4.计算两个玩家分别在棋盘上的棋子总数；");
		System.out.println("5.跳过；");
		System.out.print(player.getName() + "，输入操作的序号（输入“end”结束游戏）：");
	}

	@Override
	public void play(int actNumber, Player player, Scanner input, PrintWriter output) {
		int sourceX, sourceY, targetX, targetY;
		boolean isInvalid = true;
		switch (actNumber) {
		case 1:
			System.out.print(player.getName() + "，请输入起始位置和目的位置：");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				targetX = input.nextInt();
				targetY = input.nextInt();
				input.nextLine();
				if (action.movePiece(player, sourceX, sourceY, targetX, targetY, output))
					isInvalid = false;
				else
					System.out.print("请重新输入：");
			}
			break;
		case 2:
			System.out.print(player.getName() + "，请输入起始位置和目的位置：");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				targetX = input.nextInt();
				targetY = input.nextInt();
				input.nextLine();
				if (action.eatPiece(player, sourceX, sourceY, targetX, targetY, output))
					isInvalid = false;
				else
					System.out.print("请重新输入：");
			}
			break;
		case 3:
			System.out.print(player.getName() + "，请输入要查询的位置：");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				if (action.query(player, sourceX, sourceY, output))
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
