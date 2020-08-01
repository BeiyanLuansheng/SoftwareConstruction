package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class BlockedTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪRUNNING��nextStateΪCANCELLED��nextStateΪ����
	// ����isFinalState()
	// �����Ƿ񷵻�false

	@Test
	public void testState() {
		State state = new Blocked();
		assertEquals("BLOCKED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Blocked();
		//nextStateΪRUNNING
		assertEquals("RUNNING", state.nextState("RUNNING").state());
		//nextStateΪCANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextStateΪ����
		assertEquals("BLOCKED", state.nextState("BLOCKED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Blocked();
		assertFalse(state.isFinalState());
	}
}
