package exceptions;

/**
 * 飞机型号格式异常
 */
public class PlaneTypeFormatException extends Exception {

	public PlaneTypeFormatException() {
		super();
	}
	
	public PlaneTypeFormatException(String message) {
		super(message);
	}
}
