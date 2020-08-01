package common.conflict;

import java.util.ArrayList;
import java.util.List;

import common.plan.PlanningEntry;
import common.time.Timeslot;
import train.TrainEntry;

/**
 * 检查高铁车次之间是否有资源独占冲突、寻找前序计划项
 * 
 * @param <R> 计划项资源类型
 */
public class CheckTrainEntry<R> {

	/**
	 * 检查高铁车次之间是否存在资源独占冲突
	 * 
	 * @param entries 车次列表
	 * @return true 存在冲突 false 不存在冲突
	 */
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return false;
		List<TrainEntry<R>> trainEntries = new ArrayList<>();
		for (PlanningEntry<R> pe : entries)
			trainEntries.add((TrainEntry<R>) pe);

		List<R> carriages = new ArrayList<>();
		List<Timeslot> times = new ArrayList<>();
		for (TrainEntry<R> te : trainEntries) {
			List<R> train = te.getResources();
			Timeslot time = new Timeslot(te.getTimeslot().get(0).getStartTime(),
					te.getTimeslot().get(te.getTimeslot().size() - 1).getEndTime());
			for (R carriage : train) {
				if (carriages.contains(carriage))
					for (R c : carriages)
						if (c.equals(carriage)) {
							int index = carriages.indexOf(c);
							Timeslot t = times.get(index);
							if (!(t.isBefore(time) || time.isBefore(t)))
								return true;
						}
				carriages.add(carriage);
				times.add(time);
			}
		}
		return false;
	}

	/**
	 * 在所有计划项中找到e计划项前一个使用资源r的计划项
	 * 
	 * @param r       使用的资源，非空
	 * @param e       指定的使用资源r的计划项，非空
	 * @param entries 计划项列表，至少有一个计划项
	 * @return 计划项 使用资源 r，执行时间在e之前，且在 e和 其之间不存在使用资源 r的其他计划项 null，不存在这样的计划项
	 */
	public PlanningEntry<R> findPreEntryPerResource(R r, PlanningEntry<R> e, List<PlanningEntry<R>> entries) {
		if (r == null || e == null || !((TrainEntry<R>) e).getResources().contains(r) || entries == null
				|| entries.size() < 1)
			throw new IllegalArgumentException(); // 不满足前置条件，抛出异常
		if (entries == null || entries.size() < 2)
			return null;
		List<TrainEntry<R>> trainEntries = new ArrayList<>();
		for (PlanningEntry<R> pe : entries)
			trainEntries.add((TrainEntry<R>) pe);

		TrainEntry<R> f = null;
		for (TrainEntry<R> te : trainEntries) {
			if (te.getResources().contains(r) && te.getTimeslot().get(te.getTimeslot().size() - 1)
					.isBefore(((TrainEntry<R>) e).getTimeslot().get(0))) {
				if (f == null || te.getTimeslot().get(0).isAfter(f.getTimeslot().get(f.getTimeslot().size() - 1)))
					f = te;
			}
		}
		if (f != null) {
			assert f.getTimeslot().get(f.getTimeslot().size() - 1).isBefore(((TrainEntry<R>) e).getTimeslot().get(0)); // f要在e之前
			assert f.getResources().contains(r); // f使用的资源是r
		}
		return f;
	}
}
