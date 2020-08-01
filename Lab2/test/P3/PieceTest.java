package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {
	//Test strategy
	//getType方法：type为空(围棋)，type不为空(象棋)
	//getPlayer方法：测试返回的棋手是否正确
	
	@Test
	public void testGetType() {
		Player player = new Player("White");
		//空的type，围棋棋子
		Piece emptyPiece = new Piece(null, player);
		assertEquals(null, emptyPiece.getType());
		//type不为空，象棋棋子
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
