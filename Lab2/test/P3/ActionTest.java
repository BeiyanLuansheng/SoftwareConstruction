package P3;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.Test;

public class ActionTest {
    // Test strategy
    // putPiece方法：
    // 按位置是否为棋盘上的合法位置分：是棋盘上的位置，不是棋盘上的位置
    // 按该位置是否空闲分：该位置空闲，该位置不空闲
	// 按棋子分：棋子不是己方的，棋子是己方的
    //
    // takePiece方法：
    // 按位置是否为棋盘上的合法位置分：是棋盘上的位置，不是棋盘上的位置
    // 按该位置是否空闲分：该位置空闲，该位置不空闲
    // 所提棋子分：所提棋子是己方棋子，所提棋子不是己方棋子
    //
    // movePiece方法：
    // 按位置是否为棋盘上的合法位置分：是棋盘上的位置，不是棋盘上的位置
    // 按目的位置是否空闲分：该位置空闲，该位置不空闲
    // 按起始位置是否空闲分：该位置空闲，该位置不空闲
    // 按移动的棋子分：棋子不是己方的，棋子是己方的
    //
    // eatPiece方法：
    // 按位置是否为棋盘上的合法位置分：是棋盘上的位置，不是棋盘上的位置
    // 按两位置是否为同一位置分：是同一位置，不是同一位置
    // 按目的位置是否空闲分：该位置空闲，该位置不空闲
    // 按起始位置是否空闲分：该位置空闲，该位置不空闲
    // 按移动的棋子分：棋子不是己方的，棋子是己方的
    // 按被吃的棋子分：棋子是己方的，棋子不是己方的
    //
    // query方法:
    // 按查询的类型分：chess，go
    // 按位置是否为棋盘上的合法位置分：是棋盘上的位置，不是棋盘上的位置
    // 按该位置是否空闲分：该位置空闲，该位置不空闲
    //
    // count方法
    // 棋子数目为0，棋子数目不为0

