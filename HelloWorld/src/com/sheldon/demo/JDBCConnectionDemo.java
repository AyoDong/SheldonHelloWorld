package com.sheldon.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConnectionDemo {

	public static void main(String[] args) throws SQLException{
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/ayo?"
					+ "useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
			String user = "root";
			String password = "0000";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Succeed");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
	}
}
