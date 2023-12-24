package com.example.yemekai;

public class ItemL {
    String itemName;
    String value;
    String measure;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public ItemL(String item, String value, String measure) {
        this.itemName = item;
        this.value = value;
        this.measure=measure;
    }
    @Override
    public String toString() {
        return itemName + " " + value+" "+measure;
    }
}
