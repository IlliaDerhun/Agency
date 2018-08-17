package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.UserJdbcDao;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;

/**
 * Generate new User...Dao implementation for your own DB
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class UserDaoFactory {

    /**
     * Create new User...Dao implementation
     *
     * @param dbType database type for creating new User...Dao implementation
     * @return User...Dao implementation
     */
    public static UserDao getUser(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new UserJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("user"));
        }else {
            // create the new User...Dao implementation for your own DB
            return null;
        }
    }
}
