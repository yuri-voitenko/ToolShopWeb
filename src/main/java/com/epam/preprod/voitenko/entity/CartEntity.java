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
        return cartMap.put(entity, ++number);
    }

    @Override
    public Integer deleteProduct(ElectricToolEntity entity) {
        return cartMap.remove(entity);
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
            sumCurTool = sumCurTool.add(pair.getKey().getCost());
            total = total.add(sumCurTool);
        }
        return total;
    }
}