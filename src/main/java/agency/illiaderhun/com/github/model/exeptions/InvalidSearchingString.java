package agency.illiaderhun.com.github.model.exeptions;

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
