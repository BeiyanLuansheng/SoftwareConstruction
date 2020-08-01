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
	//   空图，图内有点但没有边，图内既有点又有边
    
    // tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString() {
    	Graph<String> graph = emptyInstance();
    	//空图
    	assertEquals("", graph.toString());
    	String a = "A";
    	String b = "B";
    	//图内只有点，没有边
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertEquals("A,B", graph.toString());
    	//图内既有点又有边
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals("A,B\n(A->B,1)", graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    // Testing strategy for Edge
    // getter方法：测试对于一条边是否能够正常获得它的各个属性
    // toString方法：测试对于一条边能够按照预期的形式输出
    
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
