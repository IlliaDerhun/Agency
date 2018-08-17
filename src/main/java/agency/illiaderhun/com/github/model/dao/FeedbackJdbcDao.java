package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.FeedbackDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * FeedbackJdbcDao works with Feedback's entities
 * can do all CRUD operations and readByReportId
 *
 * @author Illia Derhun
 * @version 1.0
 */

public class FeedbackJdbcDao implements FeedbackDao<Feedback, Integer> {

    private static final Logger LOGGER = Logger.getLogger(FeedbackJdbcDao.class.getSimpleName());

    @NotNull
    private DataSource dataSource;

    @NotNull
    private Properties properties;

    public FeedbackJdbcDao(DataSource dataSource, Properties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    /**
     * Select Feedback by reportId
     *
     * @param reportId for select all customer's feedback about this report.
     * @return return all valid feedback if it exist.
     * @exception IdInvalid in case method couldn't find anything by reportId
     */
    @Override
    public Feedback readByReportId(Integer reportId) throws IdInvalid {
        LOGGER.info("method readByReportId start with reportId: " + reportId);
        Feedback theFeedback = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByReportId"))){

            statement.setInt(1, reportId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theFeedback = madeFeedback(resultSet);
            } else {
                LOGGER.error("method readByReportId throw IdInvalidException with message: \"Invalid reportId: \"" + reportId);
                throw new IdInvalid("Invalid reportId: " + reportId);
            }
        } catch (SQLException e) {
            LOGGER.error("readByReportId caught SQLException");
            e.printStackTrace();
        }

        LOGGER.info("method readByReportId return: " + theFeedback);
        return theFeedback;
    }

    /**
     * Create new {@link Feedback} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid {@link Feedback}
     * @throws SQLException in case some problem with resultSet
     */
    private Feedback madeFeedback(ResultSet resultSet) throws SQLException {
        LOGGER.info("madeFeedback start");
        Integer commentId = resultSet.getInt("comment_id");
        String comment = resultSet.getString("comment");
        Integer reportId = resultSet.getInt("report_id");
        Date date = resultSet.getDate("date");

        Feedback theFeedback = new Feedback(comment, reportId);
        theFeedback.setCommentId(commentId);
        theFeedback.setDate(date);

        LOGGER.info("madeFeedback return " + theFeedback);
        return theFeedback;
    }

    /**
     * Create Feedback order in database.
     *
     * @param feedback for creating.
     * @return false if Feedback already exist. True if creation is success.
     */
    @Override
    public boolean create(Feedback feedback) {
        LOGGER.info("method create start with: " + feedback);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("insert"))){

            setStatement(statement, feedback);
            statement.executeUpdate();

            feedback.setCommentId(setInsertedId());
            feedback.setDate(read(feedback.getCommentId()).getDate());


            result = true;
        } catch (SQLException e) {
            LOGGER.error("method create caught SLQException");
            e.printStackTrace();
        } catch (IdInvalid idInvalid) {
            LOGGER.error("method create caught IdInvalid Exception");
            idInvalid.printStackTrace();
        }

        LOGGER.info("method create return result " + result);
        return result;
    }

    private int setInsertedId() throws SQLException {
        LOGGER.info("method setInsertedId start with no parameters");
        int commentId = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("readInsertedId"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                commentId = resultSet.getInt("comment_id");
            }
        }

        LOGGER.info("method setInsertedId return orderId: " + commentId);
        return commentId;
    }

    private void setStatement(PreparedStatement statement, Feedback feedback) throws SQLException {
        LOGGER.info("setStatement start");
        statement.setString(1, feedback.getComment());
        statement.setInt(2, feedback.getReportId());
    }

    /**
     * Select Feedback order by commentId
     *
     * @param feedbackId for select all comments.
     * @return return valid feedback if it exist.
     * @exception IdInvalid in case method couldn't find anything by commentId
     */
    @Override
    public Feedback read(Integer feedbackId) throws IdInvalid {
        LOGGER.info("method read start with entityId " + feedbackId);
        Feedback theFeedback = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, feedbackId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theFeedback = madeFeedback(resultSet);
            } else {
                LOGGER.error("method read throw IdInvalid Exception with message: \"Invalid feedback\" " + feedbackId);
                throw new IdInvalid("Invalid feedbackId " + feedbackId);
            }
        } catch (SQLException e) {
            LOGGER.error("method read caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method read return feedback " + theFeedback);
        return theFeedback;
    }

    /**
     * Update all feedback's fields
     * except date and commentId
     *
     * @param feedback for updating
     * @return true if feedback has updated
     */
    @Override
    public boolean update(Feedback feedback) {
        LOGGER.info("method update start with feedback: " + feedback);
        boolean result = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("update"))){
            statement.setString(1, feedback.getComment());
            statement.setInt(2, feedback.getCommentId());

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method update caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method update return result: " + result);
        return result;
    }

    @Override
    public boolean delete(Integer feedbackId) {
        LOGGER.info("method delete start with feedbackId: " + feedbackId);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("delete"))){
            statement.setInt(1, feedbackId);

            result = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            LOGGER.error("method delete caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method delete return result: " + result);
        return result;
    }
}
