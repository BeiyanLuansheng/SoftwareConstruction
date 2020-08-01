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
	//   AF(vertices) = �㼯Ϊvertices��ͼ��vertex�д洢��Ӧ����ߵĹ�ϵ
	// Representation invariant:
	//   vertices[i] != null
	//   vertices�в������ظ���
	// Safety from rep exposure:
	//   ����������Ϊ˽�еģ���ʹ��final�޶�
	//   ������е�ʱ������һ���µ�Set���ϴ������еĵ㣬�ٷ��ظ��ⲿ

	public ConcreteVerticesGraph() {
	}
	
	/**
	 * ��鲻����
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
	//   AF(vertex,targets)=��vertexΪ��㣬targets�е�keyΪ�յ㣬valueΪȨ�ص����б�
	// Representation invariant:
	//   vertex != null
	//   �����յ㶼����Ϊnull
	//   ���бߵ�Ȩ�ض�����0
	// Safety from rep exposure:
	//   ������������˽�е�����final�����޶�
	//   ���targetsʱ��Collections.unmodifiableMap(targets)ת��Ϊ�˲����޸ĵ���
	
	/**
	 * ����һ������
	 * @param vertex �����label
	 */
	public Vertex(L vertex) {
		this.vertex = vertex;
		this.targets = new HashMap<L, Integer>();
		checkRep();
	}
	
	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.vertex != null;
		assert !this.targets.keySet().contains(null);
		assert !this.targets.containsValue(0);
	}

	/**
	 * ��ö����label
	 * @return vertex
	 */
	public L getVertex() {
		return vertex;
	}
	
	/**
	 * @return ��vertexΪ���ı߼�targets
	 */
	public Map<L, Integer> getTargets(){
		return Collections.unmodifiableMap(targets);
	}
	
	/**
	 * �����vertexΪ�����targetΪ�յ㣬weightΪȨ�صı�
	 * @param target ��vertexΪ�����յ�
	 * @param weight ��vertexΪ��㵽target��������Ȩ��
	 * @return ��0 ���޸ıߵ�ԭ����Ȩ��
	 *          0 ԭ�������ڱ�
	 */
	public int add(L target, int weight) {
		Integer previousWeight = targets.put(target, weight);
		checkRep();
		if(previousWeight == null) return 0;
		else return previousWeight; 
	}

	/**
	 * ɾ����target����յ�ı�
	 * @param target Ŀ���
	 * @return ��0  ��ɾ���ıߵ�Ȩ��
	 *          0  û�е�target�ı�
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
