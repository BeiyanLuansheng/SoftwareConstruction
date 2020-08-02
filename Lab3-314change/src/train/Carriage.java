package train;

/**
 * 一个immutable的类型，代表一个火车车厢
 */
public class Carriage {

	private final int id;
	private final String type;
	private final int seats;
	private final int manufactureYear;

	// Abstraction function:
	// AF(id,type,seats,manufactureYear)=以id为编号，type类型，
	// 有seats个座位，出年份为manufactureYear的一个火车车厢
	// Representation invariant:
	// id > 0
	// type != null
	// seats > 0
	// age >= 0
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建一个火车车厢
	 * 
	 * @param id              非负的车厢编号
	 * @param type            非空的车厢类型
	 * @param seats           定员数，大于0
	 * @param manufactureYear 出场年份
	 */
	public Carriage(int id, String type, int seats, int manufactureYear) {
		this.id = id;
		this.type = type;
		this.seats = seats;
		this.manufactureYear = manufactureYear;
	}

	/**
	 * 获得车厢的编号
	 * 
	 * @return 一个表示车厢编号的整数
	 */
	public int getID() {
		return id;
	}

	/**
	 * 获得车厢的类型
	 * 
	 * @return 一个表示车厢的字符串
	 */
	public String getType() {
		return type;
	}

	/**
	 * 获得车厢的定员数
	 * 
	 * @return 一个表示定员数的整数
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * 获得车厢的出场年份
	 * 
	 * @return 一个表示年份的整数
	 */
	public int getManufactureYear() {
		return manufactureYear;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object carriage) {
		if (carriage instanceof Carriage) {
			Carriage c = (Carriage) carriage;
			if (c.id == this.id && c.type.equals(this.type) && c.seats == this.seats
					&& c.manufactureYear == this.manufactureYear)
				return true;
		}
		return false;
	}
}
