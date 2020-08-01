package exceptions;

/**
 * 文件中一个完整的计划项的固有的格式的异常
 */
public class InherentFormatException extends Exception {
	
	public InherentFormatException() {
		super();
	}
	
	public InherentFormatException(String message) {
		super(message);
	}
	
}
