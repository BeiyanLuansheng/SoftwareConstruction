package P3;

import java.util.*;

public class Person{
	private String name;
	private boolean visited; 
	private Set<Person> friend;
	private int distance;
	Person(String name){
		this.name = name;
		this.visited = false;
		this.friend = new HashSet<Person>();
		this.distance = 0;
	}
	
	/**
	 * Get the name of the Person object
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the status of the Person object
	 * @return  true: has visited
	 *			false: not visited
	 */
	public boolean getVisited() {
		return visited;
	}
	
	/**
	 * set visit status
	 * @param visitStatus the visit status to set
	 */
	public void setVisited(boolean visitStatus) {
		visited = visitStatus;
	}
	
	/**
	 * get the set of friends
	 * @return a set of friends
	 */
	public Set<Person> getFriend(){
		return friend;
	}
	
	/**
	 * Add a person to the set of friend
	 * @param person the person to be added
	 */
	public void addFriend(Person person) {
		friend.add(person);
	}
	
	/**
	 * Get the distance from one person to this person
	 * @return an integer representing distance
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Set the distance from one person to this person
	 * @param distance an integer representing distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
}