package common.conflict;

import java.util.List;

import activity.ActivityEntry;
import common.plan.PlanningEntry;
import flight.FlightEntry;
import train.TrainEntry;

/**
 * 检查是否有资源独占冲突和位置独占冲突、寻找前序计划项
 * 
 * @param <R> 计划项资源类型
 */
public class PlanningEntryAPIs<R> {
	private CheckFlightEntry<R> cfe = new CheckFlightEntry<>();
	private CheckTrainEntry<R> cte = new CheckTrainEntry<>();

	/**
	 * 检查计划项之间是否存在位置独占冲突
	 * 
	 * @param entries 计划项列表
	 * @return false 不存在冲突 true 存在冲突
	 */
	public boolean checkLocationConflict(CheckActivityEntry<R> cae, List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return false;
		if (entries.get(0) instanceof ActivityEntry)
			return cae.checkLocationConflict(entries);
		else
			return false;
	}

	/**
	 * 检查计划项之间是否存在资源独占冲突
	 * 
	 * @param entries 计划项列表
	 * @return false 不存在冲突 true 存在冲突
	 */
	public boolean checkResourceExclusiveConflict(List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return false;
		if (entries.get(0) instanceof FlightEntry)
			return cfe.checkResourceExclusiveConflict(entries);
		else if (entries.get(0) instanceof TrainEntry)
			return cte.checkResourceExclusiveConflict(entries);
		else
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
		if (entries.get(0) instanceof FlightEntry)
			return cfe.findPreEntryPerResource(r, e, entries);
		else if (entries.get(0) instanceof TrainEntry)
			return cte.findPreEntryPerResource(r, e, entries);
		return null;
	}
}
