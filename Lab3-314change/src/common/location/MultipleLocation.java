package common.location;

import java.util.Collections;
import java.util.List;

/**
 * 一个immutable类型， 表示多个不可更改的位置
 */
public class MultipleLocation implements MultipleLocationEntry {

	private final List<Location> locations;

	// Abstraction function:
	// AF(locations)=以locations中的对象为位置且按顺序排列的一组位置
	// Representation invariant:
	// locations!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 返回位置列表时使用Collections.unmodifiableList()返回一个不可修改的列表

	/**
	 * 创建一组途径的位置
	 * 
	 * @param locations 途径位置的列表，非空
	 */
	public MultipleLocation(List<Location> locations) {
		this.locations = locations;
	}

	@Override
	public List<Location> getLocations() {
		return Collections.unmodifiableList(locations);
	}

}
