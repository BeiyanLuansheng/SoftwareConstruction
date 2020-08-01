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
	 * ���Թ��췽����
	 * ���ļ����������룬��������
	 * 
	 * ����poem������
	 * ���Žӵ�֣������룬�����Ͽ���û���Žӵ�����룬�����Ͽ������Žӵ������
	 * ��Ȩ�ط֣�Ȩ�ص���1��Ȩ�ش���1
	 * 
	 * ����toString������
	 * ��ͼ���ǿ�ͼ
	 */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //���ļ�����
    @Test
    public void testGraphPoetEmpty() throws IOException {
    	Graph<String> graph = new ConcreteEdgesGraph<String>();
    	assertEquals(graph.toString(), new GraphPoet(new File("test/P1/poet/empty.txt")).toString());
    }
    
    //��������
    @Test
    public void testGraphPoetSingleLine() throws IOException {
    	Graph<String> graph = new ConcreteEdgesGraph<String>();
    	graph.set("a", "b", 1);
    	graph.set("b", "c", 1);
    	graph.set("c", "d", 1);
    	graph.set("d", "d", 2);
    	assertEquals(graph.toString(), new GraphPoet(new File("test/P1/poet/singleLine.txt")).toString());
    }
    
    //��������
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
    
    //������
    @Test
    public void testPoemEmpty() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("", nimoy.poem(""));
    }
    
    //���ŽӴʵ�����
    @Test
    public void testPoemHasBridge() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("Explore strange New life And", nimoy.poem("Explore New And"));
    }
    
    //û���ŽӴʵ�����
    @Test
    public void testPoemNonBridge() throws IOException {
    	GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/poem.txt"));
    	assertEquals("Seek To Explore Life", nimoy.poem("Seek To Explore Life"));
    }
    
    //��ͼ
    @Test
    public void testToStringEmpty() throws IOException {
    	assertEquals("", new GraphPoet(new File("test/P1/poet/empty.txt")).toString());
    }
    
    //�ǿ�ͼ
    @Test
    public void testToStringNonEmpty() throws IOException {
    	String string = "a,b,c,d\n(a->b,1)\n(b->c,1)\n(c->d,1)\n(d->d,2)";
    	assertEquals(string, new GraphPoet(new File("test/P1/poet/singleLine.txt")).toString());
    }
}
