package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoInterface.FeedbackDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.*;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackJdbcDaoTest {

    @Mock
    private SJDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    private Feedback feedback;

    private Properties properties = QueriesManager.getProperties("feedback");

    private FeedbackDao<Feedback, Integer> feedbackDao;

    @Before
    public void setUp() throws Exception {
        feedbackDao = new FeedbackJdbcDao(dataSource, properties);
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        feedback = new Feedback("Хорошо сделали, спасибо", 2);
        feedback.setCommentId(33);
        feedback.setDate(new Date(118, 7, 19));

        when(resultSet.first()).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("comment_id")).thenReturn(feedback.getCommentId());
        when(resultSet.getString("comment")).thenReturn(feedback.getComment());
        when(resultSet.getInt("report_id")).thenReturn(feedback.getReportId());
        when(resultSet.getDate("date")).thenReturn(feedback.getDate());
    }

    @Test
    public void readByValidReportIdReturnValidEntity() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(feedback.toString(), feedbackDao.readByReportId(2).toString());
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidReportIdThrowException() throws IdInvalidExcepiton {
        new FeedbackJdbcDao(dataSource, properties).readByReportId(0);
    }

    @Test(expected = NullPointerException.class)
    public void createFeedbackByInValidEntityThrowException() {
        new FeedbackJdbcDao(dataSource, properties).create(null);
    }

    @Test
    public void readByValidFeedbackIdReturnValidEntity() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(feedback.toString(), feedbackDao.read(33).toString());
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInValidFeedbackIdThrowException() throws IdInvalidExcepiton {
        feedbackDao.read(0);
    }

    @Test
    public void delete() {
        feedbackDao.delete(0);
    }
}