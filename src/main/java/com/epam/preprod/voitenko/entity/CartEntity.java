package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartEntity implements Cart<ElectricToolEntity> {
    private Map<ElectricToolEntity, Integer> cartMap = new HashMap<>();

    @Override
    public Integer addProduct(ElectricToolEntity entity) {
        int number = 0;
        if (cartMap.containsKey(entity)) {
            number = cartMap.get(entity);
        }
        cartMap.put(entity, ++number);
        return number;
    }

    @Override
    public Integer deleteProduct(ElectricToolEntity entity) {
        return cartMap.remove(entity);
    }

    @Override
    public Integer reduceQuantityProduct(ElectricToolEntity entity) {
        int number = 0;
        if (cartMap.containsKey(entity)) {
            number = cartMap.get(entity);
            if (number > 1) {
                cartMap.put(entity, --number);
            }
        }
        return number;
    }

    @Override
    public void clear() {
        cartMap.clear();
    }

    @Override
    public Map<ElectricToolEntity, Integer> getContent() {
        return new HashMap<>(cartMap);
    }

    @Override
    public BigDecimal getTotalSumPurchase() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<ElectricToolEntity, Integer> pair : cartMap.entrySet()) {
            BigDecimal sumCurTool = new BigDecimal(pair.getValue());
            sumCurTool = sumCurTool.multiply(pair.getKey().getCost());
            total = total.add(sumCurTool);
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public Integer getTotalQuantityProducts() {
        int totalQuantity = 0;
        for (Integer QuantitySpecificProduct : cartMap.values()) {
            totalQuantity += QuantitySpecificProduct;
        }
        return totalQuantity;
    }
}