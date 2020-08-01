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
	 * ��ͼ������µĶ��㣬����
	 * @param person ����
	 */
	public void addVertex(Person person) {
		if(!graph.add(person))
			System.exit(0);
	}

	/**
	 * ��ͼ������µıߣ��˼ʹ�ϵ
	 * @param person1 �ߵ����
	 * @param person2 �ߵ��յ�
	 */
	public void addEdge(Person person1, Person person2) {
		graph.set(person1, person2, 1);
	}

	/**
	 * ������֮�����̾���
	 * @param person1 ��һ����
	 * @param person2 �ڶ�����
	 * @return 0 ����������ͬһ����
	 *         -1 �������˲����ڹ�ϵ
	 *         ������ ��������֮�����̾���
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
	 * �ͻ��˳���
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
