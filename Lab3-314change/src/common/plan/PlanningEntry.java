package common.plan;

import java.util.List;

import activity.ActivityEntry;
import common.location.Location;
import common.location.ModifiableSingleLocation;
import common.location.ModifiableSingleLocationEntry;
import common.location.MultipleLocation;
import common.location.MultipleLocationEntry;
import common.location.TwoLocationEntry;
import common.location.TwoLocationWithTemp;
import common.time.PresetMultipleTimeslot;
import common.time.PresetMultipleTimeslotEntry;
import common.time.PresetSingleTimeslot;
import common.time.PresetSingleTimeslotEntry;
import flight.FlightEntry;
import train.TrainEntry;

/**
 * 表示一个计划项
 * 
 * @param <R> 计划项使用的资源的类型
 */
public interface PlanningEntry<R> extends Comparable<PlanningEntry<R>> {

	/**
	 * 创建航班计划项
	 * 
	 * @param <R>       资源类型
	 * @param name      项目名，非空
	 * @param departure 出发地点，非空
	 * @param temp      中转机场，null表示没有中转
	 * @param arrival   目的地点，非空
	 * @param start     出发时间，非空，格式为yyyy-MM-dd HH:mm
	 * @param end       到达时间，非空，格式为yyyy-MM-dd HH:mm
	 * @param tempStart 中转的降落时间，格式为yyyy-MM-dd HH:mm
	 * @param tempEnd   中转的起飞时间，格式为yyyy-MM-dd HH:mm
	 * @return 一个新的航班计划项
	 */
	public static <R> PlanningEntry<R> flightEntry(String name, Location departure, Location temp, Location arrival,
			String start, String end, String tempStart, String tempEnd) {
		TwoLocationEntry twoLocations = new TwoLocationWithTemp(departure, temp, arrival);
		PresetSingleTimeslotEntry oneTimeslot = new PresetSingleTimeslot(start, end);
		PresetSingleTimeslotEntry tempTimeslot = new PresetSingleTimeslot(start, end);
		return new FlightEntry<>(name, twoLocations, oneTimeslot, tempTimeslot);
	}

	/**
	 * 创建高铁车次计划项
	 * 
	 * @param <R>          资源类型
	 * @param name         项目名，非空
	 * @param locationList 位置列表，非空
	 * @param times        时刻列表，非空，格式为yyyy-MM-dd HH:mm
	 * @return 一个新的高铁车次计划项
	 */
	public static <R> PlanningEntry<R> trainEntry(String name, List<Location> locationList, List<String> times) {
		MultipleLocationEntry locations = new MultipleLocation(locationList);
		PresetMultipleTimeslotEntry timeslot = new PresetMultipleTimeslot(times);
		return new TrainEntry<>(name, locations, timeslot);
	}

	/**
	 * 创建学习活动计划项
	 * 
	 * @param <R>   资源类型
	 * @param name  项目名，非空
	 * @param start 开始时间，非空，格式为yyyy-MM-dd HH:mm
	 * @param end   结束时间，非空，格式为yyyy-MM-dd HH:mm
	 * @return 一个新的学习活动计划项
	 */
	public static <R> PlanningEntry<R> activityEntry(String name, String start, String end) {
		ModifiableSingleLocationEntry modifiableLocation = new ModifiableSingleLocation();
		PresetSingleTimeslotEntry timeslot = new PresetSingleTimeslot(start, end);
		return new ActivityEntry<>(name, modifiableLocation, timeslot);
	}

	/**
	 * 将状态设置为已分配状态
	 * 
	 * @return false 设置失败 true 设置成功
	 */
	public boolean allocate();

	/**
	 * 将状态设置为运行状态
	 * 
	 * @return false 设置失败 true 设置成功
	 */
	public boolean running();

	/**
	 * 将状态设置为已取消状态
	 * 
	 * @return false 设置失败 true 设置成功
	 */
	public boolean cancel();

	/**
	 * 将状态设置为完成状态
	 * 
	 * @return false 设置失败 true 设置成功
	 */
	public boolean end();

	/**
	 * 获取计划项的名字
	 * 
	 * @return 表示名字的字符串
	 */
	public String getName();

	/**
	 * 获得计划项状态
	 * 
	 * @return 表示状态的字符串
	 */
	public String getState();
}
