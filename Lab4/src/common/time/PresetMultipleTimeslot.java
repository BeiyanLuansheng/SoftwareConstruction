package common.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * һ��immutable�����ͣ���ʾ�ƻ������й����е�ʱ�̱�
 */
public class PresetMultipleTimeslot implements PresetMultipleTimeslotEntry {

	private final List<Timeslot> timeslot;

	// Abstraction function:
	// ��timeslot�е�˳���ź���ģ���timeslot.size()�������һ����Դ
	// Representation invariant:
	// timeslot!=null
	// timeslot.size()>0
	// ʱ�̱��е�ʱ��Ҫ��ʱ��˳������
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// �����Դʱ��Collections.unmodifiableList()ת��Ϊ���ɱ��List���

	/**
	 * ����һ��ʱ�̱�
	 * 
	 * @param times ��Ԥ���ʱ����ǿգ�times.size()>0��times.size() % 2 ==0��ʱ��Ҫ��ʱ��˳������
	 */
	public PresetMultipleTimeslot(List<String> times) {
		// times������ǰ���������׳��쳣
		if (times == null || times.size() == 0 || times.size() % 2 != 0)
			throw new IllegalArgumentException("�������Ϸ�");
		List<Timeslot> timeslot = new ArrayList<>();
		for (int i = 0; i < times.size(); i += 2)
			timeslot.add(new Timeslot(times.get(i), times.get(i + 1)));
		for (int i = 0; i < timeslot.size() - 1; i++)
			if (timeslot.get(i).getEndTime().isAfter(timeslot.get(i + 1).getStartTime()))
				throw new IllegalArgumentException("����˳�򲻺Ϸ�");
		this.timeslot = timeslot;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.timeslot != null;
		assert this.timeslot.size() > 0;
		for (int i = 0; i < timeslot.size() - 1; i++)
			if (timeslot.get(i).getEndTime().isAfter(timeslot.get(i + 1).getStartTime()))
				assert false;
	}

	@Override
	public List<Timeslot> getTimeslot() {
		checkRep();
		return Collections.unmodifiableList(timeslot);
	}

}
