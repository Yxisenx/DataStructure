package cn.onecolour.dataStructure.expection;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
public class EmptyException extends RuntimeException{

    public EmptyException() {
        super();
    }

    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyException(Throwable cause) {
        super(cause);
    }
}
