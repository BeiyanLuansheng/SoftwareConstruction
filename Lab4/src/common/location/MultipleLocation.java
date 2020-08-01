package common.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一个immutable类型， 表示多个不可更改的位置
 */
public class MultipleLocation implements MultipleLocationEntry {

	private final List<Location> locations;

	// Abstraction function:
	// AF(locations)=以locations中的对象为位置且按顺序排列的一组位置
	// Representation invariant:
	// locations!=null
	// locations中不能有重复的车站
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 返回位置列表时使用Collections.unmodifiableList()返回一个不可修改的列表

	/**
	 * 创建一组途径的位置
	 * 
	 * @param locations 途径位置的列表，非空，不能有重复的车站
	 */
	public MultipleLocation(List<Location> locations) {
		// 不满足前置条件，抛出异常
		if (locations == null || locations.size() < 2)
			throw new IllegalArgumentException("列表中的位置少于两个");
		Set<Location> locSet = new HashSet<>();
		for (Location loc : locations) {
			if (locSet.contains(loc))
				throw new IllegalArgumentException("位置列表有重复位置");
			locSet.add(loc);
		}
		this.locations = locations;
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.locations != null;
		assert this.locations.size() >= 2;
		Set<Location> locSet = new HashSet<>();
		for (Location loc : this.locations) {
			if (locSet.contains(loc))
				assert false;
			locSet.add(loc);
		}
	}

	@Override
	public List<Location> getLocations() {
		checkRep();
		return Collections.unmodifiableList(locations);
	}

}
