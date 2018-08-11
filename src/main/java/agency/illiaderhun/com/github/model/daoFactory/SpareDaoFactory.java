package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.SpareJdbcDao;
import agency.illiaderhun.com.github.model.daoInterface.SpareDao;

public class SpareDaoFactory {

    public static SpareDao getSpare(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new SpareJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("spare"));
        } else {
            return null;
        }
    }
}
