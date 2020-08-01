package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class RunningTest {

	// Test strategy
	// 测试state()
	// 测试返回的字符串和预期的状态是否相同
	// 测试nextState()
	// nextState为ENDED、nextState为BLOCKED、nextState为其他
	// 测试isFinalState()
	// 测试是否返回false

	@Test
	public void testState() {
		State state = new Running();
		assertEquals("RUNNING", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Running();
		//nextState为ENDED
		assertEquals("ENDED", state.nextState("ENDED").state());
		//nextState为BLOCKED
		assertEquals("BLOCKED", state.nextState("BLOCKED").state());
		//nextState为其他
		assertEquals("RUNNING", state.nextState("ALLOCATED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Running();
		assertFalse(state.isFinalState());
	}
}
