package cn.onecolour.dataStructure.expection;

/**
 * @author yang
 * @date 2021/12/16
 * @description
 */
public class FullException extends RuntimeException{

    public FullException() {
        super();
    }

    public FullException(String message) {
        super(message);
    }

    public FullException(String message, Throwable cause) {
        super(message, cause);
    }

    public FullException(Throwable cause) {
        super(cause);
    }
}
