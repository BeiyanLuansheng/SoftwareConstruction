package P3;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;

public class ChessTest {
	// test strategy
	// game方法:
	// 按输入类型：数字，字符串
	// 按数字分：1-5，小于1或大于5
	// 按字符串分：end，非end
	// 按是否查看历史分：查看，不查看
	// menu方法：
	// 测试是否能打印出菜单
	// play方法:
	// 按动作代号分：actNumber=1，2，3，4，5
	// 按动作的位置数据是否正确分：正确，错误
    // history方法:
	// 测试能够正确读取文件
    
	final String a = "Aa";
    final String b = "Bb";
    Chess chess;
    
    //test for game
    //字符串输入，查看历史
	@Test
	public void testGame1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        chess = new Chess(a, b);
        String inputString = "asnjcn\r\n#$%^&\r\nend\r\nY\r\nend\r\nY\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //非end输入，第一组字符串测试字母输入，第二组字符串测试特殊字符，第三组为了退出循环
        chess.game(input);
        assertEquals("1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "请重新输入正确的操作：请重新输入正确的操作：是否要查看操作历史(Y/N)？"
        		+ "\nAa:\tend", systemOut.toString());
        systemOut.reset();
        //end输入，第四组输入
        chess.game(input);
        assertEquals("1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\n"
        		+ "Aa，输入操作的序号（输入“end”结束游戏）：是否要查看操作历史(Y/N)？"
        		+ "\nAa:\tend", systemOut.toString());
        System.setOut(console);
	}
    
	//数字输入，不查看历史
	@Test
	public void testGame2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
		chess = new Chess(a, b);
        String inputString = "-1\r\n0\r\n6\r\nend\r\nN\r\n5\r\nend\r\nN\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //小于1或大于5的输入，-1第一个测试负数，0第二个测试0，6第三个测试大于5，第四个退出循环
        chess.game(input);
        assertEquals("1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "请重新输入正确的操作：请重新输入正确的操作：请重新输入正确的操作："
        		+ "是否要查看操作历史(Y/N)？", systemOut.toString());
        systemOut.reset();
        //大于等于1小于等于5的输入，第五个输入5，此时交换行动权
        chess.game(input);
        assertEquals("1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "\r\n1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nBb，输入操作的序号（输入“end”结束游戏）："
        		+ "是否要查看操作历史(Y/N)？", systemOut.toString());
        System.setOut(console);
    }
    
    //test for menu
	@Test
	public void testMenu() {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        chess = new Chess(a, b);
        chess.menu(new Player(a));
        assertEquals("1.移动棋盘上某个位置的棋子至新位置；\r\n2.吃子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\n"
        		+ "Aa，输入操作的序号（输入“end”结束游戏）：", systemOut.toString());
        System.setOut(console);
	}
    
    //test for play
	//动作1，移动
	@Test
	public void testPlay1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1 -1 19\r\n0 1 0 2\r\n0 1 0 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //错误的输入，第一组坐标，第二组坐标是为了退出循环
        chess = new Chess(a,b);
        chess.play(1, new Player(a), input, output);
        assertEquals(a + "，请输入起始位置和目的位置：位置超出棋盘边界！请重新输入：", systemOut.toString());
        systemOut.reset();
        //正确的输入，第三组坐标
        chess = new Chess(a,b);
        chess.play(1, new Player(a), input, output);
        assertEquals(a + "，请输入起始位置和目的位置：", systemOut.toString());
        System.setOut(console);
	}
	
	//动作2，吃子
	@Test
	public void testPlay2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "0 0 0 1\r\n0 0 0 6\r\n0 1 0 7\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        chess = new Chess(a,b);
        //错误的输入，第一组坐标，第二组坐标是为了退出循环
        chess.play(2, new Player(a), input, output);
        assertEquals(a + "，请输入起始位置和目的位置：目标位置棋子不是敌方的！请重新输入：", systemOut.toString());
        systemOut.reset();
        //正确的输入，第三组坐标
        chess.play(2, new Player(a), input, output);
        assertEquals(a + "，请输入起始位置和目的位置：", systemOut.toString());
        System.setOut(console);
    }

    //动作3，查询
	@Test
	public void testPlay3() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1\r\n1 1\r\n1 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        chess = new Chess(a,b);
        //错误的输入，第一组坐标，第二组坐标是为了退出循环
        chess.play(3, new Player(a), input, output);
        assertEquals(a + "，请输入要查询的位置：位置超出棋盘边界！请重新输入：该棋子是Aa的Pawn\r\n", systemOut.toString());
        systemOut.reset();
        //正确的输入，第三组坐标
        chess = new Chess(a,b);
        chess.play(3, new Player(a), input, output);
        assertEquals(a + "，请输入要查询的位置：该位置为空\r\n", systemOut.toString());
        System.setOut(console);
    }
    
    //动作4，计数
	@Test
	public void testPlay4() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        chess = new Chess(a,b);
        String inputString = "";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //非空棋盘
        chess.play(4, new Player(a), input, output);
        assertEquals("you:16, opponent:16\r\n", systemOut.toString());
        System.setOut(console);
    }
    
    //动作5，跳过
	@Test
	public void testPlay5() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        String inputString = "";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        chess = new Chess(a,b);
        chess.play(5, new Player(a), input, output);
        assertEquals("", systemOut.toString());
        output.close();
        assertEquals(a + ":\tskip\r\n", fileOut.toString());
        System.setOut(console);
    }
    
    //tests for history
	@Test
	public void testHistory() throws IOException {
        chess = new Chess(a,b);
        File file = new File("test/P3/TestHiatory.txt");
        assertEquals("\nAa:\tput (1,1)\nBb:\tskip\nAa:\tend", chess.history(file));
	}

}
