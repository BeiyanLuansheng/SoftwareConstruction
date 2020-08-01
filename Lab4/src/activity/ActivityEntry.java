package activity;

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
		implements ModifiableSingleLocationEntry, PresetSingleTimeslotEntry {

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
		// ������ǰ���������׳��쳣
		if (modifiableLocation == null || timeslot == null)
			throw new IllegalArgumentException("����Ϊ��");
		this.modifiableLocation = modifiableLocation;
		this.timeslot = timeslot;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.modifiableLocation != null;
		assert this.timeslot != null;
	}

	/**
	 * ������Դ
	 * 
	 * @param material ���������Դ���ǿ�
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocateMaterial(R material) {
		if (material == null) // ����Ϊ��ʱ�׳��쳣
			throw new IllegalArgumentException("����Ϊ��");
		if (super.allocate()) {
			this.material = material;
			checkRep();
			assert this.material != null; //�����ѷ���
			assert getState().equals("ALLOCATED"); // ȷ�����ѷ���״̬
			return true;
		}
		checkRep();
		return false;
	}

	/**
	 * ��üƻ������Դ
	 * 
	 * @return �ƻ�����Դ
	 */
	public R getMaterial() {
		checkRep();
		return material;
	}

	@Override
	public Timeslot getTimeslot() {
		checkRep();
		return this.timeslot.getTimeslot();
	}

	@Override
	public boolean setLocation(Location location) {
		if (location == null) // locationΪ�գ��׳��쳣
			throw new IllegalArgumentException("λ��Ϊ��");
		if (state.state().equals("WAITING") || state.state().equals("ALLOCATED")) {
			checkRep();
			return this.modifiableLocation.setLocation(location);
		} else
			return false;
	}

	@Override
	public Location getLocation() {
		checkRep();
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

}
