package common.time;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PresetSingleTimeslotEntryInstanceTest {

	// Test strategy
	// ����getTImeslot()
	// ���Է��ص�ʱ����Ƿ��Ԥ�ڵ����

	/**
	 * ����һ���е���ʱ��ε�ʱ�̱�
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @return һ���е���ʱ��ε�ʱ�̱�
	 */
	public abstract PresetSingleTimeslotEntry createPSTE(String start, String end);

	@Test
	public void testGetTImeslot() {
		String start = "2020-04-27 14:00";
		String end = "2020-04-27 15:00";
		PresetSingleTimeslotEntry pste = createPSTE(start, end);
		assertEquals(new Timeslot(start, end), pste.getTimeslot());
	}

}
