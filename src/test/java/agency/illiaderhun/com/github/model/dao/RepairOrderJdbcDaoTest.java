package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
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
public class RepairOrderJdbcDaoTest {

    @Mock
    private SJDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    private RepairOrder repairOrder;

    private Properties properties = QueriesManager.getProperties("repairOrder");

    private RepairOrderDao<RepairOrder, Integer> repairOrderDao;

    @Before
    public void setUp() throws Exception {
        repairOrderDao = new RepairOrderJdbcDao(dataSource, properties);
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        repairOrder = new RepairOrder.Builder("some device", 1)
                .orderId(114)
                .description("some description")
                .managerId(2)
                .masterId(3)
                .date(new Date(118, 7, 16))
                .price(45.12)
                .build();

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt("order_id")).thenReturn(repairOrder.getOrderId());
        when(resultSet.getString("device_name")).thenReturn(repairOrder.getDeviceName());
        when(resultSet.getString("description")).thenReturn(repairOrder.getDescription());
        when(resultSet.getInt("customer_id")).thenReturn(repairOrder.getCustomerId());
        when(resultSet.getInt("manager_id")).thenReturn(repairOrder.getManagerId());
        when(resultSet.getInt("master_id")).thenReturn(repairOrder.getMasterId());
        when(resultSet.getDate("date")).thenReturn(repairOrder.getDate());
        when(resultSet.getBigDecimal("order_price")).thenReturn(repairOrder.getPrice());
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidCustomerIdThrowException() throws IdInvalidExcepiton {
        repairOrderDao.readByCustomerId(2);
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidManagerIdThrowException() throws IdInvalidExcepiton {
        repairOrderDao.readByManagerId(3);
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidMasterIdThrowException() throws IdInvalidExcepiton {
        repairOrderDao.readByMasterId(1);
    }

    @Test(expected = NullPointerException.class)
    public void createRepairOrderByInValidEntityThrowException() {
        repairOrderDao.create(null);
    }

    @Test
    public void readByValidRepairOrderIdReturnTrue() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        assertEquals(repairOrder, repairOrderDao.read(114));
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidIdThrowException() throws IdInvalidExcepiton {
        repairOrderDao.read(0);
    }

    @Test
    public void deleteByInvalidRepairOrderIdReturnFalse() {
        assertFalse(repairOrderDao.delete(0));
    }
}