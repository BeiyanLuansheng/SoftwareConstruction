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
	// manufactureYear > 0
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
		// ������ǰ���������׳��쳣
		if (id <= 0 || type == null || seats <= 0 || manufactureYear <= 0)
			throw new IllegalArgumentException("�������Ϸ�");
		this.id = id;
		this.type = type;
		this.seats = seats;
		this.manufactureYear = manufactureYear;
		checkRep();
	}

	private void checkRep() {
		assert this.id > 0;
		assert this.type != null;
		assert this.seats > 0;
		assert this.manufactureYear > 0;
	}

	/**
	 * ��ó���ı��
	 * 
	 * @return һ����ʾ�����ŵ�����
	 */
	public int getID() {
		checkRep();
		return id;
	}

	/**
	 * ��ó��������
	 * 
	 * @return һ����ʾ������ַ���
	 */
	public String getType() {
		checkRep();
		return type;
	}

	/**
	 * ��ó���Ķ�Ա��
	 * 
	 * @return һ����ʾ��Ա��������
	 */
	public int getSeats() {
		checkRep();
		return seats;
	}

	/**
	 * ��ó���ĳ������
	 * 
	 * @return һ����ʾ��ݵ�����
	 */
	public int getManufactureYear() {
		checkRep();
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
