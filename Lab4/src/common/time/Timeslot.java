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
	// start����end
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
		// ��ʽ������ǰ���������׳��쳣
		if (start == null || start.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("��ʼʱ��������Ϸ�");
		if (end == null || end.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("����ʱ��������Ϸ�");
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime begin = LocalDateTime.parse(start, df);
		LocalDateTime finish = LocalDateTime.parse(end, df);
		if (!begin.isBefore(finish)) // ������ʱ��˳��Ҫ���׳��쳣
			throw new IllegalArgumentException("����ʱ�䲻�ڿ�ʼʱ��֮��");
		this.start = begin;
		this.end = finish;
		checkRep();
	}

	/**
	 * ����һ��ʱ���
	 * 
	 * @param start ��ʼʱ�䣬�ǿ�
	 * @param end   ����ʱ�䣬�ǿ�
	 */
	public Timeslot(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null || end.isBefore(start))
			throw new IllegalArgumentException("����Ϊ�ջ����ʱ�䲻�ڿ�ʼʱ��֮��");
		this.start = start;
		this.end = end;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert start != null;
		assert end != null;
		assert start.isBefore(end);
	}

	/**
	 * ��ÿ�ʼʱ��
	 * 
	 * @return ��ʼʱ��
	 */
	public LocalDateTime getStartTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		String s = start.toString();
		checkRep();
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
		checkRep();
		return LocalDateTime.parse(s.substring(0, 10) + s.substring(11, 16), df);
	}

	/**
	 * ��ñ�ʾ��ʼʱ����ַ�������ʽΪyyyy-MM-ddHH:mm
	 * 
	 * @return ��ʾ��ʼʱ����ַ���
	 */
	public String getStartTimeString() {
		String s = start.toString();
		checkRep();
		return s.substring(0, 10) + " " + s.substring(11, 16);
	}

	/**
	 * ��ñ�ʾ����ʱ����ַ�������ʽΪyyyy-MM-ddHH:mm
	 * 
	 * @return ��ʾ����ʱ����ַ���
	 */
	public String getEndTimeString() {
		String s = end.toString();
		checkRep();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
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
