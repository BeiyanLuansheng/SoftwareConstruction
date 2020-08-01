package exceptions;

/**
 * 当前状态不可取消异常
 */
public class CannotCancelledException extends Exception {
    
    public CannotCancelledException(){
        super();
    }

    public CannotCancelledException(String message){
        super(message);
    }
}