package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.InvalidSearchingString;

public interface SpareDao<Spare, Integer> extends Dao<Spare, Integer> {

    Spare readByName(String name) throws InvalidSearchingString;
}
