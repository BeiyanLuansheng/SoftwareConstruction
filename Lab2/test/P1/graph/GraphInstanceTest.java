/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    /**
     * Graph.add��������
     * ����ͼ�У��㲻��ͼ��
     * 
     * Graph.set��������
     * �����֣�����ͼ�У��㲻��ͼ��
     * ���յ�֣�����ͼ�У��㲻��ͼ��
     * ��Ȩ�ط֣�Ȩ������0��Ȩ����0
     * 
     * Graph.remove��������
     * �����Ƿ���ͼ�з֣�����ͼ�У��㲻��ͼ��
     * �����Ƿ�������Ϊ����ı߷֣���û����رߣ�������ر�
     * 
     * Graph.vertices��������
     * ͼ��û�е㣬ͼ���ж����
     * 
     * Graph.sources������Graph.targets��������
     * �����㲻��ͼ��
     * ��������ͼ�У�ͼ��û����Ӧ�ĵ㣬����Ӧ�ĵ�
     */
	
    /**
     * Overridden by implementation-specific test classes.
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    //other tests for instance methods of Graph
    @Test
    public void testAdd() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "A";
    	assertTrue(graph.add(a));
    	assertFalse(graph.add(b));
    }
    
    //��㲻��ͼ�У��յ㲻��ͼ�У�Ȩ��Ϊ0
    //�����ͼ�У��յ���ͼ�У�Ȩ�ز�Ϊ0
    @Test
    public void testSet1() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 2));
    }
    
    //��㲻��ͼ�У��յ㲻��ͼ�У�Ȩ�ز�Ϊ0
    //�����ͼ�У��յ���ͼ�У�Ȩ��Ϊ0
    @Test
    public void testSet2() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
    
    //��㲻��ͼ�У��յ���ͼ�У�Ȩ��Ϊ0
    @Test
    public void testSet3() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(b));
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
  
    //��㲻��ͼ�У��յ���ͼ�У�Ȩ�ز�Ϊ0
    @Test
    public void testSet4() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(b));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 2));
    }
    
    //�����ͼ�У��յ㲻��ͼ�У�Ȩ��Ϊ0
    @Test
    public void testSet5() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(a));
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
    
    //�����ͼ�У��յ㲻��ͼ�У�Ȩ�ز�Ϊ0
    @Test
    public void testSet6() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(a));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 2));
    }
    
    //
    @Test
    public void testRemove1() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	assertTrue(graph.add(a));
    	//����ͼ��
    	assertTrue(graph.remove(a));
    	//�㲻��ͼ��
    	assertFalse(graph.remove(a));
    }
    
    //��û����ر�
    @Test
    public void testRemove2() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	String c = "C";
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertTrue(graph.add(c));
    	assertEquals(0, graph.set(b, c, 1));
    	assertTrue(graph.remove(a));
    }
    
    //������ر�
    @Test
    public void testRemove3() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	String c = "C";
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertTrue(graph.add(c));
    	assertEquals(0, graph.set(b, a, 1));
    	assertEquals(0, graph.set(a, c, 1));
    	assertEquals(0, graph.set(c, b, 1));
    	assertTrue(graph.remove(a));
    	Map<String, Integer> map = new HashMap<>();
    	assertEquals(map, graph.targets(b));
    	assertEquals(map, graph.sources(c));
    }
    
    @Test
    public void testVertices() {
    	Graph<String> graph = emptyInstance();
    	Set<String> set = new HashSet<String>();
    	String a = "A";
    	String b = "B";
    	assertEquals(set, graph.vertices());
    	set.add(a);
    	assertTrue(graph.add(a));
    	assertEquals(set, graph.vertices());
    	set.add(b);
    	assertTrue(graph.add(b));
    	assertEquals(set, graph.vertices());
    }
    
    //��������ͼ��
    @Test
    public void testSourcesAndTargetsInGraph() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	String c = "C";
    	String d = "D";
    	Map<String, Integer> sourcesToB = new HashMap<String, Integer>();
    	Map<String, Integer> targetsFromC = new HashMap<String, Integer>();
    	Map<String, Integer> sourcesToD = new HashMap<String, Integer>();
    	Map<String, Integer> targetsFromD = new HashMap<String, Integer>();
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertTrue(graph.add(c));
    	assertTrue(graph.add(d));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(0, graph.set(c, a, 2));
    	assertEquals(0, graph.set(c, b, 3));
    	
    	sourcesToB.put(a, 1);
    	sourcesToB.put(c, 3);
    	targetsFromC.put(a, 2);
    	targetsFromC.put(b, 3);
    	
    	assertEquals(sourcesToB, graph.sources(b));
    	assertEquals(targetsFromC, graph.targets(c));
    	assertEquals(sourcesToD, graph.sources(d));
    	assertEquals(targetsFromD, graph.targets(d));
    }
    
    //�����㲻��ͼ��
    @Test
    public void testSourcesAndTargetsNotInGraph() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	assertEquals(map, graph.sources(a));
    	assertEquals(map, graph.targets(a));
    }
}
