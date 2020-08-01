package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class ModifiableSingleLocationEntryInstanceTest {

	// Test strategy
	// 测试setLocation()
	// 位置为空，位置不为空
	// 测试getLocation()
	// 测试返回的位置是否和预期相同

	/**
	 * 创建一个可变的单个位置的对象
	 * @return 一个可变的单个位置的对象
	 */
	public abstract ModifiableSingleLocationEntry createMSLE();

	//tests for setLocation()
	@Test
	public void tesSetLocation() {
		ModifiableSingleLocationEntry msle = createMSLE();
		//空位置
		assertFalse(msle.setLocation(null));
		//非空位置
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
