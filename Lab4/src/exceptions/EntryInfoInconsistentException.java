package exceptions;

/**
 * 具有不同日期相同航班号的航班的起降机场和时间有差异的异常
 */
public class EntryInfoInconsistentException extends Exception {

	public EntryInfoInconsistentException() {
		super();
	}
	
	public EntryInfoInconsistentException(String message) {
		super(message);
	}
}
