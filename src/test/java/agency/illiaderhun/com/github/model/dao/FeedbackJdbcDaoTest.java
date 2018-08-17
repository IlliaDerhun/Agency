package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoFactory.FeedbackDaoFactory;
import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.FeedbackDao;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.contains;
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

    private FeedbackDao<Feedback, Integer> feedbackDao = new FeedbackJdbcDao(ConnectionManager.testConnection(), QueriesManager.getProperties("feedback"));

    @Before
    public void setUp() throws Exception {
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        feedback = new Feedback("Быстро сделали. Варит кофе хорошо, пена тоже есть", 2);
        feedback.setCommentId(1);
        feedback.setDate(new Date(118, 6, 19));

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(feedback.getCommentId());
        when(resultSet.getString(2)).thenReturn(feedback.getComment());
        when(resultSet.getInt(3)).thenReturn(feedback.getReportId());
        when(resultSet.getDate(4)).thenReturn(feedback.getDate());
    }

    @Test
    public void readByValidReportIdReturnValidEntity() throws IdInvalid {
        assertEquals(feedback.toString(), feedbackDao.readByReportId(2).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidReportIdThrowException() throws IdInvalid {
        new FeedbackJdbcDao(dataSource, properties).readByReportId(0);
    }

    @Test(expected = NullPointerException.class)
    public void createFeedbackByInValidEntityThrowException() {
        new FeedbackJdbcDao(dataSource, properties).create(null);
    }

    @Test
    public void readByValidFeedbackIdReturnValidEntity() throws IdInvalid {
        assertEquals(feedback.toString(), feedbackDao.read(1).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInValidFeedbackIdThrowException() throws IdInvalid {
        feedbackDao.read(0);
    }

    @Test
    public void delete() {
        assertFalse(new FeedbackJdbcDao(dataSource, properties).delete(0));
    }
}