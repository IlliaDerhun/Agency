package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.RepairOrderJdbcDao;

/**
 * Generate new Repair...Dao implementation for your own DB
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class RepairOrderDaoFactory {

    /**
     * Create new Repair...Dao implementation
     *
     * @param dbType database type for creating new Spare...Dao implementation
     * @return Spare...Dao implementation
     */
    public static RepairOrderJdbcDao getReport(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new RepairOrderJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("repairOrder"));
        } else {
            // create the new Repair...Dao implementation for your own DB
            return null;
        }
    }
}
