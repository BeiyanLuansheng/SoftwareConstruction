package common.conflict;

import java.util.List;

import common.plan.PlanningEntry;

/**
 * 检查活动计划项是否存在位置独占冲突
 * 
 * @param <R> 资源类型
 */
public interface CheckActivityEntry<R> {

	/**
	 * 检查所有的活动是否存在位置独占冲突
	 * 
	 * @param activityEntries 活动列表
	 * @return true 存在冲突 false 不存在冲突
	 */
	public boolean checkLocationConflict(List<PlanningEntry<R>> entries);
}
