package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockedTest {

	// Test strategy
	// 测试state()
	// 测试返回的字符串和预期的状态是否相同
	// 测试nextState()
	// nextState为RUNNING、nextState为CANCELLED、nextState为其他
	// 测试isFinalState()
	// 测试是否返回false

	@Test
	public void testState() {
		State state = new Blocked();
		assertEquals("BLOCKED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Blocked();
		//nextState为RUNNING
		assertEquals("RUNNING", state.nextState("RUNNING").state());
		//nextState为CANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextState为其他
		assertEquals("BLOCKED", state.nextState("BLOCKED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Blocked();
		assertFalse(state.isFinalState());
	}
}
