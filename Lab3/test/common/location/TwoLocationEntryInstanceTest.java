package common.location;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class TwoLocationEntryInstanceTest {

	// Test strategy
	// 测试getDeparture
	// 测试返回的位置是否和期望相同
	// 测试getArrival
	// 测试返回的位置是否和期望相同

	/**
	 * 创建一个表示两个位置的对象
	 * @param departure 出发的位置
	 * @param arrival 到达的的位置
	 * @return 一个表示两个位置的对象
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
