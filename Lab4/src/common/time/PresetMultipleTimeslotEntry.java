package common.time;

import java.util.List;

/**
 * 表示被预设好的一组有序的时间表
 */
public interface PresetMultipleTimeslotEntry {

	/**
	 * 获得时刻表
	 * 
	 * @return 一个时刻表
	 */
	public List<Timeslot> getTimeslot();
}
