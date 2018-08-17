package agency.illiaderhun.com.github.controller.service;

import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

import java.util.ArrayList;

public interface RepairOrderService {

    void deleteOrder(Integer orderId);
    void createNewOrder(RepairOrder newRepairOrder);
    ArrayList<RepairOrder> getCustomerOrders(Integer userId);
    ArrayList<RepairOrder> getManagerOrders(Integer userId);
    ArrayList<RepairOrder> getMasterOrders(Integer userId);
    RepairOrder readOrderById(Integer orderId) throws IdInvalid;
    void updateOrder(RepairOrder repairOrder);
}
