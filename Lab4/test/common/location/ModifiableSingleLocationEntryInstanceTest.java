package common.location;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import org.junit.Rule;
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

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//tests for setLocation()
	//��λ��
	@Test(expected = IllegalArgumentException.class)
	public void tesSetLocation() {
		ModifiableSingleLocationEntry msle = createMSLE();
		assertFalse(msle.setLocation(null));
	}
	
	//�ǿ�λ��
	@Test
	public void testSetLocation2() {
		ModifiableSingleLocationEntry msle = createMSLE();
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
