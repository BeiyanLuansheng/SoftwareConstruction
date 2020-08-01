package P3;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.Test;

public class ActionTest {
    // Test strategy
    // putPiece������
    // ��λ���Ƿ�Ϊ�����ϵĺϷ�λ�÷֣��������ϵ�λ�ã����������ϵ�λ��
    // ����λ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
	// �����ӷ֣����Ӳ��Ǽ����ģ������Ǽ�����
    //
    // takePiece������
    // ��λ���Ƿ�Ϊ�����ϵĺϷ�λ�÷֣��������ϵ�λ�ã����������ϵ�λ��
    // ����λ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    // �������ӷ֣����������Ǽ������ӣ��������Ӳ��Ǽ�������
    //
    // movePiece������
    // ��λ���Ƿ�Ϊ�����ϵĺϷ�λ�÷֣��������ϵ�λ�ã����������ϵ�λ��
    // ��Ŀ��λ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    // ����ʼλ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    // ���ƶ������ӷ֣����Ӳ��Ǽ����ģ������Ǽ�����
    //
    // eatPiece������
    // ��λ���Ƿ�Ϊ�����ϵĺϷ�λ�÷֣��������ϵ�λ�ã����������ϵ�λ��
    // ����λ���Ƿ�Ϊͬһλ�÷֣���ͬһλ�ã�����ͬһλ��
    // ��Ŀ��λ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    // ����ʼλ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    // ���ƶ������ӷ֣����Ӳ��Ǽ����ģ������Ǽ�����
    // �����Ե����ӷ֣������Ǽ����ģ����Ӳ��Ǽ�����
    //
    // query����:
    // ����ѯ�����ͷ֣�chess��go
    // ��λ���Ƿ�Ϊ�����ϵĺϷ�λ�÷֣��������ϵ�λ�ã����������ϵ�λ��
    // ����λ���Ƿ���з֣���λ�ÿ��У���λ�ò�����
    //
    // count����
    // ������ĿΪ0��������Ŀ��Ϊ0

