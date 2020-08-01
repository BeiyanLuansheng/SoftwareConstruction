package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class AllocatedTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪRUNNING��nextStateΪCANCELLED��nextStateΪ����
	// ����isFinalState()
	// �����Ƿ񷵻�false

	@Test
	public void testState() {
		State state = new Allocated();
		assertEquals("ALLOCATED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Allocated();
		//nextStateΪRUNNING
		assertEquals("RUNNING", state.nextState("RUNNING").state());
		//nextStateΪCANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextStateΪ����
		assertEquals("ALLOCATED", state.nextState("ALLOCATED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Allocated();
		assertFalse(state.isFinalState());
	}
}
