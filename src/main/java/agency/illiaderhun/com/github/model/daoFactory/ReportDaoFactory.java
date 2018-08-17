package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.ReportJdbcDao;

/**
 * Generate new Report...Dao implementation for your own DB
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class ReportDaoFactory {

    /**
     * Create new Report...Dao implementation
     *
     * @param dbType database type for creating new Spare...Dao implementation
     * @return Spare...Dao implementation
     */
    public static ReportJdbcDao getReport(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new ReportJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("report"));
        } else {
            // create the new Report...Dao implementation for your own DB
            return null;
        }
    }
}
