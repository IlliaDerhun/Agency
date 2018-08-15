package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

import java.util.ArrayList;

public interface RepairOrderDao<RepairOrder, Integer> extends Dao<RepairOrder, Integer> {

    ArrayList<RepairOrder> readByCustomerId(Integer customerId) throws IdInvalid;
    ArrayList<RepairOrder> readByManagerId(Integer managerId) throws IdInvalid;
    ArrayList<RepairOrder> readByMasterId(Integer masterId) throws IdInvalid;
    Integer findFreeManager();
    Integer findFreeMaster();
}
