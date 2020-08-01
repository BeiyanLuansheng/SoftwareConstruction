package P3;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BoardTest {

	// Test strategy
	// put���������ںϷ��Ŀ���λ��
	// take�������úϷ�λ��������
	// getBoard�����������̣��ǿ�����
	// get�����������ӷ֣������ӣ�������
	
	
	Player white = new Player("White");
    Player black = new Player("Black");
	
	@Test
	public void testPut() {
		Board board = new Board(18, white, black);
		Piece piece = new Piece(null, white);
		Position position = new Position(1, 1);
		board.put(position, piece);
		assertEquals(piece, board.get(position));
	}
	
	@Test
	public void testTake() {
		Board board = new Board(18, white, black);
		Piece piece = new Piece(null, white);
		Position position = new Position(1, 1);
		board.put(position, piece);
		assertEquals(piece, board.get(position));
		board.take(position);
		assertEquals(null, board.get(position));
	}
	
	@Test
	public void testGetBoard() {
		//������
		Board board = new Board(18, white, black);
		Map<Position, Piece> map = new HashMap<>();
		for(int i=0; i<=18; i++)
			for(int j=0; j<=18; j++) {
				map.put(new Position(i, j), null);
			}
		assertEquals(map, board.getBoard());

		//�ǿ�����
		Position position1 = new Position(0, 0);
		Position position2 = new Position(2, 5);
		Position position3 = new Position(18, 18);
		Piece piece1 = new Piece(null, white);
		Piece piece2 = new Piece(null, black);
		Piece piece3 = new Piece(null, white);
		board.put(position1, piece1);
		board.put(position2, piece2);
		board.put(position3, piece3);
		for(Position p:map.keySet()) {
			if(p.equals(position1))
				map.put(p, piece1);
			else if(p.equals(position2))
				map.put(p, piece2);
			else if(p.equals(position3))
				map.put(p, piece3);
		}
		assertEquals(map, board.getBoard());
	}
	
	@Test
	public void testGet() {
		Board board = new Board(8, white, black);
		//����λ��
		assertEquals(null, board.get(new Position(0, 2)));
		//�ǿ���λ��
		assertEquals(new Piece("Rook", white), board.get(new Position(0, 0)));
	}
}
