package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {
	//Test strategy
	//getX,getY�����������ܹ���ȷ��������

	@Test
	public void testGetXY() {
		Position pos = new Position(0, 1);
		assertEquals(0, pos.getX());
		assertEquals(1, pos.getY());
	}
}
