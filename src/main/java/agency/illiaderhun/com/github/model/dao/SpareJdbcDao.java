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
            LOGGER.warn("method readByName catch SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method readByName return spare: " + theSpare);
        return theSpare;
    }

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
            LOGGER.warn("method create catch SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method create return result of creation: " + result);
        return result;
    }

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

    @Override
    public Spare read(Integer entityId) throws IdInvalid {
        LOGGER.info("method read start with entityID: " + entityId);
        Spare theSpare = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, entityId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                theSpare = madeSpare(resultSet);
            } else {
                LOGGER.error("method read throw IdInvalid Exception with message: \"Invalid spare's ID : \"" + entityId);
                throw new IdInvalid("Invalid spare's ID : " + entityId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        LOGGER.info("method read return spare: " + theSpare);
        return theSpare;
    }

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
            LOGGER.warn("method update catch SQLException " + e);
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
            LOGGER.warn("method delete catch SQLException " + e);
            e.printStackTrace();
        }
        LOGGER.info("method delete return result: " + result);
        return result;
    }
}
