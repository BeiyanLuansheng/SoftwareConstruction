package exceptions;

/**
 * 删除在被使用的资源或位置异常
 */
public class EntryUseResourceOrLocationException extends Exception {
    
    public EntryUseResourceOrLocationException(){
        super();
    }

    public EntryUseResourceOrLocationException(String message){
        super(message);
    }
}