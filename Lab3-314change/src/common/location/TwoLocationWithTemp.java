package common.location;

public class TwoLocationWithTemp implements TwoLocationEntry {

	private final Location departure, arrival, temp;

	// Abstraction function:
	// AF(departure,temp,arrival)=��ʵ�д�departureλ��(��tempλ����ת)��arrivalλ��
	// Representation invariant:
	// departure!=null
	// arrival!=null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ��������λ�ã�����λ�ú͵���λ�ã���������תλ��
	 * 
	 * @param departure ����λ�ã��ǿ�
	 * @param temp      ��תλ�ã�null��ʾû��
	 * @param arrival   ����λ�ã��ǿ�
	 */
	public TwoLocationWithTemp(Location departure, Location temp, Location arrival) {
		this.departure = departure;
		this.arrival = arrival;
		this.temp = temp;
	}

	/**
	 * �����תλ��
	 * @return null��û����ת �ǿգ���תλ��
	 */
	public Location getTemp() {
		return temp;
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
	public boolean equals(Object twoLocationWithTemp) {
		if (twoLocationWithTemp instanceof TwoLocationWithTemp) {
			TwoLocationWithTemp loc = (TwoLocationWithTemp) twoLocationWithTemp;
			if (this.departure.equals(loc.departure) && this.arrival.equals(loc.arrival)
					&& ((this.temp == null && loc.temp == null) || this.temp.equals(loc.temp)))
				return true;
		}
		return false;
	}
}