    Player white = new Player("White");
    Player black = new Player("Black");
    //Tests for putPiece
    //不是棋盘上的位置
    @Test
    public void testPutPiece1() {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //横坐标或纵坐标不在棋盘内
        Action go = new Action(18, white, black);
        assertFalse(go.putPiece(white, new Piece(null, white), -1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(go.putPiece(white, new Piece(null, white), 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        //横纵坐标都不在棋盘内
        assertFalse(go.putPiece(white, new Piece(null, white), -1, 19, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        assertFalse(go.putPiece(white, new Piece(null, white), 19, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置
    @Test
    public void testPutPiece2() {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        //第一次放，该位置空闲，棋子不是己方的
        assertFalse(go.putPiece(white, new Piece(null, black), 1, 1, output));
        assertEquals("该棋子不是己方的！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        //同位置再放一次，该位置空闲，棋子是己方的
        assertTrue(go.putPiece(white, new Piece(null, white), 1, 1, output));
        assertEquals("", systemOut.toString()); 
        systemOut.reset();
        //同位置再放一次，该位置不空闲
        assertFalse(go.putPiece(white, new Piece(null, white), 1, 1, output));
        assertEquals("该位置已有其他棋子！", systemOut.toString());
        output.close();
        assertEquals("White:\tput (1,1)\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //Tests for takePiece
    //不是棋盘上的位置
    @Test
    public void testTakePiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //横坐标或纵坐标不在棋盘内
        Action go = new Action(18, white, black);
        assertFalse(go.takePiece(white, -1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(go.takePiece(white, 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        //横纵坐标都不在棋盘内
        assertFalse(go.takePiece(white, -1, 19, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        assertFalse(go.takePiece(white, 19, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，该位置空闲
    @Test
    public void testTakePiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertFalse(go.takePiece(white, 1, 1, output));
        assertEquals("该位置无棋子可提！", bytes.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，该位置不空闲，所提棋子是己方棋子
    @Test
    public void testTakePiece3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        go.putPiece(white, new Piece("", white), 1, 1, output);
        assertFalse(go.takePiece(white, 1, 1, output));
        assertEquals("所提棋子是己方棋子！", bytes.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tput (1,1)\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，该位置不空闲，所提棋子不是己方棋子
    @Test
    public void testTakePiece4() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        go.putPiece(black, new Piece("", black), 1, 1, output);
        assertTrue(go.takePiece(white, 1, 1, output));
        output.close();
        assertEquals("Black:\tput (1,1)\r\nWhite:\ttake (1,1)\r\n", fileOut.toString()); //测试文件内容
    }

    //Tests for movePiece
    //不是棋盘上的位置
    @Test
    public void testMovePiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //起点坐标不在棋盘内
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, -1, 1, 1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 8, 1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        //终点坐标不在棋盘内
        assertFalse(chess.movePiece(white, 1, 1, 8, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 1, 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，目的位置不空闲
    @Test
    public void testMovePiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 0, 0, 1, output));
        assertEquals("目的位置已有其他棋子！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，目的位置空闲，起始位置空闲
    @Test
    public void testMovePiece3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 2, 0, 3, output));
        assertEquals("起始位置无棋子！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，目的位置空闲，起始位置不空闲，棋子不是己方的
    @Test
    public void testMovePiece4() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 6, 0, 5, output));
        assertEquals("移动的棋子不是己方的！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，目的位置空闲，起始位置不空闲，棋子是己方的
    @Test
    public void testMovePiece5() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.movePiece(white, 0, 1, 0, 2, output));
        output.close();
        assertEquals("White:\tmove Pawn (0,1) to (0,2)\r\n", fileOut.toString()); //测试文件内容
    }

    //Tests for eatPiece
    //不是棋盘上的位置
    @Test
    public void testEatPiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //起点坐标不在棋盘内
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, -1, 1, 1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 8, 1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        //终点坐标不在棋盘内
        assertFalse(chess.movePiece(white, 1, 1, 8, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 1, 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，是同一位置
    @Test
    public void testEatPiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 1, 1, output));
        assertEquals("指定的两个位置相同！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，不是同一位置，终点位置空闲
    @Test
    public void testEatPiece3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 1, 2, output));
        assertEquals("目标位置无棋子！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，不是同一位置，终点位置不空闲，起点位置空闲
    @Test
    public void testEatPiece4() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 2, 1, 6, output));
        assertEquals("起始位置无棋子！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，不是同一位置，终点位置不空闲，起点位置不空闲，起点棋子不是己方的
    @Test
    public void testEatPiece5() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(black, 1, 1, 2, 1, output));
        assertEquals("起始位置棋子不是己方的！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，不是同一位置，终点位置不空闲，起点位置不空闲，起点棋子是己方的，终点棋子是己方的
    @Test
    public void testEatPiece6() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 2, 1, output));
        assertEquals("目标位置棋子不是敌方的！", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //是棋盘上的位置，不是同一位置，终点位置不空闲，起点位置不空闲，起点棋子是己方的，终点棋子不是己方的
    @Test
    public void testEatPiece7() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.eatPiece(white, 1, 1, 1, 6, output));
        output.close();
        assertEquals("White:\tyour Pawn at (1,1) eat opponent Pawn at (1,6)\r\n", fileOut.toString()); //测试文件内容
    }

    //Tests for Query
    //国际象棋：不是棋盘上的位置
    @Test
    public void testChessQuery1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //横坐标或纵坐标不在棋盘内
        Action chess = new Action(8, white, black);
        assertFalse(chess.query(white, -1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(chess.query(white, 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        //横纵坐标都不在棋盘内
        assertFalse(chess.query(white, -1, 19, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        assertFalse(chess.query(white, 19, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //围棋：不是棋盘上的位置
    @Test
    public void testGoQuery1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //横坐标或纵坐标不在棋盘内
        Action go = new Action(18, white, black);
        assertFalse(go.query(white, -1, 1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString()); //测试屏幕打印输出的提示
        systemOut.reset();
        assertFalse(go.query(white, 1, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        //横纵坐标都不在棋盘内
        assertFalse(go.query(white, -1, 19, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        systemOut.reset();
        assertFalse(go.query(white, 19, -1, output));
        assertEquals("位置超出棋盘边界！", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //国际象棋：是棋盘上的位置，该位置空闲
    @Test
    public void testChessQuery2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.query(white, 0, 2, output));
        assertEquals("该位置为空\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tquery (0,2) 该位置为空\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //围棋：是棋盘上的位置，该位置空闲
    @Test
    public void testGoQuery2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertTrue(go.query(white, 0, 0, output));
        assertEquals("该位置为空\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tquery (0,0) 该位置为空\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //国际象棋：是棋盘上的位置，该位置不空闲
    @Test
    public void testChessQuery3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.query(white, 0, 1, output));
        assertEquals("该棋子是White的Pawn\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tquery (0,1) 该棋子是White的Pawn\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //围棋：不是棋盘上的位置，该位置不空闲
    @Test
    public void testGoQuery3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        go.putPiece(white, new Piece(null, white), 0, 0, output);
        assertTrue(go.query(white, 0, 0, output));
        assertEquals("该棋子是White的\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tput (0,0)\r\nWhite:\tquery (0,0) 该棋子是White的\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //Tests for count
    //棋子数目不为0
    @Test
    public void testCount1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertEquals(16016, chess.count(white, output));
        assertEquals("you:16, opponent:16\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tcount you:16, opponent:16\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }

    //棋子数目为0
    @Test
    public void testCount2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertEquals(0, go.count(white, output));
        assertEquals("you:0, opponent:0\r\n", systemOut.toString()); //测试屏幕打印输出的提示
        output.close();
        assertEquals("White:\tcount you:0, opponent:0\r\n", fileOut.toString()); //测试文件内容
        System.setOut(console);
    }
}
