package flight;

import common.location.Location;
import common.location.TwoLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.time.PresetSingleTimeslotEntry;
import common.time.Timeslot;

/**
 * 一个mutable的类，表示一个航班
 *
 * @param <R>资源类型
 */
public class FlightEntry<R> extends CommonPlanningEntry<R> implements TwoLocationEntry, PresetSingleTimeslotEntry {

	private final TwoLocationEntry twoLocations;
	private final PresetSingleTimeslotEntry timeslot, tempTimeslot;
	private R plane;

	// Abstraction function:
	// AF(twoLocations, timeslot, plane)=一个timeslot.getStartTime()从
	// twoLocations.getDeparture()出发timeslot.getEndTime()到达twoLocations.getArrival()的航班
	// Representation invariant:
	// twoLocations!=null
	// timeslot!=null
	// Safety from rep exposure:
	// twoLocations,timeslot数据域都是私有的用final限定
	// plane是私有的

	/**
	 * 创建一个航班计划项
	 * 
	 * @param name         航班的名称，非空
	 * @param twoLocations 航班的起止地点，非空
	 * @param timeslot     航班的起止时间，非空
	 * @param tempTimeslot 中转的起止时间
	 */
	public FlightEntry(String name, TwoLocationEntry twoLocations, PresetSingleTimeslotEntry timeslot, PresetSingleTimeslotEntry tempTimeslot) {
		super(name);
		this.twoLocations = twoLocations;
		this.timeslot = timeslot;
		this.tempTimeslot = tempTimeslot;
	}

	/**
	 * 为航班分配资源
	 * 
	 * @param plane 待分配的资源，非空
	 * @return false 分配失败 true 分配成功
	 */
	public boolean allocatePlane(R plane) {
		if (super.allocate()) {
			this.plane = plane;
			return true;
		}
		return false;
	}

	/**
	 * 获得本航班的资源
	 * 
	 * @return 航班计划项的资源
	 */
	public R getResource() {
		return plane;
	}

	/**
	 * 获得经过的位置
	 * @return 经过的位置
	 */
	public TwoLocationEntry getTwoLocationEntry() {
		return twoLocations;
	}
	
	/**
	 * 获得中转机场的降落和起飞时间
	 * @return 中转机场的降落和起飞时间
	 */
	public Timeslot getTempTimeslot() {
		return tempTimeslot.getTimeslot();
	}
	
	@Override
	public Location getDeparture() {
		return twoLocations.getDeparture();
	}

	@Override
	public Location getArrival() {
		return twoLocations.getArrival();
	}

	@Override
	public Timeslot getTimeslot() {
		return timeslot.getTimeslot();
	}

	@Override
	public int compareTo(PlanningEntry<R> entry) {
		FlightEntry<R> pe = (FlightEntry<R>) entry;
		if (this.getTimeslot().getStartTime().isBefore(pe.getTimeslot().getStartTime()))
			return -1; // this在entry之前开始
		else if (this.getTimeslot().getStartTime().isAfter(pe.getTimeslot().getStartTime()))
			return 1; // entry在this之前开始
		else
			return 0;
	}
}
