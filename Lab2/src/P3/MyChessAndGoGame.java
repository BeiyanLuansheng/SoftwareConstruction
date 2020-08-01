package P3;

import java.io.IOException;
import java.util.Scanner;
/**
 * �ͻ���
 */
public class MyChessAndGoGame {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String gameType = null;
		boolean flag = true;
		System.out.print("Chess or Go? ");
		while(flag) {
			gameType = input.nextLine();
			if(gameType.equalsIgnoreCase("go") || gameType.equalsIgnoreCase("chess"))
				flag = false;
			else System.out.print("Please input chess or go: ");
		}
		System.out.print("����player1�����֣�");
		String player1 = input.nextLine();
		System.out.print("����player2�����֣�");
		String player2 = input.nextLine();
		try {
			if(gameType.equalsIgnoreCase("go")) {
				Go game = new Go(player1, player2);
				game.game(input);
			}
			else {
				Chess game = new Chess(player1, player2);
				game.game(input);
			}
		} catch (IOException e) {
			System.out.println("�ļ������쳣");
		}
		input.close();
	}	
}
