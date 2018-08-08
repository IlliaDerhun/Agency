package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.dao.daoFactory.UserDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserJdbcDaoTest {

    @NotNull
    private UserDao<User, Integer> userDao = UserDaoFactory.getUser("mysql");

    private User user;

    @Before
    public void setUp(){
        user = new User.Builder(0, 1, "simple@mail.com")
                .build();
    }

    @After
    public void tearDown(){
    }

    @Test
    public void readByEmail() {
        assertEquals(userDao.read(1), userDao.readByEmail("alex.petrov@gmail.com"));
    }

    @Test
    public void create() {
        assertTrue(userDao.create(user));
        userDao.delete(user.getUserId());
    }

    @Test
    public void read() {
        userDao.create(user);
        assertEquals(user, userDao.read(user.getUserId()));
        userDao.delete(user.getUserId());
    }

    @Test
    public void update() {
        userDao.create(user);
        user.setFirstName("NewFirstName");
        user.setLastName("NewLastName");
        assertTrue(userDao.update(user));
        assertEquals(user, userDao.read(user.getUserId()));
        userDao.delete(user.getUserId());
    }

    @Test
    public void delete() {
        userDao.create(user);
        assertTrue(userDao.delete(user.getUserId()));
    }

}