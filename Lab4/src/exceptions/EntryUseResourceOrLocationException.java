package exceptions;

/**
 * ɾ���ڱ�ʹ�õ���Դ��λ���쳣
 */
public class EntryUseResourceOrLocationException extends Exception {
    
    public EntryUseResourceOrLocationException(){
        super();
    }

    public EntryUseResourceOrLocationException(String message){
        super(message);
    }
}