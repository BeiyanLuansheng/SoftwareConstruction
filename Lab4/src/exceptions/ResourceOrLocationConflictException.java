package exceptions;

/**
 * ��Դ��λ�ó�ͻ�쳣
 */
public class ResourceOrLocationConflictException extends Exception {

    public ResourceOrLocationConflictException(){
        super();
    }

    public ResourceOrLocationConflictException(String message){
        super(message);
    }
}