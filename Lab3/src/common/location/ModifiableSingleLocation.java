package common.location;

/**
 * ���޸ĵĵ���λ��
 */
public class ModifiableSingleLocation implements ModifiableSingleLocationEntry {

	private Location location;

	// Abstraction function:
	// AF(location)=һ����ʵ�е�λ��
	// Representation invariant:
	// location!=null
	// Safety from rep exposure:
	// ���е���������˽�е�

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