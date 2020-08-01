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
    Go go;
    
    //test for game
    //�ַ������룬�鿴��ʷ
	@Test
	public void testGame1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        go = new Go(a, b);
        String inputString = "asnjcn\r\n#$%^&\r\nend\r\nY\r\nend\r\nY\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //��end���룬��һ���ַ���������ĸ���룬�ڶ����ַ������������ַ���������Ϊ���˳�ѭ��
        go.game(input);
        assertEquals("1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "������������ȷ�Ĳ�����������������ȷ�Ĳ������Ƿ�Ҫ�鿴������ʷ(Y/N)��"
        		+ "\nAa:\tend", systemOut.toString());
        systemOut.reset();
        //end���룬����������
        go.game(input);
        assertEquals("1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
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
		go = new Go(a, b);
        String inputString = "-1\r\n0\r\n6\r\nend\r\nN\r\n5\r\nend\r\nN\r\n";
        Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //С��1�����5�����룬-1��һ�����Ը�����0�ڶ�������0��6���������Դ���5�����ĸ��˳�ѭ��
        go.game(input);
        assertEquals("1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "������������ȷ�Ĳ�����������������ȷ�Ĳ�����������������ȷ�Ĳ�����"
        		+ "�Ƿ�Ҫ�鿴������ʷ(Y/N)��", systemOut.toString());
        systemOut.reset();
        //���ڵ���1С�ڵ���5�����룬���������5����ʱ�����ж�Ȩ
        go.game(input);
        assertEquals("1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\nAa�������������ţ����롰end��������Ϸ����"
        		+ "\r\n1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
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
        go = new Go(a, b);
        go.menu(new Player(a));
        assertEquals("1.����δ�������ϵ�һ�����ӷ��������ϵ�ָ��λ�ã�\r\n2.���ӣ�\r\n"
        		+ "3.��ѯĳ��λ�õ�ռ����������У����߱���һ����ʲô������ռ�ã���\r\n"
        		+ "4.����������ҷֱ��������ϵ�����������\r\n5.������\r\n"
        		+ "Aa�������������ţ����롰end��������Ϸ����", systemOut.toString());
        System.setOut(console);
	}
    
    //test for play
	//����1������
	@Test
	public void testPlay1() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "19 -1\r\n1 1\r\n1 2\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //��������룬��һ�����꣬�ڶ���������Ϊ���˳�ѭ��
        go = new Go(a,b);
        go.play(1, new Player(a), input, output);
        assertEquals(a + "�����������λ�ã�λ�ó������̱߽磡���������룺", systemOut.toString());
        systemOut.reset();
        //��ȷ�����룬����������
        go = new Go(a,b);
        go.play(1, new Player(a), input, output);
        assertEquals(a + "�����������λ�ã�", systemOut.toString());
        System.setOut(console);
	}
	
	//����2������
	@Test
	public void testPlay2() throws IOException {
		PrintStream console = System.out;
        ByteArrayOutputStream systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOut));
        PrintWriter output = new PrintWriter(new PrintStream(new ByteArrayOutputStream()));
        String inputString = "1 1\r\n1 2\r\n1 1\r\n1 1\r\n1 1\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        go = new Go(a,b);
        //��������룬��һ�������ȷ�һ�����ӣ��ڶ��������Ǵ�������룬��������Ϊ���˳�ѭ��
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(2, new Player(b), input, output);
        assertEquals(b + "�����������ӵ�λ�ã���λ�������ӿ��ᣡ���������룺", systemOut.toString());
        //��ȷ�����룬�����������ȷ�һ�����ӣ���������������ȷ����
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(2, new Player(b), input, output);
        assertEquals(b + "�����������ӵ�λ�ã�", systemOut.toString());
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
        go = new Go(a,b);
        //��������룬��һ�����꣬�ڶ���������Ϊ���˳�ѭ��
        go.play(3, new Player(a), input, output);
        assertEquals(a + "��������Ҫ��ѯ��λ�ã�λ�ó������̱߽磡���������룺��λ��Ϊ��\r\n", systemOut.toString());
        systemOut.reset();
        //��ȷ�����룬����������
        go = new Go(a,b);
        go.play(3, new Player(a), input, output);
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
        go = new Go(a,b);
        String inputString = "1 1\r\n";
		Scanner input = new Scanner(new ByteArrayInputStream(inputString.getBytes()));
        //������
        go.play(4, new Player(a), input, output);
        assertEquals("you:0, opponent:0\r\n", systemOut.toString());
        //�ǿ�����
        go.play(1, new Player(a), input, output);
        systemOut.reset();
        go.play(4, new Player(a), input, output);
        assertEquals("you:1, opponent:0\r\n", systemOut.toString());
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
