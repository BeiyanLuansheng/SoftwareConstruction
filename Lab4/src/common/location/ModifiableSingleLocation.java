package common.location;

/**
 * ���޸ĵĵ���λ��
 */
public class ModifiableSingleLocation implements ModifiableSingleLocationEntry {

	private Location location;

	// Abstraction function:
	// AF(location)=һ����ʵ�е�λ��
	// Representation invariant:
	// location
	// Safety from rep exposure:
	// ���е���������˽�е�

	@Override
	public boolean setLocation(Location location) {
		// λ�ò���Ϊ��ʱ�׳��쳣
		if (location == null)
			throw new IllegalArgumentException("λ��Ϊ��");
		this.location = location;
		return true;
	}

	@Override
	public Location getLocation() {
		return location;
	}
}