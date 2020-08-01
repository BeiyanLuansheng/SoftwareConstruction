package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class EndedTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪ�����ַ���
	// ����isFinalState()
	// �����Ƿ񷵻�true

	@Test
	public void testState() {
		State state = new Ended();
		assertEquals("ENDED", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Ended();
		//nextStateΪRUNNING
		assertEquals("ENDED", state.nextState("RUNNING").state());
		//nextStateΪCANCELLED
		assertEquals("ENDED", state.nextState("CANCELLED").state());
		//nextStateΪBLOCKED
		assertEquals("ENDED", state.nextState("BLOCKED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Ended();
		assertTrue(state.isFinalState());
	}
}
