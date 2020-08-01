package common.location;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MultipleLocationTest extends MultipleLocationEntryInstanceTest {

	// Test strategy
	// 测试构造方法
	// 列表的位置少于两个
	// 列表的位置有重复

	@Override
	public MultipleLocationEntry createMLE(List<Location> locations) {
		return new MultipleLocation(locations);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testMultipleLocation1(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("列表中的位置少于两个");
		new MultipleLocation(new ArrayList<>());
	}

	@Test
	public void testMultipleLocation2(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("位置列表有重复位置");
		List<Location> list = new ArrayList<>();
		Location loc = new Location("place", true);
		list.add(loc);
		list.add(loc);
		new MultipleLocation(list);
	}
}
