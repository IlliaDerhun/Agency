package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.RepairOrderJdbcDao;

public class RepairOrderDaoFactory {

    public static RepairOrderJdbcDao getReport(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new RepairOrderJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("repairOrder"));
        } else {
            return null;
        }
    }
}
