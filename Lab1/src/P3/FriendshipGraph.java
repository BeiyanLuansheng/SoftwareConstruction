package P3;

import java.util.*;

public class FriendshipGraph{
	 public List<Person> personList;
	 FriendshipGraph(){
		 personList = new ArrayList<>();
	 }
	 
	 /**
	  * Add a person to the friendship graph
	  * Determine if the name is repeated
	  */
	 public void addVertex(Person person) {
		 for(Person p:personList) {
			 if(p.getName().equals(person.getName())) {
				 System.out.println("Name repetition!!!");
				 System.exit(0);
			 }
		 }
		 personList.add(person);
	 }
	 
	 /**
	  * Add an edge from person1 to person2 to the friendship graph
	  * @param person1 the begin vertex of the edge
	  * @param person2 the end vertex of the edge
	  */
	 public void addEdge(Person person1, Person person2) {
		 person1.addFriend(person2);
	 }
	 
	 /**
	  * Compute the distance between two people in the friendship graph
	  * @param person1 one person
	  * @param person2 another person
	  * @return -1: the two people are not connected
	  * 		0: person1 and person2 are the same person
	  * 		distance: the shortest distance between the people
	  */
	 public int getDistance(Person person1, Person person2) {
		 int distance = 0;
		 if(person1 == person2) return distance;
		 for(int i=0; i<personList.size(); i++) {
			 personList.get(i).setVisited(false);
			 personList.get(i).setDistance(0);
		 }
		 Queue<Person> queue = new LinkedList<>();
		 queue.offer(person1);
		 while(!queue.isEmpty()) {
			 Person currentPerson = queue.poll();
			 if(!currentPerson.getVisited()) {
				 currentPerson.setVisited(true);
				 if(currentPerson.getFriend().contains(person2))
					 return ++distance;
				 distance = currentPerson.getDistance() + 1;
				 for(Person person3:currentPerson.getFriend()) {
					 if(!person3.getVisited()) {
						 queue.offer(person3);
						 person3.setDistance(distance);
						 if (person3.getFriend().contains(person2))
							 return distance+1;
					 }
				 }
			 }
		 }
		 return -1;
	 }
	 
	 /**
	  * ¿Í»§¶Ë³ÌÐò
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