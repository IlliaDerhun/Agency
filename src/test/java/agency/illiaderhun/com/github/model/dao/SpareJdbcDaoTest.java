package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoFactory.SpareDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.SpareDao;
import agency.illiaderhun.com.github.model.entities.Spare;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    @NotNull
    SpareDao<Spare, Integer> spareDao = SpareDaoFactory.getSpare("mysql");

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
        new SpareJdbcDao(dataSource).readByName("");
    }

    @Test(expected = NullPointerException.class)
    public void readByNameWithNullDataSourceThrowNPE() throws InvalidSearchingString {
        new SpareJdbcDao(null).readByName("Engine");
    }

    @Test
    public void create() {
    }

    @Test
    public void read() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}