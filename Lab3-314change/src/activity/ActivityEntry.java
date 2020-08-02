package activity;

import common.block.BlockableEntry;
import common.location.Location;
import common.location.ModifiableSingleLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.time.PresetSingleTimeslotEntry;
import common.time.Timeslot;

/**
 * һ����ƻ���
 * 
 * @param <R> ��Դ����
 */
public class ActivityEntry<R> extends CommonPlanningEntry<R>
		implements ModifiableSingleLocationEntry, PresetSingleTimeslotEntry, BlockableEntry {

	private final ModifiableSingleLocationEntry modifiableLocation;
	private final PresetSingleTimeslotEntry timeslot;
	private R material;

	// Abstraction function:
	// AF(locations, timeslot, material)=һ����timeslotΪ��ֹʱ�䣬
	// ��modifiableLocation�е�λ�ã�ʹ��material��Դ�Ļ�ƻ���
	// Representation invariant:
	// modifiableLocation!=null
	// timeslot!=null
	// Safety from rep exposure:
	// locations,timeslot��������˽�е���final�޶�
	// material��˽�е�

	/**
	 * ����һ��ѧϰ��ƻ���
	 * 
	 * @param name               �ƻ�������֣��ǿ�
	 * @param modifiableLocation �ƻ���ص㣬�ǿ�
	 * @param timeslot           �ƻ���Ŀ�ʼ����ʱ�䣬�ǿ�
	 */
	public ActivityEntry(String name, ModifiableSingleLocationEntry modifiableLocation,
			PresetSingleTimeslotEntry timeslot) {
		super(name);
		this.modifiableLocation = modifiableLocation;
		this.timeslot = timeslot;
	}

	/**
	 * ������Դ
	 * 
	 * @param material ���������Դ
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocateMaterial(R material) {
		if (super.allocate()) {
			this.material = material;
			return true;
		}
		return false;
	}

	/**
	 * ��üƻ������Դ
	 * 
	 * @return �ƻ�����Դ
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
			return -1; // this��entry֮ǰ��ʼ
		else if (this.getTimeslot().getStartTime().isAfter(ae.getTimeslot().getStartTime()))
			return 1; // entry��this֮ǰ��ʼ
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
