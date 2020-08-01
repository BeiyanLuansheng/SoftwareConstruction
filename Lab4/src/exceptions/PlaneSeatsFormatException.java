package exceptions;

/***
 * 飞机座位格式异常
 */
public class PlaneSeatsFormatException extends Exception {

	public PlaneSeatsFormatException() {
		super();
	}
	
	public PlaneSeatsFormatException(String message) {
		super(message);
	}
}
