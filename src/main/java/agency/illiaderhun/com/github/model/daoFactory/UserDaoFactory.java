package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.UserJdbcDao;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;

public class UserDaoFactory {

    public static UserDao getUser(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new UserJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("user"));
        }else {
            return null;
        }
    }
}
