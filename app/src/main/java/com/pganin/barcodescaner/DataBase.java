package com.pganin.barcodescaner;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {//extends AsyncTask {
    Connection conn = null;
    private  Product Last_Product = null;
    private boolean isCreated = false;

    public boolean isCreated() {
        return isCreated;
    }

    public  Product getLast_Product() {
        return Last_Product;
    }

    public void AddTests(int id){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO `Catalog`( `id`, `barcode`, `name`, `quantity`, `valueBuy`, `valueOpt`, `valueSale`) " +
                    "VALUES ( "+id+"," +
                    " 'barcode "+Integer.toString(id, 16)+"'," +
                    " 'name "+Integer.toString(id, 32)+"'," +
                    " "+id/10+"," +
                    " "+id+"," +
                    " "+id+"," +
                    " "+id*1.05f+" )");

        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void Init(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //Class.forName("");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.0.103:3306/AvtoMall?useUnicode=true&characterEncoding=UTF8",//192.168.0.58
                    "pauls", "123456");
            isCreated = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Product> FindProducts(String text){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Catalog WHERE MATCH (name) AGAINST ('"+text+"')");

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("barcode"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("valueBuy"),
                        rs.getFloat("valueOpt"),
                        rs.getFloat("valueSale"),
                        rs.getInt("category")
                );
                products.add(product);
                Last_Product = product;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return products;
    }
    public ArrayList<Product> GetProducts(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Catalog");

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("barcode"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("valueBuy"),
                        rs.getFloat("valueOpt"),
                        rs.getFloat("valueSale"),
                        rs.getInt("category")
                );
                products.add(product);
                Last_Product = product;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return products;
    }
    public boolean FindProductByBarCode(String barcode){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Catalog` WHERE BarCode='"+barcode+"'");

            if (rs.next()) {
                Last_Product = new Product(
                        rs.getString("barcode"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("valueBuy"),
                        rs.getFloat("valueOpt"),
                        rs.getFloat("valueSale"),
                        rs.getInt("category")
                );
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public void AddProduct(Product product){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO `Catalog`( `id`, `barcode`, `name`, `quantity`, `valueBuy`, `valueOpt`, `valueSale`, `category`) " +
                    "VALUES ( 0," +
                    " '"+product.getBarCode()+"'," +
                    " '"+product.getName()+"'," +
                    " "+product.getQuantity()+"," +
                    " "+product.getValueBuy()+"," +
                    " "+product.getValueOpt()+"," +
                    " "+product.getValueSale()+","+
                    " "+product.getCategory()+ " )");
            Last_Product = product;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void EditProduct(Product product){
        System.out.println("Edit try edit");
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("UPDATE `Catalog` SET " +
                    "BarCode='"+product.getBarCode()+"', "+
                    "name='"+product.getName()+"', "+
                    "quantity="+product.getQuantity()+", "+
                    "valueBuy="+product.getValueBuy()+", "+
                    "valueSale="+product.getValueSale()+", "+
                    "valueOpt="+product.getValueOpt()+", "+
                    "category="+product.getCategory()+
                    " WHERE BarCode='"+product.getBarCode()+"'" );
            Last_Product = product;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //@Override
    protected Boolean doInBackground(Object[] objects) {
        Init();
        return false;
    }
}
