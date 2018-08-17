package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.SpareJdbcDao;
import agency.illiaderhun.com.github.model.daoInterface.SpareDao;

/**
 * Generate new Spare...Dao implementation for your own DB
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class SpareDaoFactory {

    /**
     * Create new Spare...Dao implementation
     *
     * @param dbType database type for creating new Spare...Dao implementation
     * @return Spare...Dao implementation
     */
    public static SpareDao getSpare(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new SpareJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("spare"));
        } else {
            // create the new Spare...Dao implementation for your own DB
            return null;
        }
    }
}
