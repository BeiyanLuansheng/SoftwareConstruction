package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	//Test strategy
	//getName�����������ܹ���ȷ������������
	
	@Test
	public void testGetName() {
		Player player = new Player("White");
		assertEquals("White", player.getName());
	}
}
