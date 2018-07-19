package com.pganin.barcodescaner;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DataBase {//extends AsyncTask<String, Void, Boolean> {
    Connection conn = null;
    private  Product Last_Product = null;

    public  Product getLast_Product() {
        return Last_Product;
    }

    public void Init(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //Class.forName("");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.85:3306/test",
                    "test", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

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
                        rs.getFloat("valueSale")
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
                        rs.getFloat("valueSale")
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
            stmt.executeUpdate("INSERT INTO `Catalog`( `barcode`, `name`, `quantity`, `valueBuy`, `valueOpt`, `valueSale`, `name_optional`) " +
                    "VALUES ('"+product.getBarCode()+"'," +
                    " '"+product.getName()+"'," +
                    " "+product.getQuantity()+"," +
                    " "+product.getValueBuy()+"," +
                    " "+product.getValueOpt()+"," +
                    " "+product.getValueSale()+")");
            Last_Product = product;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void EditProduct(Product product){
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE `Catalog` SET `id`=@rowid:=@rowid+1 " +
                    "`BarCode`='"+product.getBarCode()+"', "+
                    "`name`='"+product.getName()+"', "+
                    "`quantity`="+product.getQuantity()+", "+
                    "`valueBuy`="+product.getValueBuy()+", "+
                    "`valueSale`="+product.getValueSale()+", "+
                    "`valueOpt`="+product.getValueOpt()+
                    " WHERE `BarCode`='"+product.getBarCode()+"'" );
            Last_Product = product;
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        DataBase db = new DataBase();
        db.Init();
        ArrayList<Product> products = db.GetProducts();
        for(Product p: products){
            System.out.println("code " + p.getBarCode());
        }
    }



    //@Override
    protected Boolean doInBackground(String... strings) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.1:3306/barcode",
                    "root", "evdh5r36");//DriverManager.getConnection(ConnURL);
                    //
            if (conn == null) {
                System.out.println("Can't connect to DB!");
                System.exit(0);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
