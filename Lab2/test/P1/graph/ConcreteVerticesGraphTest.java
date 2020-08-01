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
    //    空图，图内只有点没有边，图内既有点又有边

    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString() {
    	Graph<String> graph = emptyInstance();
    	//空图
    	assertEquals("", graph.toString());
    	String a = "A";
    	String b = "B";
    	//图内只有点没有边
    	assertTrue(graph.add(a));
    	assertTrue(graph.add(b));
    	assertEquals("A\nB\n", graph.toString());
    	//图内既有点又有边
    	assertEquals(0, graph.set(a, b, 1));
    	assertEquals("A:{B=1}\nB\n", graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    // Testing strategy for Vertex
    // getter方法：
    // 测试对于一个点是否能够正常获得它的点的名称和以它为起点的边集
    // add方法：
    // 按加入的边分：原先不存在这条边，原先存在这条边
    // remove方法：
    // 按被删除的边分：不存在这条边，存在这条边
    // toString方法：
    // 按边分：只有顶点没有以之为起点的边，有以之为起点的边
    
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
    	//原先不存在这条边
    	assertEquals(0, vertex.add(b, 1));
    	//原先存在这条边
    	assertEquals(1, vertex.add(b, 2));
    }
    
    //tests for Vertex.add
    @Test
    public void testRemove() {
    	String a = "A";
    	String b = "B";
    	Vertex<String> vertex = new Vertex<>(a);
    	//不存在这条边
    	assertEquals(0, vertex.remove(b));
    	//存在这条边
    	vertex.add(b, 2);
    	assertEquals(2, vertex.remove(b));
    }
    
    //tests for Vertex.add
    //只有顶点没有以之为起点的边
    @Test
    public void testVertextToString1() {
    	String a = "A";
    	Vertex<String> vertex = new Vertex<>(a);
    	assertEquals("A", vertex.toString());
    }
    
    //有以之为起点的边
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
