package P2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.*;

public class FriendshipGraph {
	final Graph<Person> graph;

	public FriendshipGraph() {
		graph = new ConcreteVerticesGraph<>();
	}

	/**
	 * 向图中添加新的顶点，新人
	 * @param person 新人
	 */
	public void addVertex(Person person) {
		if(!graph.add(person))
			System.exit(0);
	}

	/**
	 * 在图中添加新的边，人际关系
	 * @param person1 边的起点
	 * @param person2 边的终点
	 */
	public void addEdge(Person person1, Person person2) {
		graph.set(person1, person2, 1);
	}

	/**
	 * 两个人之间的最短距离
	 * @param person1 第一个人
	 * @param person2 第二个人
	 * @return 0 这两个人是同一个人
	 *         -1 这两个人不存在关系
	 *         正整数 这两个人之间的最短距离
	 */
	public int getDistance(Person person1, Person person2) {
		if (person1.equals(person2))
			return 0;
		Queue<Person> queue = new LinkedList<>();
		Map<Person, Integer> distant = new HashMap<>();
		queue.offer(person1);
		distant.put(person1, 0);
		while (!queue.isEmpty()) {
			Person head = queue.poll();
			int nowDistant = distant.get(head);
			Set<Person> friendList = graph.targets(head).keySet();
			for (Person person : friendList)
				if (!distant.containsKey(person)) {
					distant.put(person, nowDistant + 1);
					queue.offer(person);
					if (person == person2)
						return distant.get(person2);
				}
		}
		return -1;
	}

	/**
	 * 客户端程序
	 */
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross)); // should print 1
		System.out.println(graph.getDistance(rachel, ben)); // should print 2
		System.out.println(graph.getDistance(rachel, rachel)); // should print 0
		System.out.println(graph.getDistance(rachel, kramer)); // should print -1
	}
}
