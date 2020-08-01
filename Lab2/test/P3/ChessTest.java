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
	// game����:
	// ���������ͣ����֣��ַ���
	// �����ַ֣�1-5��С��1�����5
	// ���ַ����֣�end����end
	// ���Ƿ�鿴��ʷ�֣��鿴�����鿴
	// menu������
	// �����Ƿ��ܴ�ӡ���˵�
	// play����:
	// ���������ŷ֣�actNumber=1��2��3��4��5
	// ��������λ�������Ƿ���ȷ�֣���ȷ������
    // history����:
	// �����ܹ���ȷ��ȡ�ļ�
    
	final String a = "Aa";
    final String b = "Bb";
    Chess chess;
    
    //test for game
    //�ַ������룬�鿴��ʷ
	@Test
	public void testGame1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        chess = new Chess(a, b);
        String inputString = "asnjcn\r\n#$%^&\r\nend\r\nY\r\nend\r\nY\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //��end���룬��һ���ַ���������ĸ���룬�ڶ����ַ������������ַ���������Ϊ���˳�ѭ��
        chess.game(input);
        assertEquals("1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "������������ȷ�Ĳ�����������������ȷ�Ĳ������Ƿ�Ҫ�鿴������ʷ(Y/N)��"
        		+ "\nAa:\tend", systemOut.toString());
        systemOut.reset();
        //end���룬����������
        chess.game(input);
        assertEquals("1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\n"
        		+ "Aa�������������ţ����롰end��������Ϸ�����Ƿ�Ҫ�鿴������ʷ(Y/N)��"
        		+ "\nAa:\tend", systemOut.toString());
        System.setOut(console);
	}
    
	//�������룬���鿴��ʷ
	@Test
	public void testGame2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
		chess = new Chess(a, b);
        String inputString = "-1\r\n0\r\n6\r\nend\r\nN\r\n5\r\nend\r\nN\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //С��1�����5�����룬-1��һ�����Ը�����0�ڶ�������0��6���������Դ���5�����ĸ��˳�ѭ��
        chess.game(input);
        assertEquals("1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "������������ȷ�Ĳ�����������������ȷ�Ĳ�����������������ȷ�Ĳ�����"
        		+ "�Ƿ�Ҫ�鿴������ʷ(Y/N)��", systemOut.toString());
        systemOut.reset();
        //���ڵ���1С�ڵ���5�����룬���������5����ʱ�����ж�Ȩ
        chess.game(input);
        assertEquals("1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "\r\n1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nBb�������������ţ����롰end��������Ϸ����"
        		+ "�Ƿ�Ҫ�鿴������ʷ(Y/N)��", systemOut.toString());
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
        assertEquals("1.�ƶ�������ĳ��λ�õ���������λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\n"
        		+ "Aa�������������ţ����롰end��������Ϸ����", systemOut.toString());
        System.setOut(console);
	}
    
    //test for play
	//����1���ƶ�
	@Test
	public void testPlay1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1 -1 19\r\n0 1 0 2\r\n0 1 0 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //��������룬��һ�����꣬�ڶ���������Ϊ���˳�ѭ��
        chess = new Chess(a,b);
        chess.play(1, new Player(a), input, output);
        assertEquals(a + "����������ʼλ�ú�Ŀ��λ�ã�λ�ó������̱߽磡���������룺", systemOut.toString());
        systemOut.reset();
        //��ȷ�����룬����������
        chess = new Chess(a,b);
        chess.play(1, new Player(a), input, output);
        assertEquals(a + "����������ʼλ�ú�Ŀ��λ�ã�", systemOut.toString());
        System.setOut(console);
	}
	
	//����2������
	@Test
	public void testPlay2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "0 0 0 1\r\n0 0 0 6\r\n0 1 0 7\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        chess = new Chess(a,b);
        //��������룬��һ�����꣬�ڶ���������Ϊ���˳�ѭ��
        chess.play(2, new Player(a), input, output);
        assertEquals(a + "����������ʼλ�ú�Ŀ��λ�ã�Ŀ��λ�����Ӳ��ǵз��ģ����������룺", systemOut.toString());
        systemOut.reset();
        //��ȷ�����룬����������
        chess.play(2, new Player(a), input, output);
        assertEquals(a + "����������ʼλ�ú�Ŀ��λ�ã�", systemOut.toString());
        System.setOut(console);
    }

    //����3����ѯ
	@Test
	public void testPlay3() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1\r\n1 1\r\n1 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        chess = new Chess(a,b);
        //��������룬��һ�����꣬�ڶ���������Ϊ���˳�ѭ��
        chess.play(3, new Player(a), input, output);
        assertEquals(a + "��������Ҫ��ѯ��λ�ã�λ�ó������̱߽磡���������룺��������Aa��Pawn\r\n", systemOut.toString());
        systemOut.reset();
        //��ȷ�����룬����������
        chess = new Chess(a,b);
        chess.play(3, new Player(a), input, output);
        assertEquals(a + "��������Ҫ��ѯ��λ�ã���λ��Ϊ��\r\n", systemOut.toString());
        System.setOut(console);
    }
    
    //����4������
	@Test
	public void testPlay4() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        chess = new Chess(a,b);
        String inputString = "";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //�ǿ�����
        chess.play(4, new Player(a), input, output);
        assertEquals("you:16, opponent:16\r\n", systemOut.toString());
        System.setOut(console);
    }
    
    //����5������
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
