package activity;

import common.block.BlockableEntry;
import common.location.Location;
import common.location.ModifiableSingleLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.time.PresetSingleTimeslotEntry;
import common.time.Timeslot;

/**
 * 一个活动计划项
 * 
 * @param <R> 资源类型
 */
public class ActivityEntry<R> extends CommonPlanningEntry<R>
		implements ModifiableSingleLocationEntry, PresetSingleTimeslotEntry, BlockableEntry {

	private final ModifiableSingleLocationEntry modifiableLocation;
	private final PresetSingleTimeslotEntry timeslot;
	private R material;

	// Abstraction function:
	// AF(locations, timeslot, material)=一个以timeslot为起止时间，
	// 在modifiableLocation中的位置，使用material资源的活动计划项
	// Representation invariant:
	// modifiableLocation!=null
	// timeslot!=null
	// Safety from rep exposure:
	// locations,timeslot数据域都是私有的用final限定
	// material是私有的

	/**
	 * 创建一个学习活动计划项
	 * 
	 * @param name               计划项的名字，非空
	 * @param modifiableLocation 计划项地点，非空
	 * @param timeslot           计划项的开始结束时间，非空
	 */
	public ActivityEntry(String name, ModifiableSingleLocationEntry modifiableLocation,
			PresetSingleTimeslotEntry timeslot) {
		super(name);
		this.modifiableLocation = modifiableLocation;
		this.timeslot = timeslot;
	}

	/**
	 * 分配资源
	 * 
	 * @param material 待分配的资源
	 * @return false 分配失败 true 分配成功
	 */
	public boolean allocateMaterial(R material) {
		if (super.allocate()) {
			this.material = material;
			return true;
		}
		return false;
	}

	/**
	 * 获得计划项的资源
	 * 
	 * @return 计划项资源
	 */
	public R getMaterial() {
		return material;
	}

	@Override
	public Timeslot getTimeslot() {
		return this.timeslot.getTimeslot();
	}

	@Override
	public boolean setLocation(Location location) {
		if (state.state().equals("WAITING") || state.state().equals("ALLOCATED"))
			return this.modifiableLocation.setLocation(location);
		else
			return false;
	}

	@Override
	public Location getLocation() {
		return this.modifiableLocation.getLocation();
	}

	@Override
	public int compareTo(PlanningEntry<R> entry) {
		ActivityEntry<R> ae = (ActivityEntry<R>) entry;
		if (this.getTimeslot().getStartTime().isBefore(ae.getTimeslot().getStartTime()))
			return -1; // this在entry之前开始
		else if (this.getTimeslot().getStartTime().isAfter(ae.getTimeslot().getStartTime()))
			return 1; // entry在this之前开始
		else
			return 0;
	}

	@Override
	public boolean block() {
		state = state.nextState("BLOCKED");
		if (state.state().equals("BLOCKED"))
			return true;
		return false;
	}

}
