package flight;

/**
 * 一个immutable的类型，代表一架飞机
 */
public class Plane {

	private final String id;
	private final String type;
	private final int seats;
	private final double age;

	// Abstraction function:
	// AF(id, type, seats, age)=以id为编号，type类型，有seats个座位，机龄为age的一架飞机
	// Representation invariant:
	// id != null
	// type != null
	// seats > 0
	// age >= 0
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建一架飞机
	 * 
	 * @param id    飞机的编号，不为null
	 * @param type  飞机的类型，不为null
	 * @param seats 飞机的座位数，大于0
	 * @param age   飞机的机龄，非负
	 */
	public Plane(String id, String type, int seats, double age) {
		this.id = id;
		this.type = type;
		this.seats = seats;
		this.age = age;
	}

	/**
	 * 获得飞机的编号
	 * 
	 * @return 一个表示飞机编号的字符串
	 */
	public String getID() {
		return id;
	}

	/**
	 * 获得飞机的类型
	 * 
	 * @return 一个表示类型的字符串
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获得飞机的座位数
	 * 
	 * @return 一个大于零的整数
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * 获得飞机的机龄
	 * 
	 * @return 一个非负的小数
	 */
	public double getAge() {
		return age;
	}

	@Override
	public int hashCode() {
		return getID().hashCode();
	}

	@Override
	public boolean equals(Object plane) {
		if (plane instanceof Plane) {
			Plane p = (Plane) plane;
			if (this.id.equals(p.id) && this.type.equals(p.type) && this.seats == p.seats && this.age - p.age < 0.1)
				return true;
		}
		return false;
	}
}
