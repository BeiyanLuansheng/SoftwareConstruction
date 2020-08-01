package exceptions;

/**
 * 日期时间格式异常
 */
public class DateTimeFormatException extends Exception {
	
	public DateTimeFormatException() {
		super();
	}
	
	public DateTimeFormatException(String message) {
		super(message);
	}
}
