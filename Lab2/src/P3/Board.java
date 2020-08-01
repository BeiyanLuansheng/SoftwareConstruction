package P3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * һ��mutable�����ͣ�����һ������
 */
public class Board {
	private Map<Position, Piece> board;
	
	// Rep invariant:
	//   board��Ϊ��
	// Abstraction function:
	//   AF(board) = һ����ʵ������
	// Safety from rep exposure:
	//   ���е���������˽�е�
	//   �������ʱʹ��Collections.unmodifiableMap()����ת�����˲����޸ĵ�����
	//   ��������ϵ�ĳ��λ�õ�����ʱ�������ǲ��ɱ��
	
	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.board != null;
	}
	/**
	 * ��ʼ������
	 * @param size ���̴�С��chessΪ8��goΪ18
	 * @param white �׷�����
	 * @param black �ڷ�����
	 */
	public Board(int size, Player white, Player black) {
		this.board = new HashMap<Position, Piece>();
		if(size == 8) {
			for(int i=0; i<size; i++) { //��ʼʱ��������Ŀ�λ
				for(int j=2; j<6; j++)
					board.put(new Position(i, j), null);
			}
			try {
				Scanner in = new Scanner(new File("src/P3/chess_config.txt"));
				for(int i=0; i<16; i++) {
					String type = in.next();
					int x = in.nextInt();
					int y = in.nextInt();
					board.put(new Position(x, y), new Piece(type, white));
				}
				for(int i=0; i<16; i++) {
					String type = in.next();
					int x = in.nextInt();
					int y = in.nextInt();
					board.put(new Position(x, y), new Piece(type, black));
				}
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("�������ļ���");
			}
		}
		else {
			for(int i=0; i<=size; i++)
				for(int j=0; j<=size; j++)
					board.put(new Position(i, j), null);
		}
		checkRep();
	}
	
	/**
	 * �������ϵĺϷ�λ�÷�������
	 * @param position �������ӵ�Ŀ��λ�ã����еĺϷ�λ��
	 * @param piece ���õ�����
	 */
	public void put(Position position, Piece piece) {
		board.put(position, piece);
		checkRep();
	}
	
	/**
	 * �������ϵĺϷ�λ��ȡ������
	 * @param position ��ȡ�����ӵ�λ�ã������еĺϷ�λ��
	 */
	public void take(Position position) {
		board.put(position, null);
		checkRep();
	}
	
	/**
	 * @return ����һ�������޸ĵ�����״̬ 
	 */
	public Map<Position, Piece> getBoard(){
		return Collections.unmodifiableMap(board);
	}
	
	/**
	 * ��ѯ������ĳ���Ϸ�λ�õ�״̬
	 * @param position �����ϵĺϷ�λ��
	 * @return ��������ӣ����ظ�����
	 *         ���û�����ӣ�����null
	 */
	public Piece get(Position position) {
		return board.get(position);
	}
}
