package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.SpareDao;
import agency.illiaderhun.com.github.model.entities.Spare;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;
import com.sun.istack.internal.NotNull;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpareJdbcDao implements SpareDao<Spare, Integer> {

    @NotNull
    private DataSource dataSource;

    public SpareJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Spare readByName(String name) throws InvalidSearchingString {
        Spare theSpare = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlSpare.SELECT_BY_NAME.QUERY)){

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theSpare = madeSpare(resultSet);
            } else {
                throw new InvalidSearchingString("Invalid spare's name: " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theSpare;
    }

    private Spare madeSpare(ResultSet resultSet) throws SQLException{
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

        return theSpare;
    }

    @Override
    public boolean create(Spare spare) {
        return false;
    }

    @Override
    public Spare read(Integer entityId) throws IdInvalid {
        return null;
    }

    @Override
    public boolean update(Spare spare) {
        return false;
    }

    @Override
    public boolean delete(Integer entityId) {
        return false;
    }

    enum SqlSpare{

        INSERT("INSERT INTO agency_test.spare(name, description, quantity, price)" +
                " VALUES(?, ?, ?, ?);"),
        SELECT("SELECT *" +
                " FROM agency_test.spare" +
                " WHERE detail_id = ?;"),
        UPDATE("UPDATE agency_test.spare SET" +
                " name = ?," +
                " description = ?," +
                " quantity = ?," +
                " price = ?" +
                " WHERE detail_id = ;"),
        DELETE("DELETE FROM agency_test.spare WHERE detail_id = ?;"),

        SELECT_BY_NAME("SELECT *" +
                " FROM agency_test.spare" +
                " WHERE name = ?;"),
        READ_INSERTED_ID("SELECT max(detail_id) AS detail_id FROM agency_test.spare;");


        String QUERY;
        SqlSpare(String QUERY){
            this.QUERY = QUERY;
        }
    }
}
