package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.ReportDao;
import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * ReportJdbcDao works with Report's entities
 * can do all CRUD operations and readByOrderId
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class ReportJdbcDao implements ReportDao<Report, Integer> {

    private static final Logger LOGGER = Logger.getLogger(ReportJdbcDao.class.getSimpleName());

    @NotNull
    private DataSource dataSource;

    @NotNull
    private Properties properties;

    public ReportJdbcDao(DataSource dataSource, Properties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    /**
     * Select report by orderId
     *
     * @param orderId report's name for searching
     * @return valid entity if it exist
     * @exception IdInvalidExcepiton if name doesn't exist
     */
    @Override
    public Report readByOrderId(Integer orderId) throws IdInvalidExcepiton {
        LOGGER.info("method readByOrderId start with orderId: " + orderId);
        Report theReport = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByOrderId"))){
            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theReport = madeReport(resultSet);
            } else {
                LOGGER.error("method readByOrderId throw IdInvalidExcepiton Exception message: \"Invalid order's id\": " + orderId);
                throw new IdInvalidExcepiton("Invalid order's id");
            }

        } catch (SQLException e) {
            LOGGER.error("method readByOrderId caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method readByOrderId return report: " + theReport);
        return theReport;
    }

    /**
     * Create new {@link Report} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid {@link Report}
     * @throws SQLException in case some problem with resultSet
     */
    private Report madeReport(ResultSet resultSet) throws SQLException {
        LOGGER.info("method madeReport start with resultSet: " + resultSet);

        Integer reportId = resultSet.getInt("report_id");
        String replacedPart = resultSet.getString("replaced_part");
        String breakingDescription = resultSet.getString("breaking_description");
        String theWorkDone = resultSet.getString("the_work_done");
        Integer orderId = resultSet.getInt("order_id");
        Date date = resultSet.getDate("date");

        Report theReport = new Report.Builder(breakingDescription, orderId)
                .reportId(reportId)
                .replacedPart(replacedPart)
                .theWorkDone(theWorkDone)
                .date(date)
                .build();

        LOGGER.info("method madeReport return report: " + theReport);
        return theReport;
    }

    /**
     * Create report in database.
     *
     * @param report for creating.
     * @return false if Report already exist. True if creation is success.
     */
    @Override
    public boolean create(Report report) {
        LOGGER.info("method create start with report: " + report);
        boolean result = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("insert"))){

            setStatement(statement, report);
            statement.executeUpdate();

            report.setReportId(setInsertedId());
            report.setDate(read(report.getReportId()).getDate());

            result = true;

        } catch (SQLException e) {
            LOGGER.error("method create caught SLQException " + e);
            e.printStackTrace();
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            LOGGER.error("method create caught IdInvalidExcepiton");
            idInvalidExcepiton.printStackTrace();
        }

        LOGGER.info("method create return result: " + result);
        return result;
    }

    /**
     * After report has been created
     * this method select inserted and auto.generated detailId
     *
     * @return new report's inserted and auto.generated id
     * @throws SQLException in case some problem with statement
     */
    private int setInsertedId() throws SQLException {
        LOGGER.info("method setInsertedId start with no parameters");
        int reportId = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("readInsertedId"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                reportId = resultSet.getInt("report_id");
            }
        }

        LOGGER.info("method setInsertedId return reportId: " + reportId);
        return reportId;
    }

    private void setStatement(PreparedStatement statement, Report report) throws SQLException {
        statement.setString(1, report.getReplacedPart());
        statement.setString(2, report.getBreakingDescription());
        statement.setString(3, report.getTheWorkDone());
        statement.setInt(4, report.getOrderId());
    }

    /**
     * Select report by reportId.
     *
     * @param reportId for select.
     * @return return valid entity if it exist.
     * @exception IdInvalidExcepiton in case nothing exist by this reportId
     */
    @Override
    public Report read(Integer reportId) throws IdInvalidExcepiton {
        LOGGER.info("method read start with entityId: " + reportId);
        Report theReport = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, reportId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theReport = madeReport(resultSet);
            } else {
                LOGGER.error("method read throw IdInvalidExcepiton Exception message: \"Invalid entityId\": " + reportId);
                throw new IdInvalidExcepiton("Invalid entityId");
            }
        } catch (SQLException e) {
            LOGGER.error("method read caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method read return report: " + theReport);
        return theReport;
    }

    /**
     * Update report by reportId
     *
     * @param report with new info for updating
     * @return true if report has been updated else false
     */
    @Override
    public boolean update(Report report) {
        LOGGER.info("method update start with report: " + report);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("update"))){

            setStatement(statement, report);

            statement.setInt(5, report.getReportId());

            result = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            LOGGER.error("method update caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method update return result: " + result);
        return result;
    }

    @Override
    public boolean delete(Integer entityId) {
        LOGGER.info("method delete start with reportId: " + entityId);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("delete"))){
            statement.setInt(1, entityId);

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method delete caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.error("method delete return result: " + result);
        return result;
    }
}
