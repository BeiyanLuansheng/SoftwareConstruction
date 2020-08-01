package exceptions;

/**
 * 相同航班号相同日期造成的异常
 */
public class SameInfoEntryException extends Exception {

	public SameInfoEntryException() {
		super();
	}
	
	public SameInfoEntryException(String message) {
		super(message);
	}
	
}
