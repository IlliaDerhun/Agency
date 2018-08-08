package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoFactory.UserDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osjava.sj.loader.SJDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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

    private UserDao<User, Integer> userDao = UserDaoFactory.getUser("mysql");

    @Before
    public void setUp() throws Exception {
        assertNotNull(dataSource);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(dataSource.getConnection()).thenReturn(connection);

        user = new User.Builder(1, 1, "alex.petrov@gmail.com")
                .firstName("Александр")
                .lastName("Петров")
                .phone("+38(095)45-55-782")
                .catchword("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92")
                .build();

        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(user.getUserId());
        when(resultSet.getString(2)).thenReturn(user.getFirstName());
        when(resultSet.getString(3)).thenReturn(user.getLastName());
        when(resultSet.getString(4)).thenReturn(user.geteMail());
        when(resultSet.getString(5)).thenReturn(user.getPhone());
        when(resultSet.getInt(6)).thenReturn(user.getRoleId());
    }

    @Test
    public void readByValidEmail() throws InvalidSearchingString{
        assertEquals(user, userDao.readByEmail("alex.petrov@gmail.com"));
    }

    @Test(expected = InvalidSearchingString.class)
    public void couldntReadByInvalidEmail() throws InvalidSearchingString{
        new UserJdbcDao(dataSource).readByEmail(null);
    }

    @Test
    public void create() {
        new UserJdbcDao(dataSource).create(user);
    }

    @Test(expected = NullPointerException.class)
    public void nullCreateThrowException() {
        userDao.create(null);
    }

    @Test
    public void readByValidId() throws IdInvalid {
        assertEquals(user, userDao.read(1));
    }

    @Test(expected = IdInvalid.class)
    public void readByInvalidId() throws IdInvalid {
        assertEquals(user, new UserJdbcDao(dataSource).read(1));
    }

    @Test
    public void deleteNoExistEntity() {
        assertFalse(new UserJdbcDao(dataSource).delete(0));
    }
}