    Player white = new Player("White");
    Player black = new Player("Black");
    //Tests for putPiece
    //���������ϵ�λ��
    @Test
    public void testPutPiece1() {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������������겻��������
        Action go = new Action(18, white, black);
        assertFalse(go.putPiece(white, new Piece(null, white), -1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(go.putPiece(white, new Piece(null, white), 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        //�������궼����������
        assertFalse(go.putPiece(white, new Piece(null, white), -1, 19, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        assertFalse(go.putPiece(white, new Piece(null, white), 19, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ��
    @Test
    public void testPutPiece2() {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        //��һ�ηţ���λ�ÿ��У����Ӳ��Ǽ�����
        assertFalse(go.putPiece(white, new Piece(null, black), 1, 1, output));
        assertEquals("�����Ӳ��Ǽ����ģ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        //ͬλ���ٷ�һ�Σ���λ�ÿ��У������Ǽ�����
        assertTrue(go.putPiece(white, new Piece(null, white), 1, 1, output));
        assertEquals("", systemOut.toString()); 
        systemOut.reset();
        //ͬλ���ٷ�һ�Σ���λ�ò�����
        assertFalse(go.putPiece(white, new Piece(null, white), 1, 1, output));
        assertEquals("��λ�������������ӣ�", systemOut.toString());
        output.close();
        assertEquals("White:\tput (1,1)\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //Tests for takePiece
    //���������ϵ�λ��
    @Test
    public void testTakePiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������������겻��������
        Action go = new Action(18, white, black);
        assertFalse(go.takePiece(white, -1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(go.takePiece(white, 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        //�������궼����������
        assertFalse(go.takePiece(white, -1, 19, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        assertFalse(go.takePiece(white, 19, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã���λ�ÿ���
    @Test
    public void testTakePiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertFalse(go.takePiece(white, 1, 1, output));
        assertEquals("��λ�������ӿ��ᣡ", bytes.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã���λ�ò����У����������Ǽ�������
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
        assertEquals("���������Ǽ������ӣ�", bytes.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tput (1,1)\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã���λ�ò����У��������Ӳ��Ǽ�������
    @Test
    public void testTakePiece4() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        go.putPiece(black, new Piece("", black), 1, 1, output);
        assertTrue(go.takePiece(white, 1, 1, output));
        output.close();
        assertEquals("Black:\tput (1,1)\r\nWhite:\ttake (1,1)\r\n", fileOut.toString()); //�����ļ�����
    }

    //Tests for movePiece
    //���������ϵ�λ��
    @Test
    public void testMovePiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������겻��������
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, -1, 1, 1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 8, 1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        //�յ����겻��������
        assertFalse(chess.movePiece(white, 1, 1, 8, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 1, 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�Ŀ��λ�ò�����
    @Test
    public void testMovePiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 0, 0, 1, output));
        assertEquals("Ŀ��λ�������������ӣ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�Ŀ��λ�ÿ��У���ʼλ�ÿ���
    @Test
    public void testMovePiece3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 2, 0, 3, output));
        assertEquals("��ʼλ�������ӣ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�Ŀ��λ�ÿ��У���ʼλ�ò����У����Ӳ��Ǽ�����
    @Test
    public void testMovePiece4() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, 0, 6, 0, 5, output));
        assertEquals("�ƶ������Ӳ��Ǽ����ģ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�Ŀ��λ�ÿ��У���ʼλ�ò����У������Ǽ�����
    @Test
    public void testMovePiece5() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.movePiece(white, 0, 1, 0, 2, output));
        output.close();
        assertEquals("White:\tmove Pawn (0,1) to (0,2)\r\n", fileOut.toString()); //�����ļ�����
    }

    //Tests for eatPiece
    //���������ϵ�λ��
    @Test
    public void testEatPiece1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������겻��������
        Action chess = new Action(8, white, black);
        assertFalse(chess.movePiece(white, -1, 1, 1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 8, 1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        //�յ����겻��������
        assertFalse(chess.movePiece(white, 1, 1, 8, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(chess.movePiece(white, 1, 1, 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã���ͬһλ��
    @Test
    public void testEatPiece2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 1, 1, output));
        assertEquals("ָ��������λ����ͬ��", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�����ͬһλ�ã��յ�λ�ÿ���
    @Test
    public void testEatPiece3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 1, 2, output));
        assertEquals("Ŀ��λ�������ӣ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�����ͬһλ�ã��յ�λ�ò����У����λ�ÿ���
    @Test
    public void testEatPiece4() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 2, 1, 6, output));
        assertEquals("��ʼλ�������ӣ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�����ͬһλ�ã��յ�λ�ò����У����λ�ò����У�������Ӳ��Ǽ�����
    @Test
    public void testEatPiece5() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(black, 1, 1, 2, 1, output));
        assertEquals("��ʼλ�����Ӳ��Ǽ����ģ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�����ͬһλ�ã��յ�λ�ò����У����λ�ò����У���������Ǽ����ģ��յ������Ǽ�����
    @Test
    public void testEatPiece6() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertFalse(chess.eatPiece(white, 1, 1, 2, 1, output));
        assertEquals("Ŀ��λ�����Ӳ��ǵз��ģ�", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������ϵ�λ�ã�����ͬһλ�ã��յ�λ�ò����У����λ�ò����У���������Ǽ����ģ��յ����Ӳ��Ǽ�����
    @Test
    public void testEatPiece7() throws IOException {
    	ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.eatPiece(white, 1, 1, 1, 6, output));
        output.close();
        assertEquals("White:\tyour Pawn at (1,1) eat opponent Pawn at (1,6)\r\n", fileOut.toString()); //�����ļ�����
    }

    //Tests for Query
    //�������壺���������ϵ�λ��
    @Test
    public void testChessQuery1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������������겻��������
        Action chess = new Action(8, white, black);
        assertFalse(chess.query(white, -1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(chess.query(white, 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        //�������궼����������
        assertFalse(chess.query(white, -1, 19, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        assertFalse(chess.query(white, 19, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //Χ�壺���������ϵ�λ��
    @Test
    public void testGoQuery1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        //������������겻��������
        Action go = new Action(18, white, black);
        assertFalse(go.query(white, -1, 1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString()); //������Ļ��ӡ�������ʾ
        systemOut.reset();
        assertFalse(go.query(white, 1, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        //�������궼����������
        assertFalse(go.query(white, -1, 19, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        systemOut.reset();
        assertFalse(go.query(white, 19, -1, output));
        assertEquals("λ�ó������̱߽磡", systemOut.toString());
        output.close();
        assertEquals("", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������壺�������ϵ�λ�ã���λ�ÿ���
    @Test
    public void testChessQuery2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.query(white, 0, 2, output));
        assertEquals("��λ��Ϊ��\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tquery (0,2) ��λ��Ϊ��\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //Χ�壺�������ϵ�λ�ã���λ�ÿ���
    @Test
    public void testGoQuery2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertTrue(go.query(white, 0, 0, output));
        assertEquals("��λ��Ϊ��\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tquery (0,0) ��λ��Ϊ��\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //�������壺�������ϵ�λ�ã���λ�ò�����
    @Test
    public void testChessQuery3() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertTrue(chess.query(white, 0, 1, output));
        assertEquals("��������White��Pawn\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tquery (0,1) ��������White��Pawn\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //Χ�壺���������ϵ�λ�ã���λ�ò�����
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
        assertEquals("��������White��\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tput (0,0)\r\nWhite:\tquery (0,0) ��������White��\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //Tests for count
    //������Ŀ��Ϊ0
    @Test
    public void testCount1() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action chess = new Action(8, white, black);
        assertEquals(16016, chess.count(white, output));
        assertEquals("you:16, opponent:16\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tcount you:16, opponent:16\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }

    //������ĿΪ0
    @Test
    public void testCount2() throws IOException {
        PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
        PrintWriter output = new PrintWriter(new PrintStream(fileOut));
        Action go = new Action(18, white, black);
        assertEquals(0, go.count(white, output));
        assertEquals("you:0, opponent:0\r\n", systemOut.toString()); //������Ļ��ӡ�������ʾ
        output.close();
        assertEquals("White:\tcount you:0, opponent:0\r\n", fileOut.toString()); //�����ļ�����
        System.setOut(console);
    }
}
