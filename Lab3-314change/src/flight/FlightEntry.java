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
	private final PresetSingleTimeslotEntry timeslot, tempTimeslot;
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
	 * @param tempTimeslot ��ת����ֹʱ��
	 */
	public FlightEntry(String name, TwoLocationEntry twoLocations, PresetSingleTimeslotEntry timeslot, PresetSingleTimeslotEntry tempTimeslot) {
		super(name);
		this.twoLocations = twoLocations;
		this.timeslot = timeslot;
		this.tempTimeslot = tempTimeslot;
	}

	/**
	 * Ϊ���������Դ
	 * 
	 * @param plane ���������Դ���ǿ�
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocatePlane(R plane) {
		if (super.allocate()) {
			this.plane = plane;
			return true;
		}
		return false;
	}

	/**
	 * ��ñ��������Դ
	 * 
	 * @return ����ƻ������Դ
	 */
	public R getResource() {
		return plane;
	}

	/**
	 * ��þ�����λ��
	 * @return ������λ��
	 */
	public TwoLocationEntry getTwoLocationEntry() {
		return twoLocations;
	}
	
	/**
	 * �����ת�����Ľ�������ʱ��
	 * @return ��ת�����Ľ�������ʱ��
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
			return -1; // this��entry֮ǰ��ʼ
		else if (this.getTimeslot().getStartTime().isAfter(pe.getTimeslot().getStartTime()))
			return 1; // entry��this֮ǰ��ʼ
		else
			return 0;
	}
}
