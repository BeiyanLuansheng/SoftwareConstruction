package flight;

/**
 * һ��immutable�����ͣ�����һ�ܷɻ�
 */
public class Plane {

	private final String id;
	private final String type;
	private final int seats;
	private final double age;

	// Abstraction function:
	// AF(id, type, seats, age)=��idΪ��ţ�type���ͣ���seats����λ������Ϊage��һ�ܷɻ�
	// Representation invariant:
	// id != null
	// type != null
	// seats > 0
	// age >= 0
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ����һ�ܷɻ�
	 * 
	 * @param id    �ɻ��ı�ţ���Ϊnull
	 * @param type  �ɻ������ͣ���Ϊnull
	 * @param seats �ɻ�����λ��������0
	 * @param age   �ɻ��Ļ��䣬�Ǹ�
	 */
	public Plane(String id, String type, int seats, double age) {
		this.id = id;
		this.type = type;
		this.seats = seats;
		this.age = age;
	}

	/**
	 * ��÷ɻ��ı��
	 * 
	 * @return һ����ʾ�ɻ���ŵ��ַ���
	 */
	public String getID() {
		return id;
	}

	/**
	 * ��÷ɻ�������
	 * 
	 * @return һ����ʾ���͵��ַ���
	 */
	public String getType() {
		return type;
	}

	/**
	 * ��÷ɻ�����λ��
	 * 
	 * @return һ�������������
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * ��÷ɻ��Ļ���
	 * 
	 * @return һ���Ǹ���С��
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
