package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.UserDao;
import agency.illiaderhun.com.github.model.entities.User;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserJdbcDao works with User's entities
 * can do all CRUD operations and readByEmail
 *
 * @author Illia Derhun
 * @version 1.0
 */

public class UserJdbcDao implements UserDao<User, Integer> {

    private static final Logger LOGGER = Logger.getLogger(UserJdbcDao.class.getSimpleName());

    @NotNull
    private DataSource dataSource;

    public UserJdbcDao(DataSource dataSource){
        this.dataSource = dataSource;
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
        User theUser = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.SELECT_BY_EMAIL.QUERY)){
            statement.setString(1, eMail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theUser = madeUser(resultSet);
            } else {
                LOGGER.error("nonexistent user email " + eMail);
                throw new InvalidSearchingString("Invalid user's email");
            }
        } catch (SQLException e) {
            LOGGER.error("-> Some problem with reading");
            LOGGER.error("-> " + e);
            e.printStackTrace();
        }

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
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.INSERT.QUERY)){
            setStatement(statement, user);
            statement.executeUpdate();
            user.setUserId(setInsertedId());
            result = true;
        } catch (SQLException e) {
            LOGGER.error("has got some problem with creation");
            LOGGER.error(e);
            e.printStackTrace();
        }
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.READ_INSERTED_ID.QUERY)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                return resultSet.getInt("user_id");
            }
        }

        return 0;
    }

    private void setStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.geteMail());
        statement.setString(4, user.getPhone());
        statement.setString(5, user.getCatchword());
        statement.setInt(6, user.getRoleId());
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
        User theUser = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.SELECT.QUERY)){
            statement.setInt(1, entityId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theUser = madeUser(resultSet);
            } else {
                LOGGER.error("nonexistent userId : " + entityId);
                throw new IdInvalid("Invalid user's ID : " + entityId);
            }
        } catch (SQLException e) {
            LOGGER.error("-> Some problem with reading");
            LOGGER.error("-> " + e);
            e.printStackTrace();
        }

        return theUser;
    }

    /**
     * Create new {@link User} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid User
     * @throws SQLException in case some problem with resultSet
     */
    private User madeUser(ResultSet resultSet) throws SQLException {
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
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.UPDATE.QUERY)){
            setStatement(statement, user);
            statement.setInt(7, user.getUserId());

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("Some problem with updating");
            LOGGER.error(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Integer entityId) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlUser.DELETE.QUERY)){
            statement.setInt(1, entityId);

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("Some problem with user deleting by userId" + entityId);
            LOGGER.error(e);
            e.printStackTrace();
        }
        return result;
    }

    enum SqlUser{

        INSERT("INSERT INTO agency_test.user(first_name, last_name, e_mail, phone, catchword, role_id)" +
                " VALUES(?, ?, ?, ?, ?, ?);"),
        SELECT("SELECT *" +
                " FROM agency_test.user" +
                " WHERE user_id = ?;"),
        UPDATE("UPDATE agency_test.user SET" +
                " first_name = ?," +
                " last_name = ?," +
                " e_mail = ?," +
                " phone = ?," +
                " catchword = ?,"+
                " role_id = ?" +
                " WHERE user_id = ?;"),
        DELETE("DELETE FROM agency_test.user WHERE user_id = ?;"),

        SELECT_BY_EMAIL("SELECT *" +
                " FROM agency_test.user" +
                " WHERE e_mail = ?;"),

        READ_INSERTED_ID("SELECT max(user_id) AS user_id FROM agency_test.user;");

        String QUERY;
        SqlUser(String QUERY){
            this.QUERY = QUERY;
        }
    }
}
