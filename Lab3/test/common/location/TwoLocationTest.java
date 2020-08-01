package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public class TwoLocationTest extends TwoLocationEntryInstanceTest {

	// Test strategy
	// ����toString()
	// ���Է��ص��ַ����Ƿ�����������ͬ
	// ����equals()
	// ��ͬ������λ�ã�����λ�ò���ͬ

	@Override
	public TwoLocationEntry createTLE(Location departure, Location arrival) {
		return new TwoLocation(departure, arrival);
	}

	//tests for toString()
	@Test
	public void testToString() {
		Location departure = new Location("place1", true);
		Location arrival = new Location("place2", true);
		TwoLocationEntry tle = createTLE(departure, arrival);
		assertEquals("place1->place2", tle.toString());
	}

	//tests for equals()
	@Test
	public void testEquals() {
		Location departure1 = new Location("place1", true);
		Location arrival1 = new Location("place2", true);
		TwoLocationEntry tle1 = createTLE(departure1, arrival1);
		//��ͬ������λ��
		TwoLocationEntry tle2 = createTLE(departure1, arrival1);
		assertTrue(tle1.equals(tle2));
		//����λ�ò���ͬ
		Location departure3 = new Location("place3", true);
		Location arrival3 = new Location("place4", true);
		TwoLocationEntry tle3 = createTLE(departure3, arrival3);
		assertFalse(tle1.equals(tle3));
	}
}
