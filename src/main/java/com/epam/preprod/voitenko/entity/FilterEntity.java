package com.epam.preprod.voitenko.entity;

public class FilterEntity {
    private String nameTool;
    private String category;
    private String manufacturer;
    private String lowPrice;
    private String highPrice;

    public FilterEntity() {
    }

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) {
            return false;
        }
        if (lowPrice != null ? !lowPrice.equals(that.lowPrice) : that.lowPrice != null) {
            return false;
        }
        return highPrice != null ? highPrice.equals(that.highPrice) : that.highPrice == null;
    }

    @Override
    public int hashCode() {
        int result = nameTool != null ? nameTool.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (lowPrice != null ? lowPrice.hashCode() : 0);
        result = 31 * result + (highPrice != null ? highPrice.hashCode() : 0);
        return result;
    }
}