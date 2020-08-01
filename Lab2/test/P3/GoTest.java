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

public class GoTest {
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
    Go go;
    
    //test for game
    //字符串输入，查看历史
	@Test
	public void testGame1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        go = new Go(a, b);
        String inputString = "asnjcn\r\n#$%^&\r\nend\r\nY\r\nend\r\nY\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //非end输入，第一组字符串测试字母输入，第二组字符串测试特殊字符，第三组为了退出循环
        go.game(input);
        assertEquals("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "请重新输入正确的操作：请重新输入正确的操作：是否要查看操作历史(Y/N)？"
        		+ "\nAa:\tend", systemOut.toString());
        systemOut.reset();
        //end输入，第四组输入
        go.game(input);
        assertEquals("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
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
		go = new Go(a, b);
        String inputString = "-1\r\n0\r\n6\r\nend\r\nN\r\n5\r\nend\r\nN\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //小于1或大于5的输入，-1第一个测试负数，0第二个测试0，6第三个测试大于5，第四个退出循环
        go.game(input);
        assertEquals("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "请重新输入正确的操作：请重新输入正确的操作：请重新输入正确的操作："
        		+ "是否要查看操作历史(Y/N)？", systemOut.toString());
        systemOut.reset();
        //大于等于1小于等于5的输入，第五个输入5，此时交换行动权
        go.game(input);
        assertEquals("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\nAa，输入操作的序号（输入“end”结束游戏）："
        		+ "\r\n1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
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
        go = new Go(a, b);
        go.menu(new Player(a));
        assertEquals("1.将尚未在棋盘上的一颗棋子放在棋盘上的指定位置；\r\n2.提子；\r\n"
        		+ "3.查询某个位置的占用情况（空闲，或者被哪一方的什么棋子所占用）；\r\n"
        		+ "4.计算两个玩家分别在棋盘上的棋子总数；\r\n5.跳过；\r\n"
        		+ "Aa，输入操作的序号（输入“end”结束游戏）：", systemOut.toString());
        System.setOut(console);
	}
    
    //test for play
	//动作1，放置
	@Test
	public void testPlay1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1\r\n1 1\r\n1 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //错误的输入，第一组坐标，第二组坐标是为了退出循环
        go = new Go(a,b);
        go.play(1, new Player(a), input, output);
        assertEquals(a + "，请输入放置位置：位置超出棋盘边界！请重新输入：", systemOut.toString());
        systemOut.reset();
        //正确的输入，第三组坐标
        go = new Go(a,b);
        go.play(1, new Player(a), input, output);
        assertEquals(a + "，请输入放置位置：", systemOut.toString());
        System.setOut(console);
	}
	
	//动作2，提子
	@Test
	public void testPlay2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "1 1\r\n1 2\r\n1 1\r\n1 1\r\n1 1\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        go = new Go(a,b);
        //错误的输入，第一组坐标先放一个棋子，第二组坐标是错误的输入，第三组是为了退出循环
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(2, new Player(b), input, output);
        assertEquals(b + "，请输入提子的位置：该位置无棋子可提！请重新输入：", systemOut.toString());
        //正确的输入，第四组坐标先放一个棋子，第五组坐标是正确输入
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(2, new Player(b), input, output);
        assertEquals(b + "，请输入提子的位置：", systemOut.toString());
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
        go = new Go(a,b);
        //错误的输入，第一组坐标，第二组坐标是为了退出循环
        go.play(3, new Player(a), input, output);
        assertEquals(a + "，请输入要查询的位置：位置超出棋盘边界！请重新输入：该位置为空\r\n", systemOut.toString());
        systemOut.reset();
        //正确的输入，第三组坐标
        go = new Go(a,b);
        go.play(3, new Player(a), input, output);
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
        go = new Go(a,b);
        String inputString = "1 1\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //空棋盘
        go.play(4, new Player(a), input, output);
        assertEquals("you:0, opponent:0\r\n", systemOut.toString());
        //非空棋盘
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(4, new Player(a), input, output);
        assertEquals("you:1, opponent:0\r\n", systemOut.toString());
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
        go = new Go(a,b);
        go.play(5, new Player(a), input, output);
        assertEquals("", systemOut.toString());
        output.close();
        assertEquals(a + ":\tskip\r\n", fileOut.toString());
        System.setOut(console);
    }
    
    //tests for history
	@Test
	public void testHistory() throws IOException {
        go = new Go(a,b);
        File file = new File("test/P3/TestHiatory.txt");
        assertEquals("\nAa:\tput (1,1)\nBb:\tskip\nAa:\tend", go.history(file));
	}

}
