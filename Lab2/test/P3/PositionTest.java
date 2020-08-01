package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {
	//Test strategy
	//getX,getY方法：测试能够正确返回坐标

	@Test
	public void testGetXY() {
		Position pos = new Position(0, 1);
		assertEquals(0, pos.getX());
		assertEquals(1, pos.getY());
	}
}
