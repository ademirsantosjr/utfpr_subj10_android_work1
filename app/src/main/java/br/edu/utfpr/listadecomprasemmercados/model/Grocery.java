package br.edu.utfpr.listadecomprasemmercados.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Grocery {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String itemName;

    @NonNull
    private String itemBrand;

    @NonNull
    private String packingType;

    @NonNull
    private int amountInThePackage;

    @NonNull
    private String unitOfMeasurement;

    @NonNull
    private String category;

    private boolean isBasicItem;

    public Grocery() {
    }

    public Grocery(String itemName,
                   String itemBrand,
                   String packingType,
                   int amountInThePackage,
                   String unitOfMeasurement,
                   String category,
                   boolean isBasicItem) {

        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.packingType = packingType;
        this.amountInThePackage = amountInThePackage;
        this.unitOfMeasurement = unitOfMeasurement;
        this.category = category;
        this.isBasicItem = isBasicItem;
    }

    public int getId() { return id; }

    public String getItemName() {
        return itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public String getPackingType() {
        return packingType;
    }

    public int getAmountInThePackage() {
        return amountInThePackage;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBasicItem() {
        return isBasicItem;
    }

    public void setId(int id) { this.id = id; }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public void setAmountInThePackage(int amountInThePackage) {
        this.amountInThePackage = amountInThePackage;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsBasicItem(boolean basicItem) {
        this.isBasicItem = basicItem;
    }

    @Override
    public String toString() {
        return itemName + " " +
                itemBrand + ", " +
                packingType + " " +
                amountInThePackage +
                unitOfMeasurement; /* + "\n" +
                "categoria: " + category +
                (isBasicItem() ? " (cesta básica)" : "");*/
    }
}
