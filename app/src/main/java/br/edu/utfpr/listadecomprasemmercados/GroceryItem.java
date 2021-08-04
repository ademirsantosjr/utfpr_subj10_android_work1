package br.edu.utfpr.listadecomprasemmercados;

public class GroceryItem {

    private String itemName;
    private String itemBrand;
    private String packingType;
    private int amountInThePackage;
    private String unitOfMeasurement;
    private Category category;
    private boolean basicItem = false;

    public GroceryItem(String itemName, String itemBrand, String packingType,
                       int amountInThePackage, String unitOfMeasurement) {
        this.itemName = itemName;
        this.itemBrand = itemBrand;
        this.packingType = packingType;
        this.amountInThePackage = amountInThePackage;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public String getPackingType() {
        return packingType;
    }

    public double getAmountInThePackage() {
        return amountInThePackage;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isBasicItem() {
        return basicItem;
    }

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

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setBasicItem(boolean basicItem) {
        this.basicItem = basicItem;
    }

    @Override
    public String toString() {
        return itemName + " " +
                itemBrand + ", " +
                packingType + " " +
                amountInThePackage +
                unitOfMeasurement;
    }
}
