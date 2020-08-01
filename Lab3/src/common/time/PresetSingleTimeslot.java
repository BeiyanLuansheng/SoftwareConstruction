package common.time;

/**
 * һ��immutable�����ͣ���ʾ�ƻ������й����е���ֹʱ��
 */
public class PresetSingleTimeslot implements PresetSingleTimeslotEntry {

	private final Timeslot timeslot;

	// Abstraction function:
	// AF(timeslot)=�ƻ������й����е���ֹʱ��
	// Representation invariant:
	// timeslot!=null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�

	/**
	 * ������ֹʱ�̱�
	 * 
	 * @param start ��ʼʱ��
	 * @param end   ����ʱ��
	 */
	public PresetSingleTimeslot(String start, String end) {
		this.timeslot = new Timeslot(start, end);
	}

	@Override
	public Timeslot getTimeslot() {
		return timeslot;
	}
}