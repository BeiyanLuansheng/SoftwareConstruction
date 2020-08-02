package common.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * һ��immutable�����ͣ���ʾһ��ʱ���
 */
public class Timeslot {

	private final LocalDateTime start, end;

	// Abstraction function:
	// AF(start,end)=��startΪ��ʼʱ�䣬endΪ����ʱ���һ��ʱ��
	// Representation invariant:
	// start!=null
	// end!=null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// ʹ�÷���ʽ��������start��end����ʱ��

	/**
	 * ����һ��ʱ���
	 * 
	 * @param start ��ʼʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @param end   ����ʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 */
	public Timeslot(String start, String end) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.start = LocalDateTime.parse(start, df);
		this.end = LocalDateTime.parse(end, df);
	}

	/**
	 * ����һ��ʱ���
	 * 
	 * @param start ��ʼʱ�䣬�ǿ�
	 * @param end   ����ʱ�䣬�ǿ�
	 */
	public Timeslot(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * ��ÿ�ʼʱ��
	 * 
	 * @return ��ʼʱ��
	 */
	public LocalDateTime getStartTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		String s = start.toString();
		return LocalDateTime.parse(s.substring(0, 10) + s.substring(11, 16), df);
	}

	/**
	 * ��ý���ʱ��
	 * 
	 * @return ����ʱ��
	 */
	public LocalDateTime getEndTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		String s = end.toString();
		return LocalDateTime.parse(s.substring(0, 10) + s.substring(11, 16), df);
	}

	/**
	 * ��ñ�ʾ��ʼʱ����ַ�������ʽΪyyyy-MM-ddHH:mm
	 * 
	 * @return ��ʾ��ʼʱ����ַ���
	 */
	public String getStartTimeString() {
		String s = start.toString();
		return s.substring(0, 10) + " " + s.substring(11, 16);
	}

	/**
	 * ��ñ�ʾ����ʱ����ַ�������ʽΪyyyy-MM-ddHH:mm
	 * 
	 * @return ��ʾ����ʱ����ַ���
	 */
	public String getEndTimeString() {
		String s = end.toString();
		return s.substring(0, 10) + " " + s.substring(11, 16);
	}

	/**
	 * �ж�thisʱ����Ƿ���һ��ʱ���time֮ǰ������ʱ��β��ཻ��
	 * 
	 * @param time һ��ʱ��Σ��ǿ�
	 * @return false this����time֮ǰ true this��time֮ǰ
	 */
	public boolean isBefore(Timeslot time) {
		if (this.end.isBefore(time.getStartTime()) || this.end.isEqual(time.getStartTime()))
			return true;
		else
			return false;
	}

	/**
	 * �ж�thisʱ����Ƿ���һ��ʱ���time֮������ʱ��β��ཻ��
	 * 
	 * @param time һ��ʱ��Σ��ǿ�
	 * @return false this����time֮�� true this��time֮��
	 */
	public boolean isAfter(Timeslot time) {
		if (this.start.isAfter(time.getEndTime()) || this.start.isEqual(time.getEndTime()))
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object time) {
		if (time instanceof Timeslot) {
			Timeslot t = (Timeslot) time;
			if (t.start.equals(this.start) && t.end.equals(this.end))
				return true;
		}
		return false;
	}
}
