package agency.illiaderhun.com.github.model.exeptions;

/**
 * Throw this exception in case some id doesn't exist
 * or not invalid (validation up2u)
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class IdInvalid extends Exception {
    private static final long serialVersionUID = 7845544125415684484L;

    public IdInvalid(){
        super();
    }

    public IdInvalid(String message){
        super(message);
    }

    public IdInvalid(String message, Throwable throwable){
        super(message, throwable);
    }
}
