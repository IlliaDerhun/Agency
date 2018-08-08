package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.entities.User;

public interface UserDao<User, Interger> extends Dao<User, Integer> {

    User readByEmail(String eMail);
}
