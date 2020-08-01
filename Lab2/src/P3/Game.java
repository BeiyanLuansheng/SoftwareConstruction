package P3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * һ����
 */
public interface Game {	
	/**
	 * һ������Ϸ
	 * @param input ����̨����
	 * @param currentPlayer ��������
	 * @param waitPlayer ��������
	 * @throws IOException�ļ�����ʧ��
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
				System.out.print("������������ȷ�Ĳ�����");
				act = input.nextLine();
				continue;
			}
			System.out.println();
			menu(currentPlayer);
			act = input.nextLine();
		}
		output.print(currentPlayer.getName() + ":\tend");
		output.close();
		System.out.print("�Ƿ�Ҫ�鿴������ʷ(Y/N)��");
		if(input.nextLine().equalsIgnoreCase("Y")) System.out.print(this.history(file));
	}
	
	/**
	 * ������Ϸ�Ĳ˵�
	 * @param player �ǿ����֣�����������һ��
	 */
	public void menu(Player player);
	
	/**
	 * ����Ϸ�Ķ���
	 * @param actNumber �Ϸ��Ķ����Ĵ���
	 * @param player �����ķ�����
	 * @param input ����̨����
	 * @param output �ļ����
	 */
	public void play(int actNumber, Player player, Scanner input, PrintWriter output);
	
	/**
	 * ���˫����������ʷ��¼
	 * @param file ������ʷ��¼���ļ�
	 * @return ��ʷ��¼���ɵ��ַ���
	 * @throws IOException �ļ�����ʧ��
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
