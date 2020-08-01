package exceptions;

/**
 * 机场的名字含有不合法字符的异常
 */
public class AirportFormatException extends Exception {
	
	public AirportFormatException() {
		super();
	}
	
	public AirportFormatException(String message) {
		super(message);
	}
}
