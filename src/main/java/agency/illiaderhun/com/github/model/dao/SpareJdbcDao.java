package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.SpareDao;
import agency.illiaderhun.com.github.model.entities.Spare;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * SpareJdbcDao works with Spare's entities
 * can do all CRUD operations and readByName
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class SpareJdbcDao implements SpareDao<Spare, Integer> {

    private static final Logger LOGGER = Logger.getLogger(SpareJdbcDao.class.getSimpleName());

    @NotNull
    private DataSource dataSource;

    @NotNull
    private Properties properties;

    public SpareJdbcDao(DataSource dataSource, Properties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    /**
     * Select spare by name
     *
     * @param name spare's name for searching
     * @return valid entity if it exist
     * @exception InvalidSearchingString if name doesn't exist
     */
    @Override
    public Spare readByName(String name) throws InvalidSearchingString {
        LOGGER.info("method readByName start with name: " + name);
        Spare theSpare = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByName"))){

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theSpare = madeSpare(resultSet);
            } else {
                LOGGER.warn("method readByName throw InvalidSearchingString Exception with message: \"Invalid spare's name: " + name + "\"");
                throw new InvalidSearchingString("Invalid spare's name: " + name);
            }

        } catch (SQLException e) {
            LOGGER.warn("method readByName caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method readByName return spare: " + theSpare);
        return theSpare;
    }

    /**
     * Create new {@link Spare} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid {@link Spare}
     * @throws SQLException in case some problem with resultSet
     */
    private Spare madeSpare(ResultSet resultSet) throws SQLException{
        LOGGER.info("method madeSpare start with resultSet: " + resultSet);
        Integer spareId = resultSet.getInt("detail_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Integer quantity = resultSet.getInt("quantity");
        BigDecimal price = resultSet.getBigDecimal("price");

        Spare theSpare = new Spare.Builder(spareId, name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .build();
        LOGGER.info("method madeSpare return spare " + theSpare);
        return theSpare;
    }

    /**
     * Create spare in database.
     *
     * @param spare for creating.
     * @return false if Spare already exist. True if creation is success.
     */
    @Override
    public boolean create(Spare spare) {
        LOGGER.info("method create start with spare: " + spare);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("insert"))){

            setStatement(statement, spare);
            statement.executeUpdate();

            spare.setDetailId(setInsertedId());

            result = true;

        } catch (SQLException e) {
            LOGGER.warn("method create caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method create return result of creation: " + result);
        return result;
    }

    /**
     * After spare has been created
     * this method select inserted and auto.generated detailId
     *
     * @return new spare's inserted and auto.generated id
     * @throws SQLException in case some problem with statement
     */
    private int setInsertedId() throws SQLException {
        LOGGER.info("method setInsertedId start with no parameters");
        int spareId = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("readInsertedId"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                spareId = resultSet.getInt("detail_id");
            }
        }
        LOGGER.info("method setInsertedId return spareId: " + spareId);
        return spareId;
    }

    private void setStatement(PreparedStatement statement, Spare spare) throws SQLException {
        statement.setString(1, spare.getName());
        statement.setString(2, spare.getDescription());
        statement.setInt(3, spare.getQuantity());
        statement.setBigDecimal(4, spare.getPrice());
    }

    /**
     * Select spare by spareId.
     *
     * @param detailId for select.
     * @return return valid entity if it exist.
     * @exception IdInvalid in case nothing exist by this detailId
     */
    @Override
    public Spare read(Integer detailId) throws IdInvalid {
        LOGGER.info("method read start with entityID: " + detailId);
        Spare theSpare = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, detailId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theSpare = madeSpare(resultSet);
            } else {
                LOGGER.error("method read throw IdInvalid Exception with message: \"Invalid spare's ID : \"" + detailId);
                throw new IdInvalid("Invalid spare's ID : " + detailId);
            }

        } catch (SQLException e) {
            LOGGER.warn("method read caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method read return spare: " + theSpare);
        return theSpare;
    }

    /**
     * Update spare by detailId
     *
     * @param spare with new info for updating
     * @return true if spare has been updated else false
     */
    @Override
    public boolean update(Spare spare) {
        LOGGER.info("method update start with spare: " + spare);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("update"))){
            setStatement(statement, spare);
            statement.setInt(5, spare.getDetailId());

            result = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            LOGGER.warn("method update caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method update return spare: " + spare);
        return result;
    }

    @Override
    public boolean delete(Integer entityId) {
        LOGGER.info("method delete start with entityId " + entityId);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("delete"))){
            statement.setInt(1, entityId);

            result = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            LOGGER.warn("method delete caught SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method delete return result: " + result);
        return result;
    }
}
