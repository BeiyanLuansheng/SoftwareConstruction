package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitingTest {

	// Test strategy
	// 测试state()
	// 测试返回的字符串和预期的状态是否相同
	// 测试nextState()
	// nextState为CANCELLED、nextState为ALLOCATED、nextState为其他
	// 测试isFinalState()
	// 测试是否返回false

	@Test
	public void testState() {
		State state = new Waiting();
		assertEquals("WAITING", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Waiting();
		//nextState为CANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextState为ALLOCATED
		assertEquals("ALLOCATED", state.nextState("ALLOCATED").state());
		//nextState为其他
		assertEquals("WAITING", state.nextState("RUNNING").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Waiting();
		assertFalse(state.isFinalState());
	}
}
