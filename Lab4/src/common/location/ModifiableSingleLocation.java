package common.location;

/**
 * 可修改的单个位置
 */
public class ModifiableSingleLocation implements ModifiableSingleLocationEntry {

	private Location location;

	// Abstraction function:
	// AF(location)=一个现实中的位置
	// Representation invariant:
	// location
	// Safety from rep exposure:
	// 所有的数据域都是私有的

	@Override
	public boolean setLocation(Location location) {
		// 位置参数为空时抛出异常
		if (location == null)
			throw new IllegalArgumentException("位置为空");
		this.location = location;
		return true;
	}

	@Override
	public Location getLocation() {
		return location;
	}
}