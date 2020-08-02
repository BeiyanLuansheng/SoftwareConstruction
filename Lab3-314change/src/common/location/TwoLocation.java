package common.location;

/**
 * һ��immutable���ͣ���ʾ�����͵ִ�λ��
 */
public class TwoLocation implements TwoLocationEntry {

	private final Location departure, arrival;

	// Abstraction function:
	// AF(departure,arrival)=��ʵ�д�departureλ�õ�arrivalλ��
	// Representation invariant:
	// departure!=null
	// arrival!=null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ��������λ�ã�����λ�ú͵���λ��
	 * 
	 * @param departure ����λ�ã��ǿ�
	 * @param arrival   ����λ�ã��ǿ�
	 */
	public TwoLocation(Location departure, Location arrival) {
		this.departure = departure;
		this.arrival = arrival;
	}

	@Override
	public Location getDeparture() {
		return departure;
	}

	@Override
	public Location getArrival() {
		return arrival;
	}

	@Override
	public String toString() {
		return departure.getName() + "->" + arrival.getName();
	}

	@Override
	public boolean equals(Object twoLocation) {
		if (twoLocation instanceof TwoLocation) {
			TwoLocation loc = (TwoLocation) twoLocation;
			if (this.departure.equals(loc.departure) && this.arrival.equals(loc.arrival))
				return true;
		}
		return false;
	}
}