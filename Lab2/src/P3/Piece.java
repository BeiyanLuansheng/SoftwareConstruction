package P3;

/**
 * һ��immutable���ͣ���ʾ����
 */
public class Piece {
	private final String type;
	private final Player player;
	
	// Rep invariant:
	//   player��Ϊ��
	// Abstraction function:
	//   AF(type,player) = һ����ʵ�ʵ�����Ϊtype(��Թ�������)����������Ϊplayer������
	// Safety from rep exposure:
	//   ���е���������˽�е�����final�޶�
	
	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert player != null;
	}
	/**
	 * ��������
	 * @param type ���ӵ�����
	 * @param player ӵ�и����ӵ�����
	 */
	public Piece(String type, Player player) {
		this.type = type;
		this.player = player;
		checkRep();
	}
	
	/**
	 * @return ���ӵ�����
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return ӵ�и����ӵ�����
	 */
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean equals(Object piece) {
		if(piece instanceof Piece) {
			Piece another = (Piece) piece;
			if(another.getPlayer().equals(this.player))
				if((another.getType() != null && this.type != null && another.getType().equals(this.type))
						|| (this.type == null && another.getType() == null))
					return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return player.getName().hashCode();
	}
}
