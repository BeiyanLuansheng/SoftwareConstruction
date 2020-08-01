package common.location;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public abstract class MultipleLocationEntryInstanceTest {

	// Test strategy
	// 测试getLocations()
	// 测试返回的位置列表与期望是否相等

	/**
	 * 创建一个位置组
	 * @param locations 待添加的位置列表
	 * @return 封装后的位置列表
	 */
	public abstract MultipleLocationEntry createMLE(List<Location> locations);

	@Test
	public void testGetLocations() {
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("place1", true));
		locations.add(new Location("place2", true));
		locations.add(new Location("place3", true));
		MultipleLocationEntry mle = createMLE(locations);
		assertEquals(locations, mle.getLocations());
	}

}
