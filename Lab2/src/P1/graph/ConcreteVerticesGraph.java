/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	//   AF(vertices) = 点集为vertices的图，vertex中存储相应单向边的关系
	// Representation invariant:
	//   vertices[i] != null
	//   vertices中不能有重复点
	// Safety from rep exposure:
	//   设置数据域为私有的，且使用final限定
	//   获得所有点时，创建一个新的Set集合存入所有的点，再返回给外部

	public ConcreteVerticesGraph() {
	}
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert !vertices.contains(null);
		for(int i=0; i<vertices.size()-1; i++) {
			for(int j=i+1; j<vertices.size(); j++) {
				if(vertices.get(i).getVertex().equals(vertices.get(j).getVertex()))
					assert false;
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		if(vertex == null) return false;
		for(Vertex<L> v:vertices)
			if(v.getVertex().equals(vertex))
				return false;
		vertices.add(new Vertex<L>(vertex));
		checkRep();
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {
		Vertex<L> sourceVertex = null;
		Vertex<L> targetVertex = null;
		int previousWeight;
		for(Vertex<L> v:vertices)
			if(v.getVertex().equals(source))
				sourceVertex = v;
			else if(v.getVertex().equals(target))
				targetVertex = v;
		if(targetVertex == null)
			vertices.add(new Vertex<L>(target));
		if(sourceVertex == null) {
			sourceVertex = new Vertex<L>(source);
			if(weight != 0) sourceVertex.add(target, weight);
			vertices.add(sourceVertex);
			previousWeight = 0;
		}
		else if(weight != 0)
			previousWeight = sourceVertex.add(target, weight);
		else 
			previousWeight = sourceVertex.remove(target);
		checkRep();
		return previousWeight;
	}

	@Override
	public boolean remove(L vertex) {
		for(Vertex<L> v:vertices) 
			if(v.getTargets().containsKey(vertex)) 
				v.remove(vertex);
		
		for(Vertex<L> v:vertices)
			if(v.getVertex().equals(vertex)) {
				vertices.remove(v);
				return true;
			}
		return false;
	}

	@Override
	public Set<L> vertices() {
		Set<L> ans = new HashSet<>();
		for(Vertex<L> v:vertices) {
			ans.add(v.getVertex());
		}
		return Collections.unmodifiableSet(ans);
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sources = new HashMap<L, Integer>();
		for(Vertex<L> v:vertices)
			if(v.getTargets().containsKey(target))
				sources.put(v.getVertex(), v.getTargets().get(target));
		
		return Collections.unmodifiableMap(sources);
	}

	@Override
	public Map<L, Integer> targets(L source) {
		for(Vertex<L> v:vertices)
			if(v.getVertex().equals(source))
				return Collections.unmodifiableMap(v.getTargets());
		return new HashMap<L, Integer>();
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for(Vertex<L> v:vertices) {
			string.append(v.toString());
			string.append("\n");
		}
		return string.toString();
	}
}

/**
 * specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {
	private final L vertex;
	private final Map<L, Integer> targets;
	
	// Abstraction function:
	//   AF(vertex,targets)=以vertex为起点，targets中的key为终点，value为权重的所有边
	// Representation invariant:
	//   vertex != null
	//   所有终点都不能为null
	//   所有边的权重都大于0
	// Safety from rep exposure:
	//   所有数据域都是私有的且用final做了限定
	//   获得targets时用Collections.unmodifiableMap(targets)转换为了不可修改的量
	
	/**
	 * 创建一个顶点
	 * @param vertex 顶点的label
	 */
	public Vertex(L vertex) {
		this.vertex = vertex;
		this.targets = new HashMap<L, Integer>();
		checkRep();
	}
	
	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.vertex != null;
		assert !this.targets.keySet().contains(null);
		assert !this.targets.containsValue(0);
	}

	/**
	 * 获得顶点的label
	 * @return vertex
	 */
	public L getVertex() {
		return vertex;
	}
	
	/**
	 * @return 以vertex为起点的边集targets
	 */
	public Map<L, Integer> getTargets(){
		return Collections.unmodifiableMap(targets);
	}
	
	/**
	 * 添加以vertex为起点以target为终点，weight为权重的边
	 * @param target 以vertex为起点的终点
	 * @param weight 以vertex为起点到target的正整数权重
	 * @return 非0 被修改边的原来的权重
	 *          0 原来不存在边
	 */
	public int add(L target, int weight) {
		Integer previousWeight = targets.put(target, weight);
		checkRep();
		if(previousWeight == null) return 0;
		else return previousWeight; 
	}

	/**
	 * 删除到target这个终点的边
	 * @param target 目标点
	 * @return 非0  被删除的边的权重
	 *          0  没有到target的边
	 */
	public int remove(L target) {
		Integer previousWeight = targets.remove(target);
		if(previousWeight == null) previousWeight = 0;
		checkRep();
		return previousWeight;
	}

	@Override
	public String toString() {
		if (targets.isEmpty())
			return vertex.toString();
		else 
			return vertex + ":" + targets.toString();
	}
}
