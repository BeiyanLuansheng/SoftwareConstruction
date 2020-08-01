package common.conflict;

import java.util.ArrayList;
import java.util.List;

import common.plan.PlanningEntry;
import common.time.Timeslot;
import train.TrainEntry;

/**
 * ����������֮���Ƿ�����Դ��ռ��ͻ��Ѱ��ǰ��ƻ���
 * 
 * @param <R> �ƻ�����Դ����
 */
public class CheckTrainEntry<R> {

	/**
	 * ����������֮���Ƿ������Դ��ռ��ͻ
	 * 
	 * @param entries �����б�
	 * @return true ���ڳ�ͻ false �����ڳ�ͻ
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
	 * �����мƻ������ҵ�e�ƻ���ǰһ��ʹ����Դr�ļƻ���
	 * 
	 * @param r       ʹ�õ���Դ���ǿ�
	 * @param e       ָ����ʹ����Դr�ļƻ���ǿ�
	 * @param entries �ƻ����б�������һ���ƻ���
	 * @return �ƻ��� ʹ����Դ r��ִ��ʱ����e֮ǰ������ e�� ��֮�䲻����ʹ����Դ r�������ƻ��� null�������������ļƻ���
	 */
	public PlanningEntry<R> findPreEntryPerResource(R r, PlanningEntry<R> e, List<PlanningEntry<R>> entries) {
		if (r == null || e == null || !((TrainEntry<R>) e).getResources().contains(r) || entries == null
				|| entries.size() < 1)
			throw new IllegalArgumentException(); // ������ǰ���������׳��쳣
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
			assert f.getTimeslot().get(f.getTimeslot().size() - 1).isBefore(((TrainEntry<R>) e).getTimeslot().get(0)); // fҪ��e֮ǰ
			assert f.getResources().contains(r); // fʹ�õ���Դ��r
		}
		return f;
	}
}
