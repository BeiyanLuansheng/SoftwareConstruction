package common.location;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import org.junit.Rule;
import org.junit.Test;

public class TwoLocationTest extends TwoLocationEntryInstanceTest {

	// Test strategy
	// ���Թ��췽��
	// λ��Ϊ�գ�λ�ò�Ϊ��
	// ��λ����ͬ����λ�ò�ͬ
	// ����toString()
	// ���Է��ص��ַ����Ƿ�����������ͬ
	// ����equals()
	// ��ͬ������λ�ã�����λ�ò���ͬ

	@Override
	public TwoLocationEntry createTLE(Location departure, Location arrival) {
		return new TwoLocation(departure, arrival);
	}

	@Rule 
	public ExpectedException exception = ExpectedException.none();

	// tests for TwoLocation
	@Test
	public void testTwoLocation1(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("����Ϊ��");
		new TwoLocation(new Location("A", true), null);
	}
	
	@Test
	public void testTwoLocation2(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�����ص��Ŀ�ĵص���ͬ");
		new TwoLocation(new Location("place", true), new Location("place", true));
	}

	@Test
	public void testTwoLocation3(){
		new TwoLocation(new Location("place", true), new Location("place2", true));
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
