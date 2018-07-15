package com.pganin.barcodescaner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    Connection conn = null;
    public void Init(){
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_name",
                    "user", "password");
            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
