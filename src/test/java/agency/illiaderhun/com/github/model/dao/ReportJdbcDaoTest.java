package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoInterface.ReportDao;
import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportJdbcDaoTest {

    @Mock
    private SJDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    private Report report;

    private ReportDao<Report, Integer> reportDao;

    private Properties properties = QueriesManager.getProperties("report");

    @Before
    public void setUp() throws Exception {
        reportDao = new ReportJdbcDao(dataSource, QueriesManager.getProperties("report"));
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        report = new Report.Builder("Some description", 87)
                .reportId(128)
                .replacedPart("2")
                .theWorkDone("Some work done")
                .date(new Date(118, 7, 7))
                .build();

//        when(resultSet.first()).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("report_id")).thenReturn(report.getReportId());
        when(resultSet.getString("replaced_part")).thenReturn(report.getReplacedPart());
        when(resultSet.getString("breaking_description")).thenReturn(report.getBreakingDescription());
        when(resultSet.getString("the_work_done")).thenReturn(report.getTheWorkDone());
        when(resultSet.getInt("order_id")).thenReturn(report.getOrderId());
        when(resultSet.getDate("date")).thenReturn(report.getDate());
    }

    @Test
    public void readByOrderIdReturnValidEntity() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(report.toString(), reportDao.readByOrderId(87).toString());
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidOrderIdThrowException() throws IdInvalidExcepiton {
        reportDao.readByOrderId(87);
    }

    @Test(expected = NullPointerException.class)
    public void createInValidEntityThrowException() {
        reportDao.create(null);
    }

    @Test
    public void readByReportIdReturnValidEntity() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(report.toString(), reportDao.read(128).toString());
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidReportIdThrowException() throws IdInvalidExcepiton {
        reportDao.read(0);
    }

    @Test
    public void deleteInvalidEntityReturnFalse() {
        assertFalse(reportDao.delete(report.getReportId()));
    }
}