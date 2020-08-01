package P3;

/**
 * 一个immutable类型，表示棋盘上的一个位置
 */
public class Position {
	private final int x;
	private final int y;
	
	// Rep invariant:
	//   x,y是整数
	// Abstraction function:
	//   AF(x,y) = 一个从坐标到实际点的映射
	// Safety from rep exposure:
	//   所有的数据域都是私有的且用final限定
	
	/**
	 * 创建位置
	 * @param x 位置横坐标
	 * @param y 位置纵坐标
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return 位置横坐标
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return 位置纵坐标
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object position) {
		if(position instanceof Position)
			if(position instanceof Position && this.x == ((Position) position).getX() 
					&& this.y == ((Position) position).getY())
				return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return 10 * x + y;
	}
}
