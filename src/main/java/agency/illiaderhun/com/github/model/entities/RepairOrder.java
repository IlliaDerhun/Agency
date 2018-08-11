package agency.illiaderhun.com.github.model.entities;

import agency.illiaderhun.com.github.annotations.Column;
import agency.illiaderhun.com.github.annotations.JoinColumn;
import agency.illiaderhun.com.github.annotations.JoinTable;
import agency.illiaderhun.com.github.annotations.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.Objects;

/**
 * RepairOrder represents order from {@link User} customer
 * about some problems device to manager
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Table(name = "repair_order")
public class RepairOrder {

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "description")
    private String description;

    @Column(name = "customer_id")
    @JoinTable(name = "user", joinsColums = @JoinColumn(name = "customer_id"),
            inverseJoinColumn = @JoinColumn(name = "user_id"))
    private int customerId;

    @Column(name = "manager_id")
    @JoinTable(name = "user", joinsColums = @JoinColumn(name = "manager_id"),
            inverseJoinColumn = @JoinColumn(name = "user_id"))
    private int managerId;

    @Column(name = "master_id")
    @JoinTable(name = "user", joinsColums = @JoinColumn(name = "master_id"),
            inverseJoinColumn = @JoinColumn(name = "user_id"))
    private int masterId;

    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private BigDecimal price;

    public static class Builder{

        // Required fields
        private String deviceName;
        private int cusotmerId;

        // Optional fields
        private int orderId = 0;
        private String description = "Default description";
        private int managerId = 2;
        private int masterId = 3;
        private Date date;
        private BigDecimal price = BigDecimal.ZERO;

        public Builder(String deviceName, int cusotmerId){
            this.deviceName = deviceName;
            this.cusotmerId = cusotmerId;
        }

        public Builder orderId(int orderId){
            this.orderId = orderId;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder managerId(int managerId){
            this.managerId = managerId;
            return this;
        }

        public Builder masterId(int masterId){
            this.masterId = masterId;
            return this;
        }

        public Builder date(Date date){
            this.date = date;
            return this;
        }

        public Builder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public Builder price(double price){
            this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_EVEN);
            return this;
        }

        public RepairOrder build(){
            return new RepairOrder(this);
        }
    }

    public RepairOrder(Builder builder) {
        this.orderId = builder.orderId;
        this.deviceName = builder.deviceName;
        this.description = builder.description;
        this.customerId = builder.cusotmerId;
        this.managerId = builder.managerId;
        this.masterId = builder.masterId;
        this.date = builder.date;
        this.price = builder.price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairOrder order = (RepairOrder) o;
        return orderId == order.orderId &&
                customerId == order.customerId &&
                managerId == order.managerId &&
                masterId == order.masterId &&
                Objects.equals(deviceName, order.deviceName) &&
                Objects.equals(description, order.description) &&
                Objects.equals(date, order.date) &&
                Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, deviceName, description, customerId, managerId, masterId, date, price);
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "orderId=" + orderId +
                ", deviceName='" + deviceName + '\'' +
                ", description='" + description + '\'' +
                ", customerId=" + customerId +
                ", managerId=" + managerId +
                ", masterId=" + masterId +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
