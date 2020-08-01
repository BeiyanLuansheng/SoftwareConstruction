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
     * Graph.add方法测试
     * 点在图中，点不在图中
     * 
     * Graph.set方法测试
     * 按起点分：点在图中，点不在图中
     * 按终点分：点在图中，点不在图中
     * 按权重分：权不等于0，权等于0
     * 
     * Graph.remove方法测试
     * 按点是否在图中分：点在图中，点不在图中
     * 按点是否有以其为顶点的边分：点没有相关边，点有相关边
     * 
     * Graph.vertices方法测试
     * 图中没有点，图中有多个点
     * 
     * Graph.sources方法、Graph.targets方法测试
     * 给定点不在图中
     * 给定点在图中：图中没有相应的点，有相应的点
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
    
    //起点不在图中，终点不在图中，权重为0
    //起点在图中，终点在图中，权重不为0
    @Test
    public void testSet1() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 2));
    }
    
    //起点不在图中，终点不在图中，权重不为0
    //起点在图中，终点在图中，权重为0
    @Test
    public void testSet2() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
    
    //起点不在图中，终点在图中，权重为0
    @Test
    public void testSet3() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(b));
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
  
    //起点不在图中，终点在图中，权重不为0
    @Test
    public void testSet4() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(b));
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals(1, graph.set(a, b, 2));
    }
    
    //起点在图中，终点不在图中，权重为0
    @Test
    public void testSet5() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	String b = "B";
    	assertTrue(graph.add(a));
    	assertEquals(0, graph.set(a, b, 0));
    	assertEquals(0, graph.set(a, b, 1));
    }
    
    //起点在图中，终点不在图中，权重不为0
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
    	//点在图中
    	assertTrue(graph.remove(a));
    	//点不在图中
    	assertFalse(graph.remove(a));
    }
    
    //点没有相关边
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
    
    //点有相关边
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
    
    //给定点在图中
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
    
    //给定点不在图中
    @Test
    public void testSourcesAndTargetsNotInGraph() {
    	Graph<String> graph = emptyInstance();
    	String a = "A";
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	assertEquals(map, graph.sources(a));
    	assertEquals(map, graph.targets(a));
    }
}
