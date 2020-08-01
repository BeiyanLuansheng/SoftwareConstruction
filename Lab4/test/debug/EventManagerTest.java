package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EventManagerTest {

    // Test strategy
    // 单个事件
    // 多个事件，但没有交集
    // 多个事件，存在单个交集
    // 多个事件，存在多个交集
    
	//多个测试使用的是同一个Class
    @Test
    public void testExample(){
        //单个事件
        assertEquals(1, EventManager.book(1, 10, 20));
        //多个事件，不存在交集
        assertEquals(1, EventManager.book(1, 1, 7));
        //多个事件，存在单个交集
        assertEquals(2, EventManager.book(1, 10, 22));
        //多个事件，存在多个交集
        assertEquals(3, EventManager.book(1, 5,15));
        assertEquals(4, EventManager.book(1, 5, 12));
        assertEquals(4, EventManager.book(1, 7, 10));
    }
}