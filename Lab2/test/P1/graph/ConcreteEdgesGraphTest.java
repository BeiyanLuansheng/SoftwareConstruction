/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
	//   ��ͼ��ͼ���е㵫û�бߣ�ͼ�ڼ��е����б�
    
    // tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString() {
    	Graph<String> graph = emptyInstance();
    	//��ͼ
    	assertEquals("", graph.toString());
    	String a = "A";
    	String b = "B";
    	//ͼ��ֻ�е㣬û�б�
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertEquals("A,B", graph.toString());
    	//ͼ�ڼ��е����б�
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals("A,B\n(A->B,1)", graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    // Testing strategy for Edge
    // getter���������Զ���һ�����Ƿ��ܹ�����������ĸ�������
    // toString���������Զ���һ�����ܹ�����Ԥ�ڵ���ʽ���
    
    // tests for Edge.getSource
    @Test
    public void testGetSource() {
    	Edge<String> edge = new Edge<String>("a", "b", 5);
    	assertEquals("a", edge.getSource());
    }
    
    // tests for Edge.getTarget
    @Test
    public void testGetTarget() {
    	Edge<String> edge = new Edge<String>("a", "b", 5);
    	assertEquals("b", edge.getTarget());
    }
    
    // tests for Edge.getWeight
    @Test
    public void testGetWeight() {
    	Edge<String> edge = new Edge<String>("a", "b", 5);
    	assertEquals(5, edge.getWeight());
    }
    
    // tests for Edge.toString
    @Test
    public void testEdgeToString() {
    	Edge<String> edge = new Edge<String>("a", "b", 5);
    	assertEquals("(a->b,5)", edge.toString());
    }
}
