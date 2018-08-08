package agency.illiaderhun.com.github.model.daoInterface;
/**
 * Basic DAO interface which describes all required, CRUD,
 * operations for all entities.
 *
 * @author Illia Derhun
 * @version 1.0
 */
public interface Dao<Entity, Integer> {

    boolean create(Entity entity);
    Entity read(Integer entityId);
    boolean update(Entity entity);
    boolean delete(Integer entityId);

}