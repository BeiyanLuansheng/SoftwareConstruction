package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	//Test strategy
	//getName方法：测试能够正确返回棋手姓名
	
	@Test
	public void testGetName() {
		Player player = new Player("White");
		assertEquals("White", player.getName());
	}
}
