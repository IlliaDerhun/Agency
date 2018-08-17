package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.ConnectionManager;
import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoFactory.SpareDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.SpareDao;
import agency.illiaderhun.com.github.model.entities.Spare;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
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
    private SpareDao<Spare, Integer> spareDao = new SpareJdbcDao(ConnectionManager.testConnection(), QueriesManager.getProperties("spare"));

    @Before
    public void setUp() throws Exception {
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        spare = new Spare.Builder(1, "Engine")
                .description("Engine for small coffee machine: Viena, Trevi...")
                .quantity(4)
                .price(243.55)
                .build();

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(spare.getDetailId());
        when(resultSet.getString(2)).thenReturn(spare.getName());
        when(resultSet.getString(3)).thenReturn(spare.getDescription());
        when(resultSet.getInt(4)).thenReturn(spare.getQuantity());
        when(resultSet.getBigDecimal(5)).thenReturn(spare.getPrice());
    }

    @Test
    public void readByValidNameReturnValidEntity() throws InvalidSearchingString {
        assertEquals(spare, spareDao.readByName("Engine"));
    }

    @Test(expected = InvalidSearchingString.class)
    public void readByInvalidNameThrowException() throws InvalidSearchingString {
        new SpareJdbcDao(dataSource, properties).readByName("");
    }

    @Test(expected = NullPointerException.class)
    public void readByNameWithNullDataSourceThrowNPE() throws InvalidSearchingString {
        new SpareJdbcDao(null, properties).readByName("Engine");
    }

    @Test
    public void createNewValidSpareReturnTrue() {
        assertTrue(new SpareJdbcDao(dataSource, properties).create(spare));
    }

    @Test(expected = NullPointerException.class)
    public void createNewInValidSpareReturnFalse() {
        new SpareJdbcDao(dataSource, QueriesManager.getProperties("spare")).create(null);
    }

    @Test
    public void readExistEntityReturnValidSpare() throws IdInvalid {
        assertEquals(spare, spareDao.read(1));
    }

    @Test(expected = IdInvalid.class)
    public void readNonExistEntityReturnValidSpare() throws IdInvalid {
        spareDao.read(0);
    }

    @Test
    public void deleteInValidEntityReturnFalse() {
        assertFalse(new SpareJdbcDao(dataSource, properties).delete(0));
    }
}