package train;

/**
 * һ��immutable�����ͣ�����һ���𳵳���
 */
public class Carriage {

	private final int id;
	private final String type;
	private final int seats;
	private final int manufactureYear;

	// Abstraction function:
	// AF(id,type,seats,manufactureYear)=��idΪ��ţ�type���ͣ�
	// ��seats����λ�������ΪmanufactureYear��һ���𳵳���
	// Representation invariant:
	// id > 0
	// type != null
	// seats > 0
	// age >= 0
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ����һ���𳵳���
	 * 
	 * @param id              �Ǹ��ĳ�����
	 * @param type            �ǿյĳ�������
	 * @param seats           ��Ա��������0
	 * @param manufactureYear �������
	 */
	public Carriage(int id, String type, int seats, int manufactureYear) {
		this.id = id;
		this.type = type;
		this.seats = seats;
		this.manufactureYear = manufactureYear;
	}

	/**
	 * ��ó���ı��
	 * 
	 * @return һ����ʾ�����ŵ�����
	 */
	public int getID() {
		return id;
	}

	/**
	 * ��ó��������
	 * 
	 * @return һ����ʾ������ַ���
	 */
	public String getType() {
		return type;
	}

	/**
	 * ��ó���Ķ�Ա��
	 * 
	 * @return һ����ʾ��Ա��������
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * ��ó���ĳ������
	 * 
	 * @return һ����ʾ��ݵ�����
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
