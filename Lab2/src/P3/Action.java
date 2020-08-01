package P3;

import java.io.PrintWriter;
import java.util.Map;
/*
 * 一个mutable的类型表示对国际象棋或围棋的操作
 */
public class Action {
	private Board board;
	
	// Rep invariant
	//   棋盘不为空
	// Abstraction function
	//   一个从抽象的操作到实际对棋盘上的棋子的动作的映射
	// Safety from rep exposure
	//   所有的数据域都是私有的
	//   获得棋盘board的状态时调用了Board类的getBoard方法，得到了一个不可修改的棋盘
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert board != null;
	}
	
	/**
	 * 得到一张初始化后的棋盘
	 * @param size 棋盘的大小
	 * @param white 白方棋手
	 * @param black 黑方棋手
	 */
	public Action(int size, Player white, Player black) {
		this.board = new Board(size, white, black);
		checkRep();
	}

	/**
	 * 围棋：棋手把指定棋子放在指定位置
	 * @param player 动作发生者：白方或黑方棋手
	 * @param piece 指定棋子
	 * @param x 指定位置横坐标
	 * @param y 指定位置纵坐标
	 * @param output 文件输出
	 * @return true 成功
	 *         false 失败
	 */
	public boolean putPiece(Player player, Piece piece, int x, int y,  PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("位置超出棋盘边界！");
			return false;
		}
		if(board.get(position) != null) {
			System.out.print("该位置已有其他棋子！");
			return false;
		}
		if(!piece.getPlayer().equals(player)) {
			System.out.print("该棋子不是己方的！");
			return false;
		}
		board.put(position, piece);
		output.println(player.getName() + ":\tput (" + x + "," + y + ")");
		checkRep();
		return true;
	}
	
	/**
	 * 围棋：棋手把指定棋子从指定位置取走
	 * @param player 动作发生者：白方或黑方棋手
	 * @param x 被取走棋子位置横坐标
	 * @param y 被取走棋子位置纵坐标
	 * @param output 文件输出
	 * @return true 成功
	 *         false 失败
	 */
	public boolean takePiece(Player player, int x, int y, PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("位置超出棋盘边界！");
			return false;
		}
		if(board.get(position) == null) {
			System.out.print("该位置无棋子可提！");
			return false;
		}
		if(board.get(position).getPlayer().equals(player)) {
			System.out.print("所提棋子是己方棋子！");
			return false;
		}
		board.take(position);
		output.println(player.getName() + ":\ttake (" + x + "," + y + ")");
		checkRep();
		return true;
	}
	
	/**
	 * 国际象棋：从起始位置移动棋子到目标位置
	 * @param player 动作发生者：白方或黑方棋手
	 * @param sourceX 起始位置横坐标
	 * @param sourceY 起始位置纵坐标
	 * @param targetX 目标位置横坐标
	 * @param targetY 目标位置横坐标
	 * @param output 文件输出
	 * @return true 移动成功
	 *         false 移动失败
	 */
	public boolean movePiece(Player player, int sourceX, int sourceY, int targetX, int targetY, PrintWriter output) {
		Position source = new Position(sourceX, sourceY);
		Position target = new Position(targetX, targetY);
		if(!board.getBoard().containsKey(source) || !board.getBoard().containsKey(target)) {
			System.out.print("位置超出棋盘边界！");
			return false;
		}
		if(board.get(target) != null) {
			System.out.print("目的位置已有其他棋子！");
			return false;
		}
		if(board.get(source) == null) {
			System.out.print("起始位置无棋子！");
			return false;
		}
		if(!board.get(source).getPlayer().equals(player)) {
			System.out.print("移动的棋子不是己方的！");
			return false;
		}
		Piece piece = board.get(source);
		board.take(source);
		board.put(target, piece);
		output.println(player.getName() + ":\tmove " + piece.getType() +" (" + sourceX 
						+ "," + sourceY + ") to (" + targetX + "," + targetY + ")");
		checkRep();
		return true;
	}
	
	/**
	 * 国际象棋：吃子动作，第一个位置的棋子吃掉第二个位置的棋子
	 * @param player 动作发生者：白方或黑方棋手
	 * @param sourceX 己方棋子位置横坐标
	 * @param sourceY 己方棋子位置纵坐标
	 * @param targetX 敌方棋子位置横坐标
	 * @param targetY 敌方棋子位置纵坐标
	 * @param output 文件输出
	 * @return true 成功吃掉对方棋子
	 *         false 吃掉对方棋子失败
	 */
	public boolean eatPiece(Player player, int sourceX, int sourceY, int targetX, int targetY, PrintWriter output) {
		Position source = new Position(sourceX, sourceY);
		Position target = new Position(targetX, targetY);
		if(!board.getBoard().containsKey(source) || !board.getBoard().containsKey(target)) {
			System.out.print("位置超出棋盘边界！");
			return false;
		}
		if(sourceX == targetX && sourceY == targetY) {
			System.out.print("指定的两个位置相同！");
			return false;
		}
		if(board.get(source) == null) {
			System.out.print("起始位置无棋子！");
			return false;
		}
		if(board.get(target) == null) {
			System.out.print("目标位置无棋子！");
			return false;
		}
		if(!board.get(source).getPlayer().equals(player)) {
			System.out.print("起始位置棋子不是己方的！");
			return false;
		}
		if(board.get(target).getPlayer().equals(player)) {
			System.out.print("目标位置棋子不是敌方的！");
			return false;
		}
		Piece targetPiece = board.get(target);
		board.take(target);
		Piece sourcePiece = board.get(source);
		board.take(source);
		board.put(target, sourcePiece);
		output.println(player.getName() + ":\tyour " + sourcePiece.getType() + " at (" + sourceX + "," + sourceY + 
				") eat opponent " + targetPiece.getType() +" at (" + targetX + "," + targetY + ")");
		checkRep();
		return true;
	}
	
	/**
	 * 查询某位置是否空闲，如果不空闲输出是谁的什么棋子
	 * @param player 动作发生者：白方或黑方棋手
	 * @param x 查询位置的横坐标
	 * @param y 查询位置的纵坐标
	 * @param output 文件输出
	 * @return true 查询成功
	 *         false 查询失败
	 */
	public boolean query(Player player,int x, int y, PrintWriter output) {
		Position position = new Position(x, y);
		if(!board.getBoard().containsKey(position)) {
			System.out.print("位置超出棋盘边界！");
			return false;
		}
		Piece piece = board.get(position);
		if(piece == null) {
			System.out.println("该位置为空");	
			output.println(player.getName() + ":\tquery (" + x + "," + y + ") 该位置为空");
		}	
		else {
			System.out.print("该棋子是" + piece.getPlayer().getName() + "的");
			output.print(player.getName() + ":\tquery (" + x + "," + y + 
							") 该棋子是" + piece.getPlayer().getName() + "的");
			if(piece.getType() != null){
				System.out.println(piece.getType());
				output.println(piece.getType());
			}
			else {
				System.out.println();
				output.println();
			}
		}
		return true;
	}
	
	/**
	 * 计算棋盘上双方各有多少棋子
	 * @param player 动作发生者：白方或黑方棋手
	 * @param output 文件输出
	 * @return 一个整数，高三位表示动作发生者的棋子数，低三位表示对手的棋子数
	 */
	public int count(Player player, PrintWriter output) {
		int player1 = 0, player2 = 0;
		Map<Position, Piece> map = board.getBoard();
		for(Position pos: map.keySet()) {
			if(map.get(pos) != null) 
				if(map.get(pos).getPlayer().equals(player))	player1++;
				else player2++;
		}
		System.out.println("you:" + player1 + ", opponent:" + player2);
		output.println(player.getName() + ":\tcount you:" + player1 + ", opponent:" + player2);
		return player1 * 1000 + player2;
	}
	
	/**
	 * @return 返回不可改变的棋盘状态
	 */
	public Map<Position, Piece> getBoard() {
		return board.getBoard();
	}
}
