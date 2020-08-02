package common.conflict;

import java.util.ArrayList;
import java.util.List;

import common.plan.PlanningEntry;
import common.time.Timeslot;
import flight.FlightEntry;

/**
 * 检查航班之间是否有资源独占冲突、寻找前序计划项
 * 
 * @param <R> 计划项资源类型
 */
public class CheckFlightEntry<R> {

	/**
	 * 检查航班之间是否存在资源独占冲突
	 * 
	 * @param entries 航班列表
	 * @return true 存在冲突 false 不存在冲突
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
	 * 在所有计划项中找到e计划项前一个同样使用资源r的计划项
	 * 
	 * @param r       使用的资源，非空
	 * @param e       指定的使用资源r的计划项，非空
	 * @param entries 计划项列表，至少有一个计划项
	 * @return 计划项 使用资源 r，执行时间在e之前，且在 e和 其之间不存在使用资源 r的其他计划项 null，不存在这样的计划项
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
