package exceptions;

/**
 * ��ǰ״̬����ȡ���쳣
 */
public class CannotCancelledException extends Exception {
    
    public CannotCancelledException(){
        super();
    }

    public CannotCancelledException(String message){
        super(message);
    }
}