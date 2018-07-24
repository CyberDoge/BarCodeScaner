package com.pganin.barcodescaner;

public class Product {
    private String BarCode="";
    private String Name = "";
    private int Quantity = 0;
    private float ValueBuy = .0f;
    private float ValueOpt = .0f;
    private float ValueSale = .0f;
    private int Category = 0;
    public Product(String barCode, String name, int quantity, float valueBuy, float valueOpt, float valueSale, int category){
        BarCode = barCode;
        Name = name;
        Quantity = quantity;
        ValueBuy = valueBuy;
        ValueOpt = valueOpt;
        ValueSale = valueSale;
        Category = category;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public float getValueBuy() {
        return ValueBuy;
    }

    public void setValueBuy(float valueBuy) {
        ValueBuy = valueBuy;
    }

    public float getValueOpt() {
        return ValueOpt;
    }

    public void setValueOpt(float valueOpt) {
        ValueOpt = valueOpt;
    }

    public float getValueSale() {
        return ValueSale;
    }

    public void setValueSale(float valueSale) {
        ValueSale = valueSale;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }
}
