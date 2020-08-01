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
	// 两个位置不相同
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建两个位置，出发位置和到达位置
	 * 
	 * @param departure 出发位置，非空
	 * @param arrival   到达位置，非空，和出发位置不相同
	 */
	public TwoLocation(Location departure, Location arrival) {
		// 参数不符合前置条件，抛出异常
		if (departure == null || arrival == null)
			throw new IllegalArgumentException("参数为空");
		if (departure.equals(arrival))
			throw new IllegalArgumentException("出发地点和目的地点相同");
		this.departure = departure;
		this.arrival = arrival;
		checkRep();
	}

	/**
	 * 检查不变性
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