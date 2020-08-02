package common.conflict;

import java.util.List;

import activity.ActivityEntry;
import common.plan.PlanningEntry;
import flight.FlightEntry;
import train.TrainEntry;

/**
 * ����Ƿ�����Դ��ռ��ͻ��λ�ö�ռ��ͻ��Ѱ��ǰ��ƻ���
 * 
 * @param <R> �ƻ�����Դ����
 */
public class PlanningEntryAPIs<R> {
	private CheckFlightEntry<R> cfe = new CheckFlightEntry<>();
	private CheckTrainEntry<R> cte = new CheckTrainEntry<>();

	/**
	 * ���ƻ���֮���Ƿ����λ�ö�ռ��ͻ
	 * 
	 * @param entries �ƻ����б�
	 * @return false �����ڳ�ͻ true ���ڳ�ͻ
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
	 * ���ƻ���֮���Ƿ������Դ��ռ��ͻ
	 * 
	 * @param entries �ƻ����б�
	 * @return false �����ڳ�ͻ true ���ڳ�ͻ
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
	 * �����мƻ������ҵ�e�ƻ���ǰһ��ʹ����Դr�ļƻ���
	 * 
	 * @param r       ʹ�õ���Դ���ǿ�
	 * @param e       ָ����ʹ����Դr�ļƻ���ǿ�
	 * @param entries �ƻ����б�������һ���ƻ���
	 * @return �ƻ��� ʹ����Դ r��ִ��ʱ����e֮ǰ������ e�� ��֮�䲻����ʹ����Դ r�������ƻ��� null�������������ļƻ���
	 */
	public PlanningEntry<R> findPreEntryPerResource(R r, PlanningEntry<R> e, List<PlanningEntry<R>> entries) {
		if (entries.get(0) instanceof FlightEntry)
			return cfe.findPreEntryPerResource(r, e, entries);
		else if (entries.get(0) instanceof TrainEntry)
			return cte.findPreEntryPerResource(r, e, entries);
		return null;
	}
}
