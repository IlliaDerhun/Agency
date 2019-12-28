package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * UserJdbcDao works with User's entities
 * can do all CRUD operations and readByEmail
 *
 * @author Illia Derhun
 * @version 1.0
 */

public class UserJdbcDao implements UserDao<User, Integer> {

    private static final Logger LOGGER = Logger.getLogger(UserJdbcDao.class.getSimpleName());

    private DataSource dataSource;

    private Properties properties;

    public UserJdbcDao(DataSource dataSource, Properties properties){
        this.dataSource = dataSource;
        this.properties = properties;
    }

    /**
     * Select user by its email
     *
     * @param eMail user's email for searching
     * @return valid entity if it exist
     * @exception InvalidSearchingString if email invalid
     */
    @Override
    public User readByEmail(String eMail) throws InvalidSearchingString {
        LOGGER.info("readByEmail start with: email: " + eMail);
        User theUser = null;
        LOGGER.info("readByEmail query: " + properties.getProperty("selectByEmail"));
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByEmail"))){
            statement.setString(1, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theUser = madeUser(resultSet);
            } else {
                LOGGER.error("nonexistent user email " + eMail);
                throw new InvalidSearchingString("Invalid user's email");
            }
        } catch (SQLException e) {
            LOGGER.error("method readByEmail caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("readByEmail end & return: " + theUser);
        return theUser;
    }

    /**
     * Create User in database.
     *
     * @param user for creating.
     * @return false if User already exist. True if creation is success.
     */
    @Override
    public boolean create(User user) {
        LOGGER.info("method create start with user: " + user);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("insert"))){
            setStatement(statement, user);
            statement.executeUpdate();
            user.setUserId(setInsertedId());
            result = true;
        } catch (SQLException e) {
            LOGGER.error("method create caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method create return user: " + user);
        return result;
    }

    /**
     * After user has been created
     * this method select inserted and auto.generated userId
     *
     * @return new user's inserted and auto.generated id
     * @throws SQLException in case some problem with statement
     */
    private int setInsertedId() throws SQLException {
        LOGGER.info("setInsertedId start with no parameters");
        int userId = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("readInsertedId"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                userId = resultSet.getInt("user_id");
            }
        }
        LOGGER.info("setInsertedId return userId: " + userId);
        return userId;
    }

    private void setStatement(PreparedStatement statement, User user) throws SQLException {
        LOGGER.info("method setStatement start with parameters: statement: " + statement + ". And user: " + user);
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.geteMail());
        statement.setString(4, user.getPhone());
        statement.setString(5, user.getCatchword());
        statement.setInt(6, user.getRoleId());
        LOGGER.info("method setStatement end with parameters: statement: " + statement + ". And user: " + user);
    }

    /**
     * Select User by userId.
     *
     * @param entityId for select.
     * @return return valid entity if it exist.
     * If entity does not exist throw new Exception "Could not find user :"
     */
    @Override
    public User read(Integer entityId) throws IdInvalid {
        LOGGER.info("method read start with entityId: " + entityId);
        User theUser = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, entityId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theUser = madeUser(resultSet);
            } else {
                LOGGER.error("nonexistent userId : " + entityId);
                throw new IdInvalid("Invalid user's ID : " + entityId);
            }
        } catch (SQLException e) {
            LOGGER.error("method read caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method read return user: " + theUser);
        return theUser;
    }

    /**
     * Create new {@link User} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid {@link User}
     * @throws SQLException in case some problem with resultSet
     */
    private User madeUser(ResultSet resultSet) throws SQLException {
        LOGGER.info("method madeUser start with resultSet: " + resultSet);
        User theUser;

        Integer userId = resultSet.getInt("user_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String eMail = resultSet.getString("e_mail");
        String phone = resultSet.getString("phone");
        String catchword = resultSet.getString("catchword");
        Integer roleId = resultSet.getInt("role_id");

        theUser = new User.Builder(userId, roleId, eMail)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .catchword(catchword)
                .build();
        LOGGER.info("method madeUser return user: " + theUser);
        return theUser;
    }

    /**
     * Update user by userId
     *
     * @param user with new info for updating
     * @return true if user has been updated else false
     */
    @Override
    public boolean update(User user) {
        LOGGER.info("method update start with user: " + user);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("update"))){
            setStatement(statement, user);
            statement.setInt(7, user.getUserId());

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method update caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method update return result: " + result);
        return result;
    }

    @Override
    public boolean delete(Integer entityId) {
        LOGGER.info("method delete start with entityId" + entityId);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("delete"))){
            statement.setInt(1, entityId);

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method delete caught SQLException" + e);
            e.printStackTrace();
        }
        LOGGER.info("method delete return result: " + result);
        return result;
    }
}
