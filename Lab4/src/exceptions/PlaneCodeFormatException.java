package exceptions;

/**
 * 飞机编号格式异常
 */
public class PlaneCodeFormatException extends Exception {
	
	public PlaneCodeFormatException() {
		super();
	}
	
	public PlaneCodeFormatException(String message) {
		super(message);
	}
}
