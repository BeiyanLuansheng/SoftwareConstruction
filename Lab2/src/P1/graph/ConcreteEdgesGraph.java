/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	// Abstraction function:
	//   AF(vertices,edges)=以vertices中的点为顶点，edges中的边为边的有向图
	// Representation invariant:
	//   vertices[i]!=null
	//   edges[i] != null
	//   边数 <= 点数 * (点数 - 1)
	// Safety from rep exposure:
	//   所有的数据域都是私有的且使用final限定
	//   用Collections.unmodifiableSet()转化为不可变类型返回给外部

	public ConcreteEdgesGraph() {
	}

	/*
	 * 检查不变性
	 */
	private void checkRep() {
		assert !vertices.contains(null);
		assert !edges.contains(null);
		final int sizeOfEdges = edges.size();
		final int sizeOfVertices = vertices.size();
		int maxNumberOfEdges = sizeOfVertices * (sizeOfVertices - 1);
		assertTrue(maxNumberOfEdges >= sizeOfEdges);
	}

	@Override
	public boolean add(L vertex) {
		if (vertex == null || vertices.contains(vertex))
			return false;
		vertices.add(vertex);
		checkRep();
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {
		for (Edge<L> edge : edges)
			if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
				int oldWeight = edge.getWeight();
				edges.remove(edge);
				if (weight != 0)
					edges.add(new Edge<L>(source, target, weight));
				checkRep();
				return oldWeight;
			}

		if (!vertices.contains(source))
			vertices.add(source);
		if (!vertices.contains(target))
			vertices.add(target);
		if (weight != 0)
			edges.add(new Edge<L>(source, target, weight));
		checkRep();
		return 0;
	}

	@Override
	public boolean remove(L vertex) {
		if (!vertices.contains(vertex))
			return false;
		vertices.remove(vertex);
		Iterator<Edge<L>> iter = edges.iterator();
		while (iter.hasNext()) {
			Edge<L> edge = iter.next();
			if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex))
				iter.remove();
		}
		checkRep();
		return true;
	}

	@Override
	public Set<L> vertices() {
		return Collections.unmodifiableSet(vertices);
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sources = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getTarget().equals(target)) {
				sources.put(edge.getSource(), edge.getWeight());
			}
		}
		return Collections.unmodifiableMap(sources);
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> targets = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getSource().equals(source)) {
				targets.put(edge.getTarget(), edge.getWeight());
			}
		}
		return Collections.unmodifiableMap(targets);
	}

	@Override
	public String toString() {
		if (vertices.isEmpty())
			return "";
		else {
			StringBuilder string = new StringBuilder();
			for (L ver : vertices) {
				string.append(",");
				string.append(ver);
			}
			if (!edges.isEmpty()) {
				for (Edge<L> edge : edges) {
					string.append("\n");
					string.append(edge.toString());
				}
			}
			return string.toString().substring(1);
		}
	}
}

/**
 * specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {
	private final int weight;
	private final L source, target;

	// Abstraction function:
	//   AF(source,target,weight)=source到target的一条边
	// Representation invariant:
	//   source,target!=null
	//   weight>0
	// Safety from rep exposure:
	//   所有的数据域都是私有的且使用final限定

	/**
	 * 创建一条边
	 * 
	 * @param source 不为空的起点
	 * @param target 不为空的终点
	 * @param weight 正整数的边权
	 */
	public Edge(L source, L target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		checkRep();
	}

	/*
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.source != null;
		assert this.target != null;
		assert this.weight > 0;
	}

	/**
	 * @return 获得边的起点 source
	 */
	public L getSource() {
		return source;
	}

	/**
	 * @return 获得边的终点 target
	 */
	public L getTarget() {
		return target;
	}

	/**
	 * @return 获得权重 weight
	 */
	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "(" + source + "->" + target + "," + weight + ")";
	}

}
