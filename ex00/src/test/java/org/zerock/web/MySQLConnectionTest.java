package org.zerock.web;

import java.sql.DriverManager;

import org.junit.Test;

import com.mysql.jdbc.Connection;

public class MySQLConnectionTest {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/book_ex?useSSL=false";
	
	private static final String USER = "zerock";
	
	private static final String PW = "zerock";
	
	@Test
	public void testConnection(){
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(java.sql.Connection con = DriverManager.getConnection(URL, USER, PW)){
			System.out.println(con);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
