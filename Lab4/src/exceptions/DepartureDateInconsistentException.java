package exceptions;

/**
 * 起飞日期和航班日期不是同一天异常
 */
public class DepartureDateInconsistentException extends Exception {

	public DepartureDateInconsistentException() {
		super();
	}
	
	public DepartureDateInconsistentException(String message) {
		super(message);
	}
}
