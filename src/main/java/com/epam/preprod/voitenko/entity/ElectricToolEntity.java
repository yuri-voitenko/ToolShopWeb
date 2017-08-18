package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;

public class ElectricToolEntity {
    private int id;
    private String name;
    private String category;
    private String manufacturer;
    private int power;
    private int maxRotationSpeed;
    private BigDecimal weight;
    private BigDecimal cost;
    private String mainImage;
    private String additionalImage;

    public ElectricToolEntity() {
    }

    public ElectricToolEntity(String name, String category, String manufacturer, int power,
                              int maxRotationSpeed, BigDecimal weight, BigDecimal cost, String mainImage, String additionalImage) {
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
        this.power = power;
        this.maxRotationSpeed = maxRotationSpeed;
        this.weight = weight;
        this.cost = cost;
        this.mainImage = mainImage;
        this.additionalImage = additionalImage;
    }

    public ElectricToolEntity(ElectricToolEntity anotherObject) {
        this.name = anotherObject.name;
        this.category = anotherObject.category;
        this.manufacturer = anotherObject.manufacturer;
        this.power = anotherObject.power;
        this.maxRotationSpeed = anotherObject.maxRotationSpeed;
        this.weight = anotherObject.weight;
        this.cost = anotherObject.cost;
        this.mainImage = anotherObject.mainImage;
        this.additionalImage = anotherObject.additionalImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMaxRotationSpeed() {
        return maxRotationSpeed;
    }

    public void setMaxRotationSpeed(int maxRotationSpeed) {
        this.maxRotationSpeed = maxRotationSpeed;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getAdditionalImage() {
        return additionalImage;
    }

    public void setAdditionalImage(String additionalImage) {
        this.additionalImage = additionalImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ElectricToolEntity that = (ElectricToolEntity) o;

        if (id != that.id) {
            return false;
        }
        if (power != that.power) {
            return false;
        }
        if (maxRotationSpeed != that.maxRotationSpeed) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null) {
            return false;
        }
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) {
            return false;
        }
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) {
            return false;
        }
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) {
            return false;
        }
        if (mainImage != null ? !mainImage.equals(that.mainImage) : that.mainImage != null) {
            return false;
        }
        return additionalImage != null ? additionalImage.equals(that.additionalImage) : that.additionalImage == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + power;
        result = 31 * result + maxRotationSpeed;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (mainImage != null ? mainImage.hashCode() : 0);
        result = 31 * result + (additionalImage != null ? additionalImage.hashCode() : 0);
        return result;
    }
}