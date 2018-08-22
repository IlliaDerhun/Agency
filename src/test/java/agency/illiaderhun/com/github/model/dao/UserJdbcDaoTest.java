package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.QueriesManager;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingStringException;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserJdbcDaoTest {

    @Mock
    private SJDataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet resultSet;

    private User user;

//    private UserDao<User, Integer> userDao = new UserJdbcDao(ConnectionManager.testConnection(), QueriesManager.getProperties("user"));
    private UserDao<User, Integer> userDao;
    @Before
    public void setUp() throws Exception {
        userDao = new UserJdbcDao(dataSource, QueriesManager.getProperties("user"));
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        user = new User.Builder(1, 1, "alex.petrov@gmail.com")
                .firstName("Александр")
                .lastName("Петров")
                .phone("+38(095)45-55-782")
                .catchword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                .build();

//        when(resultSet.first()).thenReturn(true);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("user_id")).thenReturn(user.getUserId());
        when(resultSet.getString("first_name")).thenReturn(user.getFirstName());
        when(resultSet.getString("last_name")).thenReturn(user.getLastName());
        when(resultSet.getString("e_mail")).thenReturn(user.geteMail());
        when(resultSet.getString("phone")).thenReturn(user.getPhone());
        when(resultSet.getString("catchword")).thenReturn(user.getCatchword());
        when(resultSet.getInt("role_id")).thenReturn(user.getRoleId());
    }

    @Test
    public void readByValidEmailReturnValidEntity() throws InvalidSearchingStringException, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(user, userDao.readByEmail("alex.petrov@gmail.com"));
    }

    @Test(expected = InvalidSearchingStringException.class)
    public void couldntReadByInvalidEmailThroeException() throws InvalidSearchingStringException, SQLException {
        when(statement.executeQuery("SELECT * FROM agency_test.user WHERE e_mail = invalidEmail;")).thenReturn(null);
        userDao.readByEmail("invalidEmail");
    }

    @Test
    public void createValidUserReturnTrue() {
        assertTrue(userDao.create(user));
    }

    @Test(expected = NullPointerException.class)
    public void nullCreateThrowException() {
        userDao.create(null);
    }

    @Test
    public void readByValidIdReturnValidEntity() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        assertEquals(user, userDao.read(1));
    }

    @Test(expected = IdInvalidExcepiton.class)
    public void readByInvalidIdThrowException() throws IdInvalidExcepiton, SQLException {
        when(statement.executeQuery("SELECT * FROM agency_test.user WHERE user_id = 1;")).thenReturn(null);
        userDao.read(1);
    }

    @Test
    public void deleteNoExistEntityReturnFalse() {
        assertFalse(userDao.delete(0));
    }
}