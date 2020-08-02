package common.location;

/**
 * 可修改的单个位置
 */
public class ModifiableSingleLocation implements ModifiableSingleLocationEntry {

	private Location location;

	// Abstraction function:
	// AF(location)=一个现实中的位置
	// Representation invariant:
	// location!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的

	@Override
	public boolean setLocation(Location location) {
		if (location == null)
			return false;
		this.location = location;
		return true;
	}

	@Override
	public Location getLocation() {
		return location;
	}
}