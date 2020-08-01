package P3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * 一盘棋
 */
public interface Game {	
	/**
	 * 一盘棋游戏
	 * @param input 控制台输入
	 * @param currentPlayer 先手棋手
	 * @param waitPlayer 后手棋手
	 * @throws IOException文件操作失败
	 */
	public default void game(Scanner input, Player currentPlayer, Player waitPlayer) throws IOException{
		File file = new File("src/P3/history.txt");
		if(file.exists()) file.delete();
		file.createNewFile();
		PrintWriter output = new PrintWriter(file);
		menu(currentPlayer);
		String act = input.nextLine();
		while(!act.equalsIgnoreCase("end")) {
			try {
				int actNumber = Integer.valueOf(act);
				if(actNumber > 5 || actNumber < 1)
					throw new NumberFormatException();
				play(actNumber, currentPlayer, input, output);
				Player temp = currentPlayer;
				currentPlayer = waitPlayer;
				waitPlayer = temp;
			} catch (NumberFormatException e) {
				System.out.print("请重新输入正确的操作：");
				act = input.nextLine();
				continue;
			}
			System.out.println();
			menu(currentPlayer);
			act = input.nextLine();
		}
		output.print(currentPlayer.getName() + ":\tend");
		output.close();
		System.out.print("是否要查看操作历史(Y/N)？");
		if(input.nextLine().equalsIgnoreCase("Y")) System.out.print(this.history(file));
	}
	
	/**
	 * 棋类游戏的菜单
	 * @param player 非空棋手，动作发生的一方
	 */
	public void menu(Player player);
	
	/**
	 * 玩游戏的动作
	 * @param actNumber 合法的动作的代号
	 * @param player 动作的发生者
	 * @param input 控制台输入
	 * @param output 文件输出
	 */
	public void play(int actNumber, Player player, Scanner input, PrintWriter output);
	
	/**
	 * 输出双方动作的历史记录
	 * @param file 保存历史记录的文件
	 * @return 历史记录构成的字符串
	 * @throws IOException 文件操作失败
	 */
	public default String history(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuffer sb = new StringBuffer();
		String content = br.readLine();
		while(content != null){
			sb.append("\n");
			sb.append(content);
			content = br.readLine();
		}
		br.close();
		return sb.toString();
	}
}
