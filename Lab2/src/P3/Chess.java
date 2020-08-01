package P3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * һ��mutable���͡��������������Ϸ
 */
public class Chess implements Game {
	private final Player white;
	private final Player black;
	private Action action;

	// Rep invariant
	//   white��Ϊ��
	//   black��Ϊ��
	//   action��Ϊ��
	// Abstraction function
	//   AF(white,black,action) = һ������ʵ�еĹ���������Ϸ��ӳ��
	// Safety from rep exposure
	//   ���е���������˽�е�

	/**
	 * ��������������Ϸ
	 * 
	 * @param player1 �׷���������
	 * @param player2 �ڷ���������
	 */
	public Chess(String player1, String player2) {
		white = new Player(player1);
		black = new Player(player2);
		action = new Action(8, white, black);
	}

	/**
	 * ����������Ϸ
	 * 
	 * @param input ����̨����
	 * @throws IOException �ļ������쳣
	 */
	public void game(Scanner input) throws IOException {
		game(input, white, black);
	}

	@Override
	public void menu(Player player) {
		System.out.println("1.�ƶ�������ĳ��λ�õ���������λ�ã�");
		System.out.println("2.���ӣ�");
		System.out.println("3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���");
		System.out.println("4.����������ҷֱ��������ϵ�����������");
		System.out.println("5.������");
		System.out.print(player.getName() + "�������������ţ����롰end��������Ϸ����");
	}

	@Override
	public void play(int actNumber, Player player, Scanner input, PrintWriter output) {
		int sourceX, sourceY, targetX, targetY;
		boolean isInvalid = true;
		switch (actNumber) {
		case 1:
			System.out.print(player.getName() + "����������ʼλ�ú�Ŀ��λ�ã�");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				targetX = input.nextInt();
				targetY = input.nextInt();
				input.nextLine();
				if (action.movePiece(player, sourceX, sourceY, targetX, targetY, output))
					isInvalid = false;
				else
					System.out.print("���������룺");
			}
			break;
		case 2:
			System.out.print(player.getName() + "����������ʼλ�ú�Ŀ��λ�ã�");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				targetX = input.nextInt();
				targetY = input.nextInt();
				input.nextLine();
				if (action.eatPiece(player, sourceX, sourceY, targetX, targetY, output))
					isInvalid = false;
				else
					System.out.print("���������룺");
			}
			break;
		case 3:
			System.out.print(player.getName() + "��������Ҫ��ѯ��λ�ã�");
			while (isInvalid) {
				sourceX = input.nextInt();
				sourceY = input.nextInt();
				if (action.query(player, sourceX, sourceY, output))
					isInvalid = false;
				else
					System.out.print("���������룺");
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
