package P3;

import static org.junit.Assert.*;
import org.junit.Test;
import P3.Person;
import P3.FriendshipGraph;

public class FriendshipGraphTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	/**
	 * Test addVertex.
	 */
	@Test
	public void addVertexTest() {
		Person a = new Person("A");
		Person b = new Person("B");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		assertEquals(a, graph.personList.get(0));
		graph.addVertex(b);
		assertEquals(b, graph.personList.get(1));
		//名字重复测试。由于测试这部分时会退出程序，所以注释了这部分
		//Person b2 = new Person("B");
		//graph.addVertex(b2);
	}
	
	/**
	 * Test addEdge.
	 */
	@Test
	public void addEdgeTest() {
		Person a = new Person("A");
		Person b = new Person("B");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
		//添加单向边
		graph.addEdge(a, b);
		assertTrue(a.getFriend().contains(b));
		assertFalse(b.getFriend().contains(a));
		//添加双向边
		graph.addEdge(b, a);
		assertTrue(b.getFriend().contains(a));
	}
	
	/**
	 * Test getDistance.
	 */
	/**
	 * 到达自身
	 */
	@Test
	public void getDistance1() {
		Person a = new Person("A");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		assertEquals(0, graph.getDistance(a, a));
	}
	
	/**
	 * 不能到达：a  b
	 */
	@Test
	public void getDistance2() {
		Person a = new Person("A");
		Person b = new Person("B");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
		assertEquals(-1, graph.getDistance(a, b));
	}
	
	/**
	 * 单向到达：a-->b<-->c
	 */
	@Test
	public void getDistance3() {
		Person a = new Person("A");
		Person b = new Person("B");
		Person c = new Person("C");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addEdge(a, b);
		graph.addEdge(b, c);
		graph.addEdge(c, b);
		assertEquals(1, graph.getDistance(a, b));
		assertEquals(-1, graph.getDistance(b, a));
		assertEquals(2, graph.getDistance(a, c));
		assertEquals(-1, graph.getDistance(c, a));
	}
	
	/**
	 * 多条路径
	 * 	    b
	 *   / /  \
	 * 	/ /    \
	 * a--e--f--c
	 *  \ \   /
	 *   \ \ / 
	 *     d
	 */
	@Test
	public void getDistance4() {
		Person a = new Person("A");
		Person b = new Person("B");
		Person c = new Person("C");
		Person d = new Person("D");
		Person e = new Person("E");
		Person f = new Person("F");
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addEdge(a, b);	graph.addEdge(b, a);
		graph.addEdge(b, c);	graph.addEdge(c, b);
		graph.addEdge(c, d);	graph.addEdge(d, c);
		graph.addEdge(d, a);	graph.addEdge(a, d);
		graph.addEdge(a, e);	graph.addEdge(e, a);
		graph.addEdge(e, f);	graph.addEdge(f, e);
		graph.addEdge(f, c);	graph.addEdge(c, f);
		graph.addEdge(b, f);	graph.addEdge(f, b);
		graph.addEdge(e, d);	graph.addEdge(d, e);
		assertEquals(2, graph.getDistance(a, c));
	}
}