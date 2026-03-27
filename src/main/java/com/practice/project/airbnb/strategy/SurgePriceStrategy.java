package com.practice.project.airbnb.strategy;

import com.practice.project.airbnb.entity.Inventory;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;



@RequiredArgsConstructor
public class SurgePriceStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        return wrapped.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }


}
