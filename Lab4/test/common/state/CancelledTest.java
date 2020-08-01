package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class CancelledTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪ�����ַ���
	// ����isFinalState()
	// �����Ƿ񷵻�true

	@Test
	public void testState() {
		State state = new Cancelled();
		assertEquals("CANCELLED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Cancelled();
		//nextStateΪRUNNING
		assertEquals("CANCELLED", state.nextState("RUNNING").state());
		//nextStateΪCANCELLED
		assertEquals("CANCELLED", state.nextState("CANCELLED").state());
		//nextStateΪBLOCKED
		assertEquals("CANCELLED", state.nextState("BLOCKED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Cancelled();
		assertTrue(state.isFinalState());
	}
}
