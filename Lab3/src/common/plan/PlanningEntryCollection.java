package common.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 一个mutable的类，PlanningEntry的集合类
 * 
 * @param <R> 资源类型
 */
public class PlanningEntryCollection<R> {

	private final List<PlanningEntry<R>> entries = new ArrayList<>();

	// Abstraction function:
	// AF(entries)=现实中的计划项清单
	// Representation invariant:
	// entries!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的用final限定
	// 获得entries时使用Collections.unmodifiableList()转化为不可更改的list返回

	/**
	 * 增加一条计划项
	 * 
	 * @param entry 待增加的计划项
	 * @return false 添加失败，计划项为空 true 添加成功
	 */
	public boolean addEntries(PlanningEntry<R> entry) {
		if (entry == null)
			return false;
		entries.add(entry);
		return true;
	}

	/**
	 * 获得所有的计划项列表
	 * 
	 * @return 计划项列表
	 */
	public List<PlanningEntry<R>> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	/**
	 * 计划项迭代器
	 * 
	 * @return 迭代器
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		return entries.iterator();
	}

	/**
	 * 把计划项按开始时间从早到晚排序
	 */
	public void sort() {
		entries.sort(null);
	}
}
