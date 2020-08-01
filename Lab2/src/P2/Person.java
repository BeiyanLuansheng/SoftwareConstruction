package P2;

/**
 * Immutable type representing a person
 */
public class Person{
	private final String name;
	/**
	 * ����һ����
	 * @param name ����
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
	 * �ж��������Ƿ�Ϊͬһ��
	 * @param anotherPerson ��һ����
	 */
	@Override
	public boolean equals(Object anotherPerson) {
		if(this.name.equals(((Person) anotherPerson).getName()))
			return true;
		else return false;
	}
}