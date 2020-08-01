package P3;

/**
 * һ��immutable���ͣ���ʾ�����ϵ�һ��λ��
 */
public class Position {
	private final int x;
	private final int y;
	
	// Rep invariant:
	//   x,y������
	// Abstraction function:
	//   AF(x,y) = һ�������굽ʵ�ʵ��ӳ��
	// Safety from rep exposure:
	//   ���е���������˽�е�����final�޶�
	
	/**
	 * ����λ��
	 * @param x λ�ú�����
	 * @param y λ��������
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return λ�ú�����
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return λ��������
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
