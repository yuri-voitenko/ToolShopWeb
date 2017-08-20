package com.epam.preprod.voitenko.entity;

import java.util.Arrays;

public class FilterEntity {
    private String nameTool;
    private String category;
    private String[] manufacturers;
    private String lowPrice;
    private String highPrice;
    private String orderKey;
    private String orderDirection;
    private String numberToolsOnPage;
    private String numberPage;

    public String getNameTool() {
        return nameTool;
    }

    public void setNameTool(String nameTool) {
        this.nameTool = nameTool;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String[] manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getNumberToolsOnPage() {
        return numberToolsOnPage;
    }

    public void setNumberToolsOnPage(String numberToolsOnPage) {
        this.numberToolsOnPage = numberToolsOnPage;
    }

    public String getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilterEntity that = (FilterEntity) o;

        if (nameTool != null ? !nameTool.equals(that.nameTool) : that.nameTool != null) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(manufacturers, that.manufacturers)) {
            return false;
        }
        if (lowPrice != null ? !lowPrice.equals(that.lowPrice) : that.lowPrice != null) {
            return false;
        }
        if (highPrice != null ? !highPrice.equals(that.highPrice) : that.highPrice != null) {
            return false;
        }
        if (orderKey != null ? !orderKey.equals(that.orderKey) : that.orderKey != null) {
            return false;
        }
        if (orderDirection != null ? !orderDirection.equals(that.orderDirection) : that.orderDirection != null) {
            return false;
        }
        if (numberToolsOnPage != null ? !numberToolsOnPage.equals(that.numberToolsOnPage) : that.numberToolsOnPage != null) {
            return false;
        }
        return numberPage != null ? numberPage.equals(that.numberPage) : that.numberPage == null;
    }

    @Override
    public int hashCode() {
        int result = nameTool != null ? nameTool.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(manufacturers);
        result = 31 * result + (lowPrice != null ? lowPrice.hashCode() : 0);
        result = 31 * result + (highPrice != null ? highPrice.hashCode() : 0);
        result = 31 * result + (orderKey != null ? orderKey.hashCode() : 0);
        result = 31 * result + (orderDirection != null ? orderDirection.hashCode() : 0);
        result = 31 * result + (numberToolsOnPage != null ? numberToolsOnPage.hashCode() : 0);
        result = 31 * result + (numberPage != null ? numberPage.hashCode() : 0);
        return result;
    }
}