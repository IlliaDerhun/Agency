package agency.illiaderhun.com.github.model.exeptions;

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
