package common.time;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PresetSingleTimeslotEntryInstanceTest {

	// Test strategy
	// 测试getTImeslot()
	// 测试返回的时间表是否和预期的相等

	/**
	 * 创建一个有单个时间段的时刻表
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 一个有单个时间段的时刻表
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
