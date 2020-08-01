package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitingTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪCANCELLED��nextStateΪALLOCATED��nextStateΪ����
	// ����isFinalState()
	// �����Ƿ񷵻�false

	@Test
	public void testState() {
		State state = new Waiting();
		assertEquals("WAITING", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Waiting();
		//nextStateΪCANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextStateΪALLOCATED
		assertEquals("ALLOCATED", state.nextState("ALLOCATED").state());
		//nextStateΪ����
		assertEquals("WAITING", state.nextState("RUNNING").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Waiting();
		assertFalse(state.isFinalState());
	}
}
