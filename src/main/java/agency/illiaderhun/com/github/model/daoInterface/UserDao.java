package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingStringException;

public interface UserDao<User, Interger> extends Dao<User, Integer> {

    User readByEmail(String eMail) throws InvalidSearchingStringException;
}
