package common.resource;

import java.util.Collections;
import java.util.List;

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
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 获得资源时用Collections.unmodifiableList()转化为不可变的List输出

	/**
	 * 创建一个资源组
	 * 
	 * @param train 指定的资源组，非空，元素个数大于0
	 */
	public MultipleSortedResource(List<R> train) {
		this.train = train;
	}

	@Override
	public List<R> getResources() {
		return Collections.unmodifiableList(train);
	}
}
