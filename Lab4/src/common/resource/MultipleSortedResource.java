package common.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一个immutable的类型，表示有序的一组资源
 * 
 * @param <R> 资源类型
 */
public class MultipleSortedResource<R> implements MultipleSortedResourceEntry<R> {

	private final List<R> train;

	// Abstraction function:
	// 以train中的顺序排好序的，有train.size()个个体的一组资源
	// Representation invariant:
	// train!=null
	// train.size()>0
	// train中不能有重复的车厢
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 获得资源时用Collections.unmodifiableList()转化为不可变的List输出

	/**
	 * 创建一个资源组
	 * 
	 * @param train 指定的资源组，非空，元素个数大于0
	 */
	public MultipleSortedResource(List<R> train) {
		// 不满足非空或元素个数大于0的前置条件，抛出异常
		if (train == null || train.size() == 0)
			throw new IllegalArgumentException("空元素");
		Set<R> carrigeSet = new HashSet<>();
		for (R carriage : train) {
			if (carrigeSet.contains(carriage))
				throw new IllegalArgumentException("车厢资源有重复");
			carrigeSet.add(carriage);
		}
		this.train = train;
		checkRep();
	}

	private void checkRep() {
		assert this.train != null;
		assert this.train.size() > 0;
		Set<R> carrigeSet = new HashSet<>();
		for (R carriage : this.train) {
			if (carrigeSet.contains(carriage))
				assert false;
			carrigeSet.add(carriage);
		}
	}

	@Override
	public List<R> getResources() {
		checkRep();
		return Collections.unmodifiableList(train);
	}
}
