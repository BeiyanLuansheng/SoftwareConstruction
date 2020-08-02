package common.time;

/**
 * 一个代表被预设的起止时刻的时刻表的接口
 */
public interface PresetSingleTimeslotEntry {

	/**
	 * 获得起止时刻
	 * 
	 * @return 一个有起止时刻的时刻表
	 */
	public Timeslot getTimeslot();
}