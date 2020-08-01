package P3;

/**
 * 一个immutable类型，表示棋子
 */
public class Piece {
	private final String type;
	private final Player player;
	
	// Rep invariant:
	//   player不为空
	// Abstraction function:
	//   AF(type,player) = 一个到实际的类型为type(针对国际象棋)，所属棋手为player的棋子
	// Safety from rep exposure:
	//   所有的数据域都是私有的且用final限定
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert player != null;
	}
	/**
	 * 创建棋子
	 * @param type 棋子的类型
	 * @param player 拥有该棋子的棋手
	 */
	public Piece(String type, Player player) {
		this.type = type;
		this.player = player;
		checkRep();
	}
	
	/**
	 * @return 棋子的类型
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return 拥有该棋子的棋手
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
