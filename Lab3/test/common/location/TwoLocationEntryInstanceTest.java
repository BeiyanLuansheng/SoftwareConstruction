package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class TwoLocationEntryInstanceTest {

	// Test strategy
	// ����getDeparture
	// ���Է��ص�λ���Ƿ��������ͬ
	// ����getArrival
	// ���Է��ص�λ���Ƿ��������ͬ

	/**
	 * ����һ����ʾ����λ�õĶ���
	 * @param departure ������λ��
	 * @param arrival ����ĵ�λ��
	 * @return һ����ʾ����λ�õĶ���
	 */
	public abstract TwoLocationEntry createTLE(Location departure, Location arrival);

	@Test
	public void testGetDeparture() {
		Location departure = new Location("place1", true);
		Location arrival = new Location("place2", true);
		TwoLocationEntry tle = createTLE(departure, arrival);
		assertEquals(departure, tle.getDeparture());
	}

	@Test
	public void testGetArrival() {
		Location departure = new Location("place1", true);
		Location arrival = new Location("place2", true);
		TwoLocationEntry tle = createTLE(departure, arrival);
		assertEquals(arrival, tle.getArrival());
	}
}
