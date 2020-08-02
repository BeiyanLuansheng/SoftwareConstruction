package activity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 一个immutable的类型，表示学习材料
 */
public class Material {

	private final String name;
	private final String department;
	private final LocalDate date;
	
	// Abstraction function:
	// AF(name, department, date)=以name为名称，发布部门为department，发布日期为date的学习材料
	// Representation invariant:
	// name != null
	// department != null
	// date != null
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 获得发布日期时转化为字符串输出
	
	/**
	 * 创建一个新的材料
	 * @param name 材料的名字，非空
	 * @param department 发布材料的部门，非空
	 * @param date 材料的发布日期，格式为yyyy-MM-dd，非空
	 */
	public Material(String name, String department, String date) {
		this.name = name;
		this.department = department;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(date, df);
	}
	
	/**
	 * 获得材料名称
	 * @return 一个表示材料名称的字符串
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获得材料的发布部门
	 * @return 一个表示材料发布部门的字符串
	 */
	public String  getDepartment() {
		return department;
	}
	
	/**
	 * 获得材料的发布日期
	 * @return 一个格式为yyyy-MM-dd的字符串，表示材料的发布日期
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
