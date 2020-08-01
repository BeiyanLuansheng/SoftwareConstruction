package common.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个immutable的类型，表示计划项运行过程中的时刻表
 */
public class PresetMultipleTimeslot implements PresetMultipleTimeslotEntry {

	private final List<Timeslot> timeslot = new ArrayList<>();

	// Abstraction function:
	// 以timeslot中的顺序排好序的，有timeslot.size()个个体的一组资源
	// Representation invariant:
	// timeslot!=null
	// timeslot.size()>0
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 获得资源时用Collections.unmodifiableList()转化为不可变的List输出

	/**
	 * 创建一个时刻表
	 * 
	 * @param times 被预设的时间表，非空，times.size()>0
	 */
	public PresetMultipleTimeslot(List<String> times) {
		for (int i = 0; i < times.size(); i += 2) {
			this.timeslot.add(new Timeslot(times.get(i), times.get(i + 1)));
		}
	}

	@Override
	public List<Timeslot> getTimeslot() {
		return Collections.unmodifiableList(timeslot);
	}

}
