package com.Inventory.Model;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {YourSpringConfig.class}) // Springの設定クラスを指定
@Transactional
public class ListSearchDBTest4 extends DatabaseTestCase {

    private IDataSet backupDataSet; // 元のデータをバックアップするための変数

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        Connection jdbcConnection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mysql", // データベース名
            "root", // ユーザー名
            "root"  // パスワード
        );
        IDatabaseConnection con = new DatabaseConnection(jdbcConnection, "mysql");
        con.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
        return con;
    }
    
    
    @Override
    protected void setUp() throws Exception {
        IDatabaseConnection connection = getConnection();
        
        // "staff" テーブルをバックアップ
        backupDataSet = connection.createDataSet(new String[] { "product_detail" });

        // バックアップデータの行数をログ出力
        System.out.println("Backup row count: " + backupDataSet.getTable("product_detail").getRowCount());

        // 親クラスのsetUp()を呼び出して、テスト用データの挿入を行う
        super.setUp();
    }


    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(
            getClass().getClassLoader().getResourceAsStream("ListSearch2.xml")
        );
    }

    

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT; // データを追加のみする
    }

    @Override
    protected void tearDown() throws Exception {
        IDatabaseConnection connection = getConnection();

        try {
            // まず、テーブルをクリア
            DatabaseOperation.DELETE_ALL.execute(connection, getDataSet());
        } catch (Exception e) {
            System.err.println("Error clearing data: " + e.getMessage());
        }

        try {
            // バックアップデータを元に戻す
            DatabaseOperation.CLEAN_INSERT.execute(connection, backupDataSet);
        } catch (Exception e) {
            System.err.println("Error restoring backup data: " + e.getMessage());
        }

        super.tearDown();
    }



    @Test
    @Rollback(true)
    public void testStaffTable() throws Exception {
        IDatabaseConnection connection = getConnection();
        ITable actualTable = connection.createQueryTable("product_detail", "SELECT * FROM product_detail");
        
        // 実際のテーブルの行を出力して確認
        for (int i = 0; i < actualTable.getRowCount(); i++) {
            System.out.println("Row " + i + ": " + actualTable.getValue(i, "trading_date"));
        }

        ITable expectedTable = getDataSet().getTable("product_detail");
        assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
    }

}
