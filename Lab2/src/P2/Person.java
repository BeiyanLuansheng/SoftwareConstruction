package P2;

/**
 * Immutable type representing a person
 */
public class Person{
	private final String name;
	/**
	 * 创建一个人
	 * @param name 姓名
	 */
	public Person(String name){
		this.name = name;
	}
	
	/**
	 * @return Get the name of the Person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 判断两个人是否为同一人
	 * @param anotherPerson 另一个人
	 */
	@Override
	public boolean equals(Object anotherPerson) {
		if(this.name.equals(((Person) anotherPerson).getName()))
			return true;
		else return false;
	}
}