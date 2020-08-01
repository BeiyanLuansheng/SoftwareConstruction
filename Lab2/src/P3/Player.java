package P3;

/**
 * 一个immutable类型，表示棋手
 */
public class Player {
	private final String name;
	
	// Rep invariant:
	//   name不为空
	// Abstraction function:
	//   AF(name) = 到真实的棋手的映射
	// Safety from rep exposure:
	//   所有的数据域都是私有的且用final限定
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert name != null;
	}
	
	/**
	 * 创建棋手
	 * @param name 一个非空的字符串
	 */
	public Player(String name) {
		this.name = name;
		checkRep();
	}
	
	/**
	 * @return 返回棋手的姓名
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object anotherPlayer) {
		if(anotherPlayer instanceof Player)
			if(this.name.equals(((Player) anotherPlayer).getName()))
				return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
