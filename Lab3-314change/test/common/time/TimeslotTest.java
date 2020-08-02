package common.time;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class TimeslotTest {

	// Test strategy
	// ����getStartTime()��getEndTime()
	// ���Եõ���ʱ���Ƿ���������ʱ����ͬ
	// ����getStartTimeString()��getEndTimeString()
	// ���Եõ���ʱ���ַ����Ƿ���������ʱ����ͬ
	// ����isBefore()
	// ����ʱ����ཻ������ʱ��β��ཻ
	// ����isAfter()
	// ����ʱ����ཻ������ʱ��β��ཻ
	// ����equals()
	// ����ʱ��εı�ʾ��ʱ����ȣ���ʾ��ʱ�䲻��

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
		//����ʱ����ཻ
		Timeslot time2 = new Timeslot("2020-04-27 15:00", "2020-04-27 17:00");
		assertFalse(time1.isBefore(time2));
		assertFalse(time2.isBefore(time1));
		//����ʱ��β��ཻ
		Timeslot time3 = new Timeslot("2020-04-27 16:00", "2020-04-27 17:00");
		assertTrue(time1.isBefore(time3));
		assertFalse(time3.isBefore(time1));
	}

	@Test
	public void testIsAfter() {
		Timeslot time1 = new Timeslot("2020-04-27 14:00", "2020-04-27 16:00");
		//����ʱ����ཻ
		Timeslot time2 = new Timeslot("2020-04-27 15:00", "2020-04-27 17:00");
		assertFalse(time1.isAfter(time2));
		assertFalse(time2.isAfter(time1));
		//��ʼʱ�䲻��
		Timeslot time3 = new Timeslot("2020-04-27 13:00", "2020-04-27 14:00");
		assertTrue(time1.isAfter(time3));
		assertFalse(time3.isAfter(time1));
	}

	@Test
	public void testEquals() {
		Timeslot time1 = new Timeslot("2020-04-27 14:00", "2020-04-27 15:00");
		//ʱ�����
		Timeslot time2 = new Timeslot("2020-04-27 14:00", "2020-04-27 15:00");
		assertTrue(time1.equals(time2));
		//ʱ�䲻��
		Timeslot time3 = new Timeslot("2020-04-27 13:00", "2020-04-27 15:00");
		assertFalse(time1.equals(time3));
	}
}
