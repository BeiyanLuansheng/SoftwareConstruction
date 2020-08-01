package train;

import java.util.List;

import common.block.BlockableEntry;
import common.location.Location;
import common.location.MultipleLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.resource.MultipleSortedResource;
import common.resource.MultipleSortedResourceEntry;
import common.time.PresetMultipleTimeslotEntry;
import common.time.Timeslot;

/**
 * 代表一个表示高铁车次计划项
 * 
 * @param <R> 资源类型
 */
public class TrainEntry<R> extends CommonPlanningEntry<R>
		implements MultipleLocationEntry, PresetMultipleTimeslotEntry, MultipleSortedResourceEntry<R>, BlockableEntry {

	private final MultipleLocationEntry locations;
	private final PresetMultipleTimeslotEntry timeslot;
	private MultipleSortedResourceEntry<R> train;

	// Abstraction function:
	// AF(locations, timeslot, train)=一个以timeslot为时刻表，
	// 经过locations中的车站，使用train中的车厢资源的高铁车次
	// Representation invariant:
	// locations!=null
	// timeslot!=null
	// Safety from rep exposure:
	// locations,timeslot数据域都是私有的用final限定
	// train是私有的

	/**
	 * 创建一个高铁车次计划项
	 * 
	 * @param name      车次的名称，非空
	 * @param locations 本车次经过的位置，非空
	 * @param timeslot  本车次的时刻表，非空
	 */
	public TrainEntry(String name, MultipleLocationEntry locations, PresetMultipleTimeslotEntry timeslot) {
		super(name);
		this.locations = locations;
		this.timeslot = timeslot;
	}

	/**
	 * 为本车次分配资源
	 * 
	 * @param train 待分配的资源，非空
	 * @return false 分配失败 true 分配成功
	 */
	public boolean allocateTrain(List<R> train) {
		if (super.allocate()) {
			this.train = new MultipleSortedResource<>(train);
			return true;
		}
		return false;
	}

	@Override
	public List<Location> getLocations() {
		return locations.getLocations();
	}

	@Override
	public List<R> getResources() {
		return train.getResources();
	}

	@Override
	public List<Timeslot> getTimeslot() {
		return this.timeslot.getTimeslot();
	}

	@Override
	public boolean block() {
		state = state.nextState("BLOCKED");
		if (state.state().equals("BLOCKED"))
			return true;
		return false;
	}

	@Override
	public int compareTo(PlanningEntry<R> entry) {
		TrainEntry<R> te = (TrainEntry<R>) entry;
		if (this.getTimeslot().get(0).getStartTime().isBefore(te.getTimeslot().get(0).getStartTime()))
			return -1; // this在entry之前开始
		else if (this.getTimeslot().get(0).getStartTime().isAfter(te.getTimeslot().get(0).getStartTime()))
			return 1; // entry在this之前开始
		else
			return 0;
	}
}
