package exceptions;

/**
 * 资源和位置冲突异常
 */
public class ResourceOrLocationConflictException extends Exception {

    public ResourceOrLocationConflictException(){
        super();
    }

    public ResourceOrLocationConflictException(String message){
        super(message);
    }
}