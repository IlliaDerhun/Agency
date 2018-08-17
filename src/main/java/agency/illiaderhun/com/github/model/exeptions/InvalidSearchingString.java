package agency.illiaderhun.com.github.model.exeptions;

/**
 * Throw this exception in case some string for search doesn't exist
 * or not invalid (validation up2u)
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class InvalidSearchingString extends Exception {

    private static final long serialVersionUID = 4578421658974512365L;

    public InvalidSearchingString(){
        super();
    }

    public InvalidSearchingString(String message){
        super(message);
    }

    public InvalidSearchingString(String message, Throwable throwable){
        super(message, throwable);
    }
}
