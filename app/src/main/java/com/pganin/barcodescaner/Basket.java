package com.pganin.barcodescaner;

public class Basket extends Product {
    public Basket(String barCode, String name, int quantity, float valueBuy, float valueOpt, float valueSale, int category, int quantityIn) {
        super(barCode, name, quantity, valueBuy, valueOpt, valueSale, category);
        setQuatityInBasket(quantityIn);

    }
    private int QuatityInBasket = 0;

    public int getQuatityInBasket() {
        return QuatityInBasket;
    }

    public void setQuatityInBasket(int quatityInBasket) {
        QuatityInBasket = quatityInBasket;
    }
}
