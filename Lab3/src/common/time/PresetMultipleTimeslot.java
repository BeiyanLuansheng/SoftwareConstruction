package common.time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * һ��immutable�����ͣ���ʾ�ƻ������й����е�ʱ�̱�
 */
public class PresetMultipleTimeslot implements PresetMultipleTimeslotEntry {

	private final List<Timeslot> timeslot = new ArrayList<>();

	// Abstraction function:
	// ��timeslot�е�˳���ź���ģ���timeslot.size()�������һ����Դ
	// Representation invariant:
	// timeslot!=null
	// timeslot.size()>0
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// �����Դʱ��Collections.unmodifiableList()ת��Ϊ���ɱ��List���

	/**
	 * ����һ��ʱ�̱�
	 * 
	 * @param times ��Ԥ���ʱ����ǿգ�times.size()>0
	 */
	public PresetMultipleTimeslot(List<String> times) {
		for (int i = 0; i < times.size(); i += 2) {
			this.timeslot.add(new Timeslot(times.get(i), times.get(i + 1)));
		}
	}

	@Override
	public List<Timeslot> getTimeslot() {
		return Collections.unmodifiableList(timeslot);
	}

}
