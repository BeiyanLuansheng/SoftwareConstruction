package common.time;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class TimeslotTest {

	// Test strategy
	// 测试getStartTime()和getEndTime()
	// 测试得到的时间是否与期望的时间相同
	// 测试getStartTimeString()和getEndTimeString()
	// 测试得到的时间字符串是否与期望的时间相同
	// 测试isBefore()
	// 两个时间段相交，两个时间段不相交
	// 测试isAfter()
	// 两个时间段相交，两个时间段不相交
	// 测试equals()
	// 两个时间段的表示的时间相等，表示的时间不等

	@Test
	public void testGetStartTimeAndGetEndtime() {
		LocalDateTime start = LocalDateTime.of(2020, 04, 27, 14, 00);
		LocalDateTime end = LocalDateTime.of(2020, 04, 27, 16, 00);
		Timeslot time = new Timeslot("2020-04-27 14:00", "2020-04-27 16:00");
		assertEquals(start, time.getStartTime());
		assertEquals(end, time.getEndTime());
	}
	
	@Test
	public void testGetStartTimeStringAndGetEndtimeString() {
		String start = "2020-04-27 14:00";
		String end = "2020-04-27 16:00";
		Timeslot time = new Timeslot(start, end);
		assertEquals(start, time.getStartTimeString());
		assertEquals(end, time.getEndTimeString());
	}

	@Test
	public void testIsBefore() {
		Timeslot time1 = new Timeslot("2020-04-27 14:00", "2020-04-27 16:00");
		//两个时间段相交
		Timeslot time2 = new Timeslot("2020-04-27 15:00", "2020-04-27 17:00");
		assertFalse(time1.isBefore(time2));
		assertFalse(time2.isBefore(time1));
		//两个时间段不相交
		Timeslot time3 = new Timeslot("2020-04-27 16:00", "2020-04-27 17:00");
		assertTrue(time1.isBefore(time3));
		assertFalse(time3.isBefore(time1));
	}

	@Test
	public void testIsAfter() {
		Timeslot time1 = new Timeslot("2020-04-27 14:00", "2020-04-27 16:00");
		//两个时间段相交
		Timeslot time2 = new Timeslot("2020-04-27 15:00", "2020-04-27 17:00");
		assertFalse(time1.isAfter(time2));
		assertFalse(time2.isAfter(time1));
		//开始时间不等
		Timeslot time3 = new Timeslot("2020-04-27 13:00", "2020-04-27 14:00");
		assertTrue(time1.isAfter(time3));
		assertFalse(time3.isAfter(time1));
	}

	@Test
	public void testEquals() {
		Timeslot time1 = new Timeslot("2020-04-27 14:00", "2020-04-27 15:00");
		//时间相等
		Timeslot time2 = new Timeslot("2020-04-27 14:00", "2020-04-27 15:00");
		assertTrue(time1.equals(time2));
		//时间不等
		Timeslot time3 = new Timeslot("2020-04-27 13:00", "2020-04-27 15:00");
		assertFalse(time1.equals(time3));
	}
}
