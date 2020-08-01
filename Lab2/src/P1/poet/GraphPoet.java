/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P1.graph.ConcreteEdgesGraph;
import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph;

	// Abstraction function:
	//   是一个利用加权有向图寻找两点中间有无通路的方法，到实际给一句话添加中间词的问题的映射
	// Representation invariant:
	//   graph != null
	// Safety from rep exposure:
	//   所有的数据域都是私有的且使用final做了限定

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus text file from which to derive the poet's affinity graph
	 * @throws IOException if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {
		graph = new ConcreteEdgesGraph<String>();
		BufferedReader input = new BufferedReader(new FileReader(corpus));
		String line = input.readLine();
		List<String> words = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (line != null) {
			words.addAll(Arrays.asList(line.split(" ")));
			line = input.readLine();
		}
		input.close();
		for (int i = 0; i < words.size() - 1; i++) {
			String source = words.get(i).toLowerCase();
			String target = words.get(i + 1).toLowerCase();
			if (map.containsKey(source + "->" + target))
				map.put(source + "->" + target, map.get(source + "->" + target) + 1);
			else
				map.put(source + "->" + target, 1);
			graph.set(source, target, map.get(source + "->" + target));
		}
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert graph != null;
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		if (input == null)
			return "";
		List<String> words = Arrays.asList(input.split(" "));
		StringBuffer poem = new StringBuffer().append(words.get(0));
		for (int i = 0; i < words.size() - 1; i++) {
			String source = words.get(i).toLowerCase();
			String nextSource = words.get(i + 1).toLowerCase();
			Map<String, Integer> bridges = graph.targets(source);
			String bridgeWord = null;
			int maxWeight = 0;
			for (String bridge : bridges.keySet()) {
				int sourceToBridge = bridges.get(bridge);
				Integer bridgeToNextSource = graph.targets(bridge).get(nextSource);
				if ((bridgeToNextSource != null) && (sourceToBridge + bridgeToNextSource > maxWeight)) {
					maxWeight = sourceToBridge + bridgeToNextSource;
					bridgeWord = bridge;
				}
			}
			if (bridgeWord != null)
				poem.append(" " + bridgeWord + " " + words.get(i + 1));
			else
				poem.append(" " + words.get(i + 1));
		}
		return poem.toString();
	}

	@Override
	public String toString() {
		return graph.toString();
	}
}
