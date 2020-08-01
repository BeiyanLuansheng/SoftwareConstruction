package P3;

import java.io.PrintWriter;
import java.util.Map;
/*
 * һ��mutable�����ͱ�ʾ�Թ��������Χ��Ĳ���
 */
public class Action {
	private Board board;
	
	// Rep invariant
	//   ���̲�Ϊ��
	// Abstraction function
	//   һ���ӳ���Ĳ�����ʵ�ʶ������ϵ����ӵĶ�����ӳ��
	// Safety from rep exposure
	//   ���е���������˽�е�
	//   �������board��״̬ʱ������Board���getBoard�������õ���һ�������޸ĵ�����
	
	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert board != null;
	}
	
	/**
	 * �õ�һ�ų�ʼ���������
	 * @param size ���̵Ĵ�С
	 * @param white �׷�����
	 * @param black �ڷ�����
	 */
	public Action(int size, Player white, Player black) {
		this.board = new Board(size, white, black);
		checkRep();
	}

	/**
	 * Χ�壺���ְ�ָ�����ӷ���ָ��λ��
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param piece ָ������
	 * @param x ָ��λ�ú�����
	 * @param y ָ��λ��������
	 * @param output �ļ����
	 * @return true �ɹ�
	 *         false ʧ��
	 */
	public boolean putPiece(Player player, Piece piece, int x, int y,  PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("λ�ó������̱߽磡");
			return false;
		}
		if(board.get(position) != null) {
			System.out.print("��λ�������������ӣ�");
			return false;
		}
		if(!piece.getPlayer().equals(player)) {
			System.out.print("�����Ӳ��Ǽ����ģ�");
			return false;
		}
		board.put(position, piece);
		output.println(player.getName() + ":\tput (" + x + "," + y + ")");
		checkRep();
		return true;
	}
	
	/**
	 * Χ�壺���ְ�ָ�����Ӵ�ָ��λ��ȡ��
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param x ��ȡ������λ�ú�����
	 * @param y ��ȡ������λ��������
	 * @param output �ļ����
	 * @return true �ɹ�
	 *         false ʧ��
	 */
	public boolean takePiece(Player player, int x, int y, PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("λ�ó������̱߽磡");
			return false;
		}
		if(board.get(position) == null) {
			System.out.print("��λ�������ӿ��ᣡ");
			return false;
		}
		if(board.get(position).getPlayer().equals(player)) {
			System.out.print("���������Ǽ������ӣ�");
			return false;
		}
		board.take(position);
		output.println(player.getName() + ":\ttake (" + x + "," + y + ")");
		checkRep();
		return true;
	}
	
	/**
	 * �������壺����ʼλ���ƶ����ӵ�Ŀ��λ��
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param sourceX ��ʼλ�ú�����
	 * @param sourceY ��ʼλ��������
	 * @param targetX Ŀ��λ�ú�����
	 * @param targetY Ŀ��λ�ú�����
	 * @param output �ļ����
	 * @return true �ƶ��ɹ�
	 *         false �ƶ�ʧ��
	 */
	public boolean movePiece(Player player, int sourceX, int sourceY, int targetX, int targetY, PrintWriter output) {
		Position source = new Position(sourceX, sourceY);
		Position target = new Position(targetX, targetY);
		if(!board.getBoard().containsKey(source) || !board.getBoard().containsKey(target)) {
			System.out.print("λ�ó������̱߽磡");
			return false;
		}
		if(board.get(target) != null) {
			System.out.print("Ŀ��λ�������������ӣ�");
			return false;
		}
		if(board.get(source) == null) {
			System.out.print("��ʼλ�������ӣ�");
			return false;
		}
		if(!board.get(source).getPlayer().equals(player)) {
			System.out.print("�ƶ������Ӳ��Ǽ����ģ�");
			return false;
		}
		Piece piece = board.get(source);
		board.take(source);
		board.put(target, piece);
		output.println(player.getName() + ":\tmove " + piece.getType() +" (" + sourceX 
						+ "," + sourceY + ") to (" + targetX + "," + targetY + ")");
		checkRep();
		return true;
	}
	
	/**
	 * �������壺���Ӷ�������һ��λ�õ����ӳԵ��ڶ���λ�õ�����
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param sourceX ��������λ�ú�����
	 * @param sourceY ��������λ��������
	 * @param targetX �з�����λ�ú�����
	 * @param targetY �з�����λ��������
	 * @param output �ļ����
	 * @return true �ɹ��Ե��Է�����
	 *         false �Ե��Է�����ʧ��
	 */
	public boolean eatPiece(Player player, int sourceX, int sourceY, int targetX, int targetY, PrintWriter output) {
		Position source = new Position(sourceX, sourceY);
		Position target = new Position(targetX, targetY);
		if(!board.getBoard().containsKey(source) || !board.getBoard().containsKey(target)) {
			System.out.print("λ�ó������̱߽磡");
			return false;
		}
		if(sourceX == targetX && sourceY == targetY) {
			System.out.print("ָ��������λ����ͬ��");
			return false;
		}
		if(board.get(source) == null) {
			System.out.print("��ʼλ�������ӣ�");
			return false;
		}
		if(board.get(target) == null) {
			System.out.print("Ŀ��λ�������ӣ�");
			return false;
		}
		if(!board.get(source).getPlayer().equals(player)) {
			System.out.print("��ʼλ�����Ӳ��Ǽ����ģ�");
			return false;
		}
		if(board.get(target).getPlayer().equals(player)) {
			System.out.print("Ŀ��λ�����Ӳ��ǵз��ģ�");
			return false;
		}
		Piece targetPiece = board.get(target);
		board.take(target);
		Piece sourcePiece = board.get(source);
		board.take(source);
		board.put(target, sourcePiece);
		output.println(player.getName() + ":\tyour " + sourcePiece.getType() + " at (" + sourceX + "," + sourceY + 
				") eat opponent " + targetPiece.getType() +" at (" + targetX + "," + targetY + ")");
		checkRep();
		return true;
	}
	
	/**
	 * ��ѯĳλ���Ƿ���У���������������˭��ʲô����
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param x ��ѯλ�õĺ�����
	 * @param y ��ѯλ�õ�������
	 * @param output �ļ����
	 * @return true ��ѯ�ɹ�
	 *         false ��ѯʧ��
	 */
	public boolean query(Player player,int x, int y, PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("λ�ó������̱߽磡");
			return false;
		}
		Piece piece = board.get(position);
		if(piece == null) {
			System.out.println("��λ��Ϊ��");	
			output.println(player.getName() + ":\tquery (" + x + "," + y + ") ��λ��Ϊ��");
		}	
		else {
			System.out.print("��������" + piece.getPlayer().getName() + "��");
			output.print(player.getName() + ":\tquery (" + x + "," + y + 
							") ��������" + piece.getPlayer().getName() + "��");
			if(piece.getType() != null){
				System.out.println(piece.getType());
				output.println(piece.getType());
			}
			else {
				System.out.println();
				output.println();
			}
		}
		return true;
	}
	
	/**
	 * ����������˫�����ж�������
	 * @param player ���������ߣ��׷���ڷ�����
	 * @param output �ļ����
	 * @return һ������������λ��ʾ���������ߵ�������������λ��ʾ���ֵ�������
	 */
	public int count(Player player, PrintWriter output) {
		int player1 = 0, player2 = 0;
		Map<Position, Piece> map = board.getBoard();
		for(Position pos: map.keySet()) {
			if(map.get(pos) != null) 
				if(map.get(pos).getPlayer().equals(player))	player1++;
				else player2++;
		}
		System.out.println("you:" + player1 + ", opponent:" + player2);
		output.println(player.getName() + ":\tcount you:" + player1 + ", opponent:" + player2);
		return player1 * 1000 + player2;
	}
	
	/**
	 * @return ���ز��ɸı������״̬
	 */
	public Map<Position, Piece> getBoard() {
		return board.getBoard();
	}
}
