package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EventManagerTest {

    // Test strategy
    // �����¼�
    // ����¼�����û�н���
    // ����¼������ڵ�������
    // ����¼������ڶ������
    
	//�������ʹ�õ���ͬһ��Class
    @Test
    public void testExample(){
        //�����¼�
        assertEquals(1, EventManager.book(1, 10, 20));
        //����¼��������ڽ���
        assertEquals(1, EventManager.book(1, 1, 7));
        //����¼������ڵ�������
        assertEquals(2, EventManager.book(1, 10, 22));
        //����¼������ڶ������
        assertEquals(3, EventManager.book(1, 5,15));
        assertEquals(4, EventManager.book(1, 5, 12));
        assertEquals(4, EventManager.book(1, 7, 10));
    }
}