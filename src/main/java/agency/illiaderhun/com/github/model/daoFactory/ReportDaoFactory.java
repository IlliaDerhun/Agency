package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.ReportJdbcDao;

public class ReportDaoFactory {

    public static ReportJdbcDao getReport(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new ReportJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("report"));
        } else {
            return null;
        }
    }
}
