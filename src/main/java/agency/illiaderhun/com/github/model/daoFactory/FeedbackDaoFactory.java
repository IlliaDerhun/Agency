package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.FeedbackJdbcDao;

public class FeedbackDaoFactory {

    public static FeedbackJdbcDao getFeedback(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new FeedbackJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("feedback"));
        } else {
            return null;
        }
    }
}
