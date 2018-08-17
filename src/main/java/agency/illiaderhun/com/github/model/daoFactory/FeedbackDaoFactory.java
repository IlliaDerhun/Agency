package agency.illiaderhun.com.github.model.daoFactory;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.dao.FeedbackJdbcDao;

/**
 * Generate new Feedback...Dao implementation for your own DB
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class FeedbackDaoFactory {

    /**
     * Create new Feedback...Dao implementation
     *
     * @param dbType database type for creating new Spare...Dao implementation
     * @return Spare...Dao implementation
     */
    public static FeedbackJdbcDao getFeedback(String dbType){
        if (dbType.equalsIgnoreCase("mysql")){
            return new FeedbackJdbcDao(ConnectionManager.modelConnection(), QueriesManager.getProperties("feedback"));
        } else {
            // create the new Feedback...Dao implementation for your own DB
            return null;
        }
    }
}
