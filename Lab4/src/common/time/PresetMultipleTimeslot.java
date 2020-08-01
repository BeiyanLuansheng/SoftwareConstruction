package common.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个immutable的类型，表示计划项运行过程中的时刻表
 */
public class PresetMultipleTimeslot implements PresetMultipleTimeslotEntry {

	private final List<Timeslot> timeslot;

	// Abstraction function:
	// 以timeslot中的顺序排好序的，有timeslot.size()个个体的一组资源
	// Representation invariant:
	// timeslot!=null
	// timeslot.size()>0
	// 时刻表中的时间要按时间顺序排列
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 获得资源时用Collections.unmodifiableList()转化为不可变的List输出

	/**
	 * 创建一个时刻表
	 * 
	 * @param times 被预设的时间表，非空，times.size()>0，times.size() % 2 ==0，时间要按时间顺序排列
	 */
	public PresetMultipleTimeslot(List<String> times) {
		// times不满足前置条件，抛出异常
		if (times == null || times.size() == 0 || times.size() % 2 != 0)
			throw new IllegalArgumentException("参数不合法");
		List<Timeslot> timeslot = new ArrayList<>();
		for (int i = 0; i < times.size(); i += 2)
			timeslot.add(new Timeslot(times.get(i), times.get(i + 1)));
		for (int i = 0; i < timeslot.size() - 1; i++)
			if (timeslot.get(i).getEndTime().isAfter(timeslot.get(i + 1).getStartTime()))
				throw new IllegalArgumentException("参数顺序不合法");
		this.timeslot = timeslot;
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.timeslot != null;
		assert this.timeslot.size() > 0;
		for (int i = 0; i < timeslot.size() - 1; i++)
			if (timeslot.get(i).getEndTime().isAfter(timeslot.get(i + 1).getStartTime()))
				assert false;
	}

	@Override
	public List<Timeslot> getTimeslot() {
		checkRep();
		return Collections.unmodifiableList(timeslot);
	}

}
