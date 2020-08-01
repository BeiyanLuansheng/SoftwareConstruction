package P3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 一个mutable的类型，代表一个棋盘
 */
public class Board {
	private Map<Position, Piece> board;
	
	// Rep invariant:
	//   board不为空
	// Abstraction function:
	//   AF(board) = 一个真实的棋盘
	// Safety from rep exposure:
	//   所有的数据域都是私有的
	//   获得棋盘时使用Collections.unmodifiableMap()方法转换成了不可修改的类型
	//   获得棋盘上的某个位置的棋子时，棋子是不可变的
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.board != null;
	}
	/**
	 * 初始化棋盘
	 * @param size 棋盘大小，chess为8，go为18
	 * @param white 白方棋手
	 * @param black 黑方棋手
	 */
	public Board(int size, Player white, Player black) {
		this.board = new HashMap<Position, Piece>();
		if(size == 8) {
			for(int i=0; i<size; i++) { //初始时国际象棋的空位
				for(int j=2; j<6; j++)
					board.put(new Position(i, j), null);
			}
			try {
				Scanner in = new Scanner(new File("src/P3/chess_config.txt"));
				for(int i=0; i<16; i++) {
					String type = in.next();
					int x = in.nextInt();
					int y = in.nextInt();
					board.put(new Position(x, y), new Piece(type, white));
				}
				for(int i=0; i<16; i++) {
					String type = in.next();
					int x = in.nextInt();
					int y = in.nextInt();
					board.put(new Position(x, y), new Piece(type, black));
				}
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("无配置文件！");
			}
		}
		else {
			for(int i=0; i<=size; i++)
				for(int j=0; j<=size; j++)
					board.put(new Position(i, j), null);
		}
		checkRep();
	}
	
	/**
	 * 在棋盘上的合法位置放置棋子
	 * @param position 放置棋子的目标位置，空闲的合法位置
	 * @param piece 放置的棋子
	 */
	public void put(Position position, Piece piece) {
		board.put(position, piece);
		checkRep();
	}
	
	/**
	 * 从棋盘上的合法位置取走棋子
	 * @param position 被取走棋子的位置，不空闲的合法位置
	 */
	public void take(Position position) {
		board.put(position, null);
		checkRep();
	}
	
	/**
	 * @return 返回一个不可修改的棋盘状态 
	 */
	public Map<Position, Piece> getBoard(){
		return Collections.unmodifiableMap(board);
	}
	
	/**
	 * 查询棋盘上某个合法位置的状态
	 * @param position 棋盘上的合法位置
	 * @return 如果有棋子，返回该棋子
	 *         如果没有棋子，返回null
	 */
	public Piece get(Position position) {
		return board.get(position);
	}
}
