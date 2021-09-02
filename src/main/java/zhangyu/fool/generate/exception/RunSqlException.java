package zhangyu.fool.generate.exception;

/**
 * @author xiaomingzhang
 * @date 2021/9/2
 */
public class RunSqlException extends RuntimeException {

    public RunSqlException(String message) {
        super(message);
    }

    public RunSqlException(String message, Throwable cause) {
        super(message, cause);
    }

}
