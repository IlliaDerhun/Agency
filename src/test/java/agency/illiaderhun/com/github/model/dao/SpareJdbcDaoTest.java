package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoInterface.SpareDao;
import agency.illiaderhun.com.github.model.entities.Spare;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingStringException;
import com.sun.istack.internal.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpareJdbcDaoTest {

    @Mock
    private SJDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    private Spare spare;

    private Properties properties = QueriesManager.getProperties("spare");

    @NotNull
//    private SpareDao<Spare, Integer> spareDao = new SpareJdbcDao(ConnectionManager.testConnection(), QueriesManager.getProperties("spare"));
    private SpareDao<Spare, Integer> spareDao;
    @Before
    public void setUp() throws Exception {
        spareDao = new SpareJdbcDao(dataSource, QueriesManager.getProperties("spare"));
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        spare = new Spare.Builder(1, "Engine")
                .description("Engine for small coffee machine: Viena, Trevi...")
                .quantity(4)
                .price(243.55)
                .build();

//        when(resultSet.first()).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("detail_id")).thenReturn(spare.getDetailId());
        when(resultSet.getString("name")).thenReturn(spare.getName());
        when(resultSet.getString("description")).thenReturn(spare.getDescription());
        when(resultSet.getInt("quantity")).thenReturn(spare.getQuantity());
        when(resultSet.getBigDecimal("price")).thenReturn(spare.getPrice());
    }

    @Test
    public void readByValidNameReturnValidEntity() throws InvalidSearchingStringException, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(spare, spareDao.readByName("Engine"));
    }

    @Test(expected = InvalidSearchingStringException.class)
    public void readByInvalidNameThrowException() throws InvalidSearchingStringException {
       spareDao.readByName("");
    }

    @Test
    public void createNewValidSpareReturnTrue() {
        spareDao.create(spare);
    }

    @Test(expected = NullPointerException.class)
    public void createNewInValidSpareReturnFalse() {
        spareDao.create(null);
    }

    @Test
    public void readExistEntityReturnValidSpare() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(spare, spareDao.read(1));
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readNonExistEntityReturnValidSpare() throws IdInvalidExcepiton {
        spareDao.read(0);
    }

    @Test
    public void deleteInValidEntityReturnFalse() {
        assertFalse(spareDao.delete(0));
    }
}