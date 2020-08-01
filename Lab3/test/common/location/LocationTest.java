package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

	// Test strategy
	// 测试getName()
	// 测试返回的名字与期望是否相同
	// 测试isSharable()
	// 测试返回的可共享性与期望是否相同
	// 测试getLongitude()
	// 测试返回的经度与期望是否相同
	// 测试getLatitude()
	// 测试返回的纬度与期望是否相同
	// 测试hashCode()
	// 测试返回的哈希值与期望是否相同
	// 测试equals()
	// 两位置相同，两位置不同

	//tests for getName()
	@Test
	public void testGetName() {
		Location loc = new Location("place", true);
		assertEquals("place", loc.getName());
	}

	//tests for isSharable()
	@Test
	public void testIsSharable() {
		Location loc = new Location("place", true);
		assertTrue(loc.isSharable());
	}

	//tests for getLongitude()
	@Test
	public void testGetLongitude() {
		Location loc = new Location("place", true, 100, 50);
		assertEquals(100, loc.getLongitude(), 0.0);
	}

	//tests for getLatitude()
	@Test
	public void testGetLatitude() {
		Location loc = new Location("place", true, 100, 50);
		assertEquals(50, loc.getLatitude(), 0.0);
	}

	//tests for hashCode()
	@Test
	public void testHashCode() {
		Location loc = new Location("place", true);
		assertEquals("place".hashCode(), loc.hashCode());
	}

	//tests for equals()
	@Test
	public void testEquals() {
		Location loc1 = new Location("place1", true);
		//两位置相等
		Location loc2 = new Location("place1", true);
		assertTrue(loc1.equals(loc2));
		//两位置不相等
		Location loc3 = new Location("place3", true);
		assertFalse(loc1.equals(loc3));
	}
}
