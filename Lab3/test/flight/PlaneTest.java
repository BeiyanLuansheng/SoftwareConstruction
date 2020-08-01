package flight;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaneTest {

	// Test strategy
	// ����getID()
	// ���Եõ���ID�Ƿ��Ԥ�ڵ����
	// ����getType()
	// ���Եõ��������Ƿ��Ԥ�ڵ���ͬ
	// ����getSeats()
	// ���Եõ�����λ���Ƿ��Ԥ�ڵ����
	// ����getAge()
	// ���Եõ��Ļ����Ƿ��Ԥ�ڵ����
	// ����hashCode()
	// ���Եõ��Ĺ�ϣֵ�Ƿ��Ԥ�ڴ�����
	// ����equals()
	// ���ܷɻ���ͬһ�ܷɻ������ܷɻ�����ͬһ�ܷɻ�

	@Test
	public void testGetID() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("B0741", plane.getID());
	}

	@Test
	public void testGetType() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("A380", plane.getType());
	}

	@Test
	public void testGetSeats() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals(468, plane.getSeats());
	}

	@Test
	public void testGetAge() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals(1.1, plane.getAge(), 0.0);
	}
	
	@Test
	public void testHashCode() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("B0741".hashCode(), plane.hashCode());
	}

	@Test
	public void testEquals() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//ͬһ�ܷɻ�
		Plane same = new Plane("B0741", "A380", 468, 1.1);
		assertTrue(plane.equals(same));
		//����ͬһ�ܷɻ�
		Plane different = new Plane("N5375", "B757", 261, 3.1);
		assertFalse(plane.equals(different));

	}
}
