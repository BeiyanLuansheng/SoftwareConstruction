package flight;

import common.location.Location;
import common.location.TwoLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.time.PresetSingleTimeslotEntry;
import common.time.Timeslot;

/**
 * һ��mutable���࣬��ʾһ������
 *
 * @param <R>��Դ����
 */
public class FlightEntry<R> extends CommonPlanningEntry<R> implements TwoLocationEntry, PresetSingleTimeslotEntry {

	private final TwoLocationEntry twoLocations;
	private final PresetSingleTimeslotEntry timeslot;
	private R plane;

	// Abstraction function:
	// AF(twoLocations, timeslot, plane)=һ��timeslot.getStartTime()��
	// twoLocations.getDeparture()����timeslot.getEndTime()����twoLocations.getArrival()�ĺ���
	// Representation invariant:
	// twoLocations!=null
	// timeslot!=null
	// Safety from rep exposure:
	// twoLocations,timeslot��������˽�е���final�޶�
	// plane��˽�е�

	/**
	 * ����һ������ƻ���
	 * 
	 * @param name         ��������ƣ��ǿ�
	 * @param twoLocations �������ֹ�ص㣬�ǿ�
	 * @param timeslot     �������ֹʱ�䣬�ǿ�
	 */
	public FlightEntry(String name, TwoLocationEntry twoLocations, PresetSingleTimeslotEntry timeslot) {
		super(name);
		// ������ǰ���������׳��쳣
		if (twoLocations == null || timeslot == null)
			throw new IllegalArgumentException("���ڿղ���");
		this.twoLocations = twoLocations;
		this.timeslot = timeslot;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.twoLocations != null;
		assert this.timeslot != null;
		assert this.timeslot.getTimeslot().getStartTime().isBefore(this.timeslot.getTimeslot().getEndTime());
	}

	/**
	 * Ϊ���������Դ
	 * 
	 * @param plane ���������Դ���ǿ�
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocatePlane(R plane) {
		if (plane == null) // ����Ϊ��ʱ�׳��쳣
			throw new IllegalArgumentException("��ԴΪ��");
		if (super.allocate()) {
			this.plane = plane;
			checkRep();
			assert this.plane != null;
			assert getState().equals("ALLOCATED"); // ȷ�����ѷ���״̬
			return true;
		}
		checkRep();
		return false;
	}

	/**
	 * ��ñ��������Դ
	 * 
	 * @return ����ƻ������Դ
	 */
	public R getResource() {
		checkRep();
		return plane;
	}

	@Override
	public Location getDeparture() {
		checkRep();
		return twoLocations.getDeparture();
	}

	@Override
	public Location getArrival() {
		checkRep();
		return twoLocations.getArrival();
	}

	@Override
	public Timeslot getTimeslot() {
		checkRep();
		return timeslot.getTimeslot();
	}

	@Override
	public int compareTo(PlanningEntry<R> entry) {
		FlightEntry<R> pe = (FlightEntry<R>) entry;
		if (this.getTimeslot().getStartTime().isBefore(pe.getTimeslot().getStartTime()))
			return -1; // this��entry֮ǰ��ʼ
		else if (this.getTimeslot().getStartTime().isAfter(pe.getTimeslot().getStartTime()))
			return 1; // entry��this֮ǰ��ʼ
		else
			return 0;
	}
}
