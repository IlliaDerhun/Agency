package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoFactory.ReportDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.ReportDao;
import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private ReportDao<Report, Integer> reportDao = ReportDaoFactory.getReport("mysql");

    private Properties properties = QueriesManager.getProperties("report");

    @Before
    public void setUp() throws Exception {
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        report = new Report.Builder("Some description", 87)
                .reportId(128)
                .replacedPart("2")
                .theWorkDone("Some work done")
                .date(new Date(118, 7, 7))
                .build();

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(report.getReportId());
        when(resultSet.getString(2)).thenReturn(report.getReplacedPart());
        when(resultSet.getString(3)).thenReturn(report.getBreakingDescription());
        when(resultSet.getString(4)).thenReturn(report.getTheWorkDone());
        when(resultSet.getInt(5)).thenReturn(report.getOrderId());
        when(resultSet.getDate(6)).thenReturn(report.getDate());
    }

    @Test
    public void readByOrderIdReturnValidEntity() throws IdInvalid {
        assertEquals(report.toString(), reportDao.readByOrderId(87).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidOrderIdThrowException() throws IdInvalid {
        new ReportJdbcDao(dataSource, properties).readByOrderId(87);
    }

    @Test
    public void createValidEntityReturnTrue() {
        assertTrue(new ReportJdbcDao(dataSource, properties).create(report));
    }

    @Test(expected = NullPointerException.class)
    public void createInValidEntityThrowException() {
        new ReportJdbcDao(dataSource, properties).create(null);
    }

    @Test
    public void readByReportIdReturnValidEntity() throws IdInvalid {
        assertEquals(report.toString(), reportDao.read(128).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidReportIdThrowException() throws IdInvalid {
        reportDao.read(0);
    }

    @Test
    public void deleteInvalidEntityReturnFalse() {
        assertFalse(new ReportJdbcDao(dataSource, properties).delete(report.getReportId()));
    }
}