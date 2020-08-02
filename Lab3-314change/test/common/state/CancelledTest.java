package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class CancelledTest {

	// Test strategy
	// 测试state()
	// 测试返回的字符串和预期的状态是否相同
	// 测试nextState()
	// nextState为任意字符串
	// 测试isFinalState()
	// 测试是否返回true

	@Test
	public void testState() {
		State state = new Cancelled();
		assertEquals("CANCELLED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Cancelled();
		//nextState为RUNNING
		assertEquals("CANCELLED", state.nextState("RUNNING").state());
		//nextState为CANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextState为BLOCKED
		assertEquals("CANCELLED", state.nextState("BLOCKED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Cancelled();
		assertTrue(state.isFinalState());
	}
}
