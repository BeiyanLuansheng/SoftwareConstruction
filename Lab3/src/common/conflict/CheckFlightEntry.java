package common.conflict;

import java.util.ArrayList;
import java.util.List;

import common.plan.PlanningEntry;
import common.time.Timeslot;
import flight.FlightEntry;

/**
 * ��麽��֮���Ƿ�����Դ��ռ��ͻ��Ѱ��ǰ��ƻ���
 * 
 * @param <R> �ƻ�����Դ����
 */
public class CheckFlightEntry<R> {

	/**
	 * ��麽��֮���Ƿ������Դ��ռ��ͻ
	 * 
	 * @param entries �����б�
	 * @return true ���ڳ�ͻ false �����ڳ�ͻ
	 */
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return false;
		List<FlightEntry<R>> flightEntries = new ArrayList<>();
		for (PlanningEntry<R> pe : entries)
			flightEntries.add((FlightEntry<R>) pe);

		List<R> planes = new ArrayList<>();
		List<Timeslot> times = new ArrayList<>();
		for (FlightEntry<R> fe : flightEntries) {
			R plane = fe.getResource();
			Timeslot time = fe.getTimeslot();
			if (planes.contains(plane))
				for (R p : planes)
					if (p.equals(plane)) {
						int index = planes.indexOf(p);
						Timeslot t = times.get(index);
						if (!(t.isBefore(time) || time.isBefore(t)))
							return true;
					}
			planes.add(plane);
			times.add(time);
		}
		return false;
	}

	/**
	 * �����мƻ������ҵ�e�ƻ���ǰһ��ͬ��ʹ����Դr�ļƻ���
	 * 
	 * @param r       ʹ�õ���Դ���ǿ�
	 * @param e       ָ����ʹ����Դr�ļƻ���ǿ�
	 * @param entries �ƻ����б�������һ���ƻ���
	 * @return �ƻ��� ʹ����Դ r��ִ��ʱ����e֮ǰ������ e�� ��֮�䲻����ʹ����Դ r�������ƻ��� null�������������ļƻ���
	 */
	public PlanningEntry<R> findPreEntryPerResource(R r, PlanningEntry<R> e, List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return null;
		List<FlightEntry<R>> flightEntries = new ArrayList<>();
		for (PlanningEntry<R> pe : entries)
			flightEntries.add((FlightEntry<R>) pe);

		FlightEntry<R> f = null;
		for (FlightEntry<R> fe : flightEntries) {
			if (fe.getResource().equals(r) && fe.getTimeslot().isBefore(((FlightEntry<R>) e).getTimeslot())) {
				if (f == null || fe.getTimeslot().isAfter(f.getTimeslot()))
					f = fe;
			}
		}
		return f;
	}
}
