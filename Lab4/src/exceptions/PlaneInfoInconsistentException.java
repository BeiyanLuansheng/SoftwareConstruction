package exceptions;

/**
 * 相同编号的飞机信息不一致异常
 */
public class PlaneInfoInconsistentException extends Exception {
	
	public PlaneInfoInconsistentException() {
		super();
	}
	
	public PlaneInfoInconsistentException(String message) {
		super(message);
	}
}
