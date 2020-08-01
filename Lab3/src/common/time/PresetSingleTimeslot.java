package common.time;

/**
 * 一个immutable的类型，表示计划项运行过程中的起止时刻
 */
public class PresetSingleTimeslot implements PresetSingleTimeslotEntry {

	private final Timeslot timeslot;

	// Abstraction function:
	// AF(timeslot)=计划项运行过程中的起止时刻
	// Representation invariant:
	// timeslot!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定

	/**
	 * 创建起止时刻表
	 * 
	 * @param start 开始时间
	 * @param end   结束时间
	 */
	public PresetSingleTimeslot(String start, String end) {
		this.timeslot = new Timeslot(start, end);
	}

	@Override
	public Timeslot getTimeslot() {
		return timeslot;
	}
}