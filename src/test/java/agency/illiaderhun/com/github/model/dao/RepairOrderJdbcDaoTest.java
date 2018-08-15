package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
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
import static org.mockito.Matchers.booleanThat;
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

    private RepairOrderDao<RepairOrder, Integer> repairOrderDao = new RepairOrderJdbcDao(ConnectionManager.testConnection(), QueriesManager.getProperties("repairOrder"));

    @Before
    public void setUp() throws Exception {
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        repairOrder = new RepairOrder.Builder("some device", 1)
                .orderId(87)
                .description("some description")
                .managerId(2)
                .masterId(3)
                .date(new Date(118, 7, 7))
                .price(123.45)
                .build();

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(repairOrder.getOrderId());
        when(resultSet.getString(2)).thenReturn(repairOrder.getDeviceName());
        when(resultSet.getString(3)).thenReturn(repairOrder.getDescription());
        when(resultSet.getInt(4)).thenReturn(repairOrder.getCustomerId());
        when(resultSet.getInt(5)).thenReturn(repairOrder.getManagerId());
        when(resultSet.getInt(6)).thenReturn(repairOrder.getMasterId());
        when(resultSet.getDate(7)).thenReturn(repairOrder.getDate());
        when(resultSet.getBigDecimal(8)).thenReturn(repairOrder.getPrice());
    }

    @Test
    public void readByCustomerIdReturnArrayWithAllCustomersOrders() throws IdInvalid {
        assertEquals(repairOrder.toString(), repairOrderDao.readByCustomerId(1).get(2).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidCustomerIdThrowException() throws IdInvalid {
        new RepairOrderJdbcDao(dataSource, properties).readByCustomerId(2);
    }

    @Test
    public void readByManagerIdReturnArrayWithAllCustomersOrders() throws IdInvalid {
        assertEquals(repairOrder.toString(), repairOrderDao.readByManagerId(2).get(2).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidManagerIdThrowException() throws IdInvalid {
        new RepairOrderJdbcDao(dataSource, properties).readByManagerId(3);
    }

    @Test
    public void readByMasterIdReturnArrayWithAllCustomersOrders() throws IdInvalid {
        assertEquals(repairOrder.toString(), repairOrderDao.readByMasterId(3).get(2).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidMasterIdThrowException() throws IdInvalid {
        new RepairOrderJdbcDao(dataSource, properties).readByMasterId(1);
    }

    @Test(expected = NullPointerException.class)
    public void createRepairOrderByInValidEntityThrowException() {
        new RepairOrderJdbcDao(dataSource, properties).create(null);
    }

    @Test
    public void readByValidRepairOrderIdReturnTrue() throws IdInvalid {
        assertEquals(repairOrder.toString(), repairOrderDao.read(87).toString());
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidIdThrowException() throws IdInvalid {
        new RepairOrderJdbcDao(dataSource, properties).read(0);
    }

    @Test
    public void deleteByInvalidRepairOrderIdReturnFalse() {
        assertFalse(new RepairOrderJdbcDao(dataSource, properties).delete(0));
    }
}