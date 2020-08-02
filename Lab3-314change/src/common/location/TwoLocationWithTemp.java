package common.location;

public class TwoLocationWithTemp implements TwoLocationEntry {

	private final Location departure, arrival, temp;

	// Abstraction function:
	// AF(departure,temp,arrival)=现实中从departure位置(经temp位置中转)到arrival位置
	// Representation invariant:
	// departure!=null
	// arrival!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建两个位置，出发位置和到达位置，可以有中转位置
	 * 
	 * @param departure 出发位置，非空
	 * @param temp      中转位置，null表示没有
	 * @param arrival   到达位置，非空
	 */
	public TwoLocationWithTemp(Location departure, Location temp, Location arrival) {
		this.departure = departure;
		this.arrival = arrival;
		this.temp = temp;
	}

	/**
	 * 获得中转位置
	 * @return null，没有中转 非空，中转位置
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
