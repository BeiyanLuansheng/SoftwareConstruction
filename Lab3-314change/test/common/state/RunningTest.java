package common.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class RunningTest {

	// Test strategy
	// ����state()
	// ���Է��ص��ַ�����Ԥ�ڵ�״̬�Ƿ���ͬ
	// ����nextState()
	// nextStateΪENDED��nextStateΪBLOCKED��nextStateΪ����
	// ����isFinalState()
	// �����Ƿ񷵻�false

	@Test
	public void testState() {
		State state = new Running();
		assertEquals("RUNNING", state.state());
	}

	@Test
	public void testNextState() {
		State state = new Running();
		//nextStateΪENDED
		assertEquals("ENDED", state.nextState("ENDED").state());
		//nextStateΪBLOCKED
		assertEquals("BLOCKED", state.nextState("BLOCKED").state());
		//nextStateΪ����
		assertEquals("RUNNING", state.nextState("ALLOCATED").state());
	}

	@Test
	public void testIsFinalState() {
		State state = new Running();
		assertFalse(state.isFinalState());
	}
}
