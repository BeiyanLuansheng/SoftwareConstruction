package exceptions;

/**
 * 降落日期和航班日期的差距大于一天异常
 */
public class ArrivalDateInconsistentException extends Exception {

	public ArrivalDateInconsistentException() {
		super();
	}
	
	public ArrivalDateInconsistentException(String message) {
		super(message);
	}
}
