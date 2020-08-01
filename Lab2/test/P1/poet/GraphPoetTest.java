/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import P1.graph.ConcreteEdgesGraph;
import P1.graph.Graph;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
	/**
	 * 测试构造方法：
	 * 空文件，单行输入，多行输入
	 * 
	 * 测试poem方法：
	 * 按桥接点分：空输入，在语料库中没有桥接点的输入，在语料库中有桥接点的输入
	 * 按权重分：权重等于1，权重大于1
	 * 
	 * 测试toString方法：
	 * 空图，非空图
	 */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //空文件输入
    @Test
    public void testGraphPoetEmpty() throws IOException {
    	Graph<String> graph = new ConcreteEdgesGraph<String>();
    	assertEquals(graph.toString(), new GraphPoet(new File("test/P1/poet/empty.txt")).toString());
    }
    
    //单行输入
    @Test
    public void testGraphPoetSingleLine() throws IOException {
    	Graph<String> graph = new ConcreteEdgesGraph<String>();
    	graph.set("a", "b", 1);
    	graph.set("b", "c", 1);
    	graph.set("c", "d", 1);
    	graph.set("d", "d", 2);
    	assertEquals(graph.toString(), new GraphPoet(new File("test/P1/poet/singleLine.txt")).toString());
    }
    
    //多行输入
    @Test
    public void testGraphPoetMultipleLines() throws IOException {
    	Graph<String> graph = new ConcreteEdgesGraph<String>();
    	graph.set("a", "b", 1);
    	graph.set("b", "c", 1);
    	graph.set("c", "d", 1);
    	graph.set("d", "d", 2);
    	graph.set("d", "the", 1);
    	graph.set("the", "second", 1);
    	graph.set("second", "line", 1);
    	assertEquals(graph.toString(), new GraphPoet(new File("test/P1/poet/multipleLines.txt")).toString());
    }
    
    //空输入
    @Test
    public void testPoemEmpty() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("", nimoy.poem(""));
    }
    
    //有桥接词的输入
    @Test
    public void testPoemHasBridge() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("Explore strange New life And", nimoy.poem("Explore New And"));
    }
    
    //没有桥接词的输入
    @Test
    public void testPoemNonBridge() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("Seek To Explore Life", nimoy.poem("Seek To Explore Life"));
    }
    
    //空图
    @Test
    public void testToStringEmpty() throws IOException {
    	assertEquals("", new GraphPoet(new File("test/P1/poet/empty.txt")).toString());
    }
    
    //非空图
    @Test
    public void testToStringNonEmpty() throws IOException {
    	String string = "a,b,c,d\n(a->b,1)\n(b->c,1)\n(c->d,1)\n(d->d,2)";
    	assertEquals(string, new GraphPoet(new File("test/P1/poet/singleLine.txt")).toString());
    }
}
