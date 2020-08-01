package train;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarriageTest {

	// Test strategy
	// ����getID()
	// ���Եõ��ı���Ƿ��Ԥ�ڵ����
	// ����getType()
	// ���Եõ��������Ƿ��Ԥ�ڵ���ͬ
	// ����getSeats()
	// ���Եõ�����λ���Ƿ��Ԥ�ڵ����
	// ����getAge()
	// ���Եõ�����������Ƿ��Ԥ�ڵ����
	// ����equals()
	// ��������ͬһ���ᣬ�����᲻��ͬһ������
	
	@Test
	public void testGetID() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(1, carriage.getID());
	}

	@Test
	public void testGetType() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals("ErDengZuo", carriage.getType());
	}

	@Test
	public void testGetSeats() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(100, carriage.getSeats());
	}

	@Test
	public void testGetAge() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(2020, carriage.getManufactureYear());
	}

	@Test
	public void testEquals() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//��������ͬһ����
		Carriage same = new Carriage(1, "ErDengZuo", 100, 2020);
		assertTrue(carriage.equals(same));
		//�����᲻��ͬһ������
		Carriage different = new Carriage(2, "ErDengZuo", 100, 2020);
		assertFalse(carriage.equals(different));
	}

}
