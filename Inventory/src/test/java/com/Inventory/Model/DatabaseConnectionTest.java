package com.Inventory.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private String jdbcUrl = "jdbc:mysql://localhost:3306/mysql";
    private String username = "root";
    private String password = "root";

    @BeforeEach
    public void setUp() throws Exception {
        Class.forName(jdbcDriver);
    }

    @Test
    public void testConnectionAndGetTables() throws Exception {
        // データベースに接続
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        
        // DBUnitの接続を作成
        IDatabaseConnection dbUnitConnection = new JdbcDatabaseTester(jdbcDriver, jdbcUrl, username, password).getConnection();

        // データベースメタデータを取得
        java.sql.DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[] {"TABLE"});

        // テーブル名を出力
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            System.out.println("Table: " + tableName);
        }

        // リソースをクリーンアップ
        tables.close();
        connection.close();
    }
    
}
