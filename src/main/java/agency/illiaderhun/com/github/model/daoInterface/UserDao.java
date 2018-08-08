package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;

public interface UserDao<User, Interger> extends Dao<User, Integer> {

    User readByEmail(String eMail) throws InvalidSearchingString;
}
