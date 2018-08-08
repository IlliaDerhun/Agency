package agency.illiaderhun.com.github.model.entities;

import agency.illiaderhun.com.github.annotations.AutoGeneration;
import agency.illiaderhun.com.github.annotations.Column;
import agency.illiaderhun.com.github.annotations.Table;
import agency.illiaderhun.com.github.annotations.TypeOfGeneration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Spare - class represents some replacement parts
 * for repairing
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Table(name = "spare")
public class Spare {

    @Column(name = "detail_id")
    @AutoGeneration(TypeOfGeneration.AUTOINCREMENT)
    private int detailId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    public static class Builder{
        // Required fields
        private int detailId;
        private String name;

        // Optional fields
        private String description = "No description";
        private int quantity = 0;
        private BigDecimal price = BigDecimal.ZERO;

        public Builder(int detailId, String name){
            this.detailId = detailId;
            this.name = name;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder quantity(int quantity){
            this.quantity = quantity;
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

        public Spare build(){
            return new Spare(this);
        }
    }

    private Spare(Builder builder){
        this.detailId = builder.detailId;
        this.name = builder.name;
        this.description = builder.description;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(double price){
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spare spare = (Spare) o;
        return detailId == spare.detailId &&
                quantity == spare.quantity &&
                Objects.equals(name, spare.name) &&
                Objects.equals(description, spare.description) &&
                Objects.equals(price, spare.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(detailId, name, description, quantity, price);
    }

    @Override
    public String toString() {
        return "Spare{" +
                "detailId=" + detailId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
