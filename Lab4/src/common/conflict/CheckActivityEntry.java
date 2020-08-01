package common.conflict;

import java.util.List;

import common.plan.PlanningEntry;

/**
 * ����ƻ����Ƿ����λ�ö�ռ��ͻ
 * 
 * @param <R> ��Դ����
 */
public interface CheckActivityEntry<R> {

	/**
	 * ������еĻ�Ƿ����λ�ö�ռ��ͻ
	 * 
	 * @param activityEntries ��б�
	 * @return true ���ڳ�ͻ false �����ڳ�ͻ
	 */
	public boolean checkLocationConflict(List<PlanningEntry<R>> entries);
}
