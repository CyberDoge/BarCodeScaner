package com.pganin.barcodescaner;

import java.util.ArrayList;

public class Repository {
    private static DataBase DB;
    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Product> basket = new ArrayList<>();

    public static ArrayList<Product> getBasket() {
        return basket;
    }

    public static void setBasket(ArrayList<Product> basket) {
        Repository.basket = basket;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static void setProducts(ArrayList<Product> products) {
        Repository.products = products;
    }

    public static DataBase getDB() {
        return DB;
    }

    public static void setDB(DataBase DB) {
        Repository.DB = DB;
    }
}
