package activity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * һ��immutable�����ͣ���ʾѧϰ����
 */
public class Material {

	private final String name;
	private final String department;
	private final LocalDate date;
	
	// Abstraction function:
	// AF(name, department, date)=��nameΪ���ƣ���������Ϊdepartment����������Ϊdate��ѧϰ����
	// Representation invariant:
	// name != null
	// department != null
	// date != null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// ��÷�������ʱת��Ϊ�ַ������
	
	/**
	 * ����һ���µĲ���
	 * @param name ���ϵ����֣��ǿ�
	 * @param department �������ϵĲ��ţ��ǿ�
	 * @param date ���ϵķ������ڣ���ʽΪyyyy-MM-dd���ǿ�
	 */
	public Material(String name, String department, String date) {
		this.name = name;
		this.department = department;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(date, df);
	}
	
	/**
	 * ��ò�������
	 * @return һ����ʾ�������Ƶ��ַ���
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * ��ò��ϵķ�������
	 * @return һ����ʾ���Ϸ������ŵ��ַ���
	 */
	public String  getDepartment() {
		return department;
	}
	
	/**
	 * ��ò��ϵķ�������
	 * @return һ����ʽΪyyyy-MM-dd���ַ�������ʾ���ϵķ�������
	 */
	public String getDate() {
		return date.toString();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object material) {
		if(material instanceof Material) {
			Material m = (Material) material;
			if(m.name.equals(this.name) && m.department.equals(this.department) && m.date.equals(this.date))
				return true;
		}
		return false;
	}
}
