package common.location;

/**
 * 一个immutable的类型，表示位置
 */
public class Location {
	private final String name;
	private final boolean sharable;
	private final double longitude;
	private final double latitude;

	// Abstraction function:
	// AF(name, sharable)=以name为名称，是否可共享为sharable的位置
	// AF(name, sharable, longitude,latitude)=以name为名称，
	// 是否可共享为sharable，东经longitude，北纬latitude的位置
	// Representation invariant:
	// longitude>=0 && longitude<=180
	// latitude>=0 && latitude<=90
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建一个位置
	 * 
	 * @param place    位置的名字,非空
	 * @param sharable 位置是否可共享，true为可共享，false为不可共享
	 */
	public Location(String place, boolean sharable) {
		if (place == null) // place参数不满足非空条件，抛出异常
			throw new IllegalArgumentException("地名为空");
		this.name = place;
		this.sharable = sharable;
		this.latitude = 0;
		this.longitude = 0;
		checkRep();
	}

	/**
	 * 创建一个位置
	 * 
	 * @param place     位置的名字，非空
	 * @param sharable  位置是否可共享，true为可共享，false为不可共享
	 * @param longitude 东经经度
	 * @param latitude  北纬纬度
	 */
	public Location(String place, boolean sharable, double longitude, double latitude) {
		// 参数不满足前置条件，抛出异常
		if (place == null)
			throw new IllegalArgumentException("地名为空");
		if (longitude < 0 || longitude > 180 || latitude < 0 || latitude > 90)
			throw new IllegalArgumentException("经纬度不合法");
		this.name = place;
		this.sharable = sharable;
		this.latitude = latitude;
		this.longitude = longitude;
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.name != null;
		assert this.longitude >= 0 && this.longitude <= 180;
		assert this.latitude >= 0 && this.latitude <= 90;
	}

	/**
	 * 获得位置的名字
	 * 
	 * @return 一个非空字符串，表示返回地名
	 */
	public String getName() {
		checkRep();
		return name;
	}

	/**
	 * 地点是否为可共享的
	 * 
	 * @return true 地点是可共享的 false 不可共享
	 */
	public boolean isSharable() {
		checkRep();
		return sharable;
	}

	/**
	 * 获得经度
	 * 
	 * @return 一个浮点数，表示东经经度
	 */
	public double getLongitude() {
		checkRep();
		return longitude;
	}

	/**
	 * 获得纬度
	 * 
	 * @return 一个浮点数，表示北纬纬度
	 */
	public double getLatitude() {
		checkRep();
		return latitude;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object location) {
		if (location instanceof Location) {
			Location loc = (Location) location;
			if (this.name.equals(loc.getName()) && this.sharable == loc.isSharable() && this.longitude == loc.longitude
					&& this.latitude == loc.latitude)
				return true;
		}
		return false;
	}

}
