package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class ModifiableSingleLocationEntryInstanceTest {

	// Test strategy
	// ����setLocation()
	// λ��Ϊ�գ�λ�ò�Ϊ��
	// ����getLocation()
	// ���Է��ص�λ���Ƿ��Ԥ����ͬ

	/**
	 * ����һ���ɱ�ĵ���λ�õĶ���
	 * @return һ���ɱ�ĵ���λ�õĶ���
	 */
	public abstract ModifiableSingleLocationEntry createMSLE();

	//tests for setLocation()
	@Test
	public void tesSetLocation() {
		ModifiableSingleLocationEntry msle = createMSLE();
		//��λ��
		assertFalse(msle.setLocation(null));
		//�ǿ�λ��
		Location loc = new Location("place", false);
		assertTrue(msle.setLocation(loc));
	}

	//tests for getLocation()
	@Test
	public void testGetLocation() {
		ModifiableSingleLocationEntry msle = createMSLE();
		assertNull(msle.getLocation());
		Location loc = new Location("place", false);
		msle.setLocation(loc);
		assertEquals(loc, msle.getLocation());
	}
}
