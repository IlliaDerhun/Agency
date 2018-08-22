package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;

import java.util.ArrayList;

public interface RepairOrderDao<RepairOrder, Integer> extends Dao<RepairOrder, Integer> {

    ArrayList<RepairOrder> readByCustomerId(Integer customerId) throws IdInvalidExcepiton;
    ArrayList<RepairOrder> readByManagerId(Integer managerId) throws IdInvalidExcepiton;
    ArrayList<RepairOrder> readByMasterId(Integer masterId) throws IdInvalidExcepiton;
    Integer findFreeManager();
    Integer findFreeMaster();
}
