package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {
	//Test strategy
	//getType������typeΪ��(Χ��)��type��Ϊ��(����)
	//getPlayer���������Է��ص������Ƿ���ȷ
	
	@Test
	public void testGetType() {
		Player player = new Player("White");
		//�յ�type��Χ������
		Piece emptyPiece = new Piece(null, player);
		assertEquals(null, emptyPiece.getType());
		//type��Ϊ�գ���������
		Piece piece = new Piece("King", player);
		assertEquals("King", piece.getType());
	}
	
	@Test
	public void testGetPalyer() {
		Player player = new Player("White");
		Piece piece = new Piece("King", player);
		assertEquals(player, piece.getPlayer());
	}
}
