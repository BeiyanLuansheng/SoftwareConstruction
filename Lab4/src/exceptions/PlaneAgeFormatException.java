package exceptions;

/**
 * 机龄格式异常
 */
public class PlaneAgeFormatException extends Exception {
	
	public PlaneAgeFormatException() {
		super();
	}
	
	public PlaneAgeFormatException(String message) {
		super(message);
	}
}
