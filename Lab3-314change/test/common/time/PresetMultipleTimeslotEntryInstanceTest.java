package common.time;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public abstract class PresetMultipleTimeslotEntryInstanceTest {

	// Test strategy
	// 测试getTImeslot()
	// 测试返回的时间表是否和预期的相等

	/**
	 * 创建一个有多个时间段的时刻表
	 * @param times 时间表，有偶数个时间
	 * @return 一个有多个时间段的时刻表
	 */
	public abstract PresetMultipleTimeslotEntry createPMTE(List<String> times);

	@Test
	public void testGetTimeslot() {
		List<String> times = new ArrayList<>();
		times.add("2020-04-27 14:00");
		times.add("2020-04-27 15:00");
		times.add("2020-04-27 16:00");
		times.add("2020-04-27 17:00");
		List<Timeslot> timeslots = new ArrayList<>();
		for(int i=0; i<times.size(); i+=2)
			timeslots.add(new Timeslot(times.get(i), times.get(i+1)));
		PresetMultipleTimeslotEntry pmte = createPMTE(times);
		assertEquals(timeslots, pmte.getTimeslot());
	}

}
