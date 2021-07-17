package com.galvanize.invoicify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.galvanize.invoicify.domain.Fee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(value = { "fee" })
public class ItemDto {
    private String description;
    private int quantity;
    private Fee fee;
    private boolean isFlatFee;
    private double rateFee;
    private int quantityFee;
    private double amountFlatFee;

    public ItemDto(String description, int quantity, boolean isFlatFee, double amountFlatFee) {
        this.description = description;
        this.quantity = quantity;
        this.isFlatFee = isFlatFee;
        this.amountFlatFee = amountFlatFee;
    }

    public ItemDto(String description, int quantity, boolean isFlatFee, double rateFee, int quantityFee) {
        this.description = description;
        this.quantity = quantity;
        this.isFlatFee = isFlatFee;
        this.rateFee = rateFee;
        this.quantityFee = quantityFee;
    }

    public double getFee() {
        return fee.getTotalFee();
    }

    public boolean getIsFlatFee() {
        return isFlatFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return quantity == itemDto.quantity && isFlatFee == itemDto.isFlatFee && Double.compare(itemDto.rateFee, rateFee) == 0 && quantityFee == itemDto.quantityFee && Double.compare(itemDto.amountFlatFee, amountFlatFee) == 0 && description.equals(itemDto.description) && fee.equals(itemDto.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, quantity, fee, isFlatFee, rateFee, quantityFee, amountFlatFee);
    }

    public void setIsFlatFee(boolean flatFee) {
        isFlatFee = flatFee;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "description='" + description + '\'' +
                ", quantity=" + quantity +
                ", fee=" + fee +
                ", isFlatFee=" + isFlatFee +
                ", rateFee=" + rateFee +
                ", quantityFee=" + quantityFee +
                ", amountFlatFee=" + amountFlatFee +
                '}';
    }

}
