package common.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 一个immutable的类型，表示一个时间段
 */
public class Timeslot {

	private final LocalDateTime start, end;

	// Abstraction function:
	// AF(start,end)=以start为开始时间，end为结束时间的一段时间
	// Representation invariant:
	// start!=null
	// end!=null
	// start早于end
	// Safety from rep exposure:
	// 所有的数据域都是私有的且使用final限定
	// 使用防御式拷贝返回start和end两个时间

	/**
	 * 创建一个时间段
	 * 
	 * @param start 开始时间，非空，格式为yyyy-MM-dd HH:mm
	 * @param end   结束时间，非空，格式为yyyy-MM-dd HH:mm
	 */
	public Timeslot(String start, String end) {
		// 格式不符合前置条件，抛出异常
		if (start == null || start.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("开始时间参数不合法");
		if (end == null || end.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("结束时间参数不合法");
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime begin = LocalDateTime.parse(start, df);
		LocalDateTime finish = LocalDateTime.parse(end, df);
		if (!begin.isBefore(finish)) // 不符合时间顺序要求，抛出异常
			throw new IllegalArgumentException("结束时间不在开始时间之后");
		this.start = begin;
		this.end = finish;
		checkRep();
	}

	/**
	 * 创建一个时间段
	 * 
	 * @param start 开始时间，非空
	 * @param end   结束时间，非空
	 */
	public Timeslot(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null || end.isBefore(start))
			throw new IllegalArgumentException("参数为空或结束时间不在开始时间之后");
		this.start = start;
		this.end = end;
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert start != null;
		assert end != null;
		assert start.isBefore(end);
	}

	/**
	 * 获得开始时间
	 * 
	 * @return 开始时间
	 */
	public LocalDateTime getStartTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		String s = start.toString();
		checkRep();
		return LocalDateTime.parse(s.substring(0, 10) + s.substring(11, 16), df);
	}

	/**
	 * 获得结束时间
	 * 
	 * @return 结束时间
	 */
	public LocalDateTime getEndTime() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm");
		String s = end.toString();
		checkRep();
		return LocalDateTime.parse(s.substring(0, 10) + s.substring(11, 16), df);
	}

	/**
	 * 获得表示开始时间的字符串，格式为yyyy-MM-ddHH:mm
	 * 
	 * @return 表示开始时间的字符串
	 */
	public String getStartTimeString() {
		String s = start.toString();
		checkRep();
		return s.substring(0, 10) + " " + s.substring(11, 16);
	}

	/**
	 * 获得表示结束时间的字符串，格式为yyyy-MM-ddHH:mm
	 * 
	 * @return 表示结束时间的字符串
	 */
	public String getEndTimeString() {
		String s = end.toString();
		checkRep();
		return s.substring(0, 10) + " " + s.substring(11, 16);
	}

	/**
	 * 判断this时间段是否在一个时间段time之前（两个时间段不相交）
	 * 
	 * @param time 一个时间段，非空
	 * @return false this不在time之前 true this在time之前
	 */
	public boolean isBefore(Timeslot time) {
		if (this.end.isBefore(time.getStartTime()) || this.end.isEqual(time.getStartTime()))
			return true;
		else
			return false;
	}

	/**
	 * 判断this时间段是否在一个时间段time之后（两个时间段不相交）
	 * 
	 * @param time 一个时间段，非空
	 * @return false this不在time之后 true this在time之后
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
