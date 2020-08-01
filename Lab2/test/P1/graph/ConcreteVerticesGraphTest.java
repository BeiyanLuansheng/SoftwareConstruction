/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //    ��ͼ��ͼ��ֻ�е�û�бߣ�ͼ�ڼ��е����б�

    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString() {
    	Graph<String> graph = emptyInstance();
    	//��ͼ
    	assertEquals("", graph.toString());
    	String a = "A";
    	String b = "B";
    	//ͼ��ֻ�е�û�б�
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertEquals("A\nB\n", graph.toString());
    	//ͼ�ڼ��е����б�
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals("A:{B=1}\nB\n", graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    // Testing strategy for Vertex
    // getter������
    // ���Զ���һ�����Ƿ��ܹ�����������ĵ�����ƺ�����Ϊ���ı߼�
    // add������
    // ������ı߷֣�ԭ�Ȳ����������ߣ�ԭ�ȴ���������
    // remove������
    // ����ɾ���ı߷֣������������ߣ�����������
    // toString������
    // ���߷֣�ֻ�ж���û����֮Ϊ���ıߣ�����֮Ϊ���ı�
    
    //tests for Vertex.getVertex
    @Test
    public void testGetVertex() {
    	String a = "A";
    	Vertex<String> vertex = new Vertex<>(a);
    	assertEquals(a, vertex.getVertex());
    }
    
    //tests for Vertex.geTarget
    @Test
    public void testGetTargets() {
    	String a = "A";
    	String b = "B";
    	String c = "C";
    	Map<String, Integer> targets = new HashMap<String, Integer>();
    	targets.put(b, 1);
    	targets.put(c, 2);
    	Vertex<String> vertex = new Vertex<>(a);
    	vertex.add(b, 1);
    	vertex.add(c, 2);
    	assertEquals(targets, vertex.getTargets());
    }
    
    //tests for Vertex.add
    @Test
    public void testAdd() {
    	String a = "A";
    	String b = "B";
    	Vertex<String> vertex = new Vertex<>(a);
    	//ԭ�Ȳ�����������
    	assertEquals(0, vertex.add(b, 1));
    	//ԭ�ȴ���������
    	assertEquals(1, vertex.add(b, 2));
    }
    
    //tests for Vertex.add
    @Test
    public void testRemove() {
    	String a = "A";
    	String b = "B";
    	Vertex<String> vertex = new Vertex<>(a);
    	//������������
    	assertEquals(0, vertex.remove(b));
    	//����������
    	vertex.add(b, 2);
    	assertEquals(2, vertex.remove(b));
    }
    
    //tests for Vertex.add
    //ֻ�ж���û����֮Ϊ���ı�
    @Test
    public void testVertextToString1() {
    	String a = "A";
    	Vertex<String> vertex = new Vertex<>(a);
    	assertEquals("A", vertex.toString());
    }
    
    //����֮Ϊ���ı�
    @Test
    public void testVertextToString2() {
    	String a = "A";
    	String b = "B";
    	String c = "C";
    	Vertex<String> vertex = new Vertex<>(a);
    	vertex.add(b, 2);
    	vertex.add(c, 3);
    	assertEquals("A:{B=2, C=3}", vertex.toString());
    }
}
