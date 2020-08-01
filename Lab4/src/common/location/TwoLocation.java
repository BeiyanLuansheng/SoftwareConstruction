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
	// ����λ�ò���ͬ
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ��������λ�ã�����λ�ú͵���λ��
	 * 
	 * @param departure ����λ�ã��ǿ�
	 * @param arrival   ����λ�ã��ǿգ��ͳ���λ�ò���ͬ
	 */
	public TwoLocation(Location departure, Location arrival) {
		// ����������ǰ���������׳��쳣
		if (departure == null || arrival == null)
			throw new IllegalArgumentException("����Ϊ��");
		if (departure.equals(arrival))
			throw new IllegalArgumentException("�����ص��Ŀ�ĵص���ͬ");
		this.departure = departure;
		this.arrival = arrival;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.departure != null;
		assert this.arrival != null;
		assert !this.departure.equals(this.arrival);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrival == null) ? 0 : arrival.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		return result;
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