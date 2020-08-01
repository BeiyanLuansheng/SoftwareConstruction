package common.location;

/**
 * 一个immutable类型，表示出发和抵达位置
 */
public class TwoLocation implements TwoLocationEntry {

	private final Location departure, arrival;

	// Abstraction function:
	// AF(departure,arrival)=现实中从departure位置到arrival位置
	// Representation invariant:
	// departure!=null
	// arrival!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建两个位置，出发位置和到达位置
	 * 
	 * @param departure 出发位置，非空
	 * @param arrival   到达位置，非空
